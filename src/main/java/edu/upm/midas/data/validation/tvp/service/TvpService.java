package edu.upm.midas.data.validation.tvp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.service.HasSymptomService;
import edu.upm.midas.data.relational.service.SourceService;
import edu.upm.midas.data.relational.service.helperNative.ConfigurationHelper;
import edu.upm.midas.data.validation.helper.ConsultHelper;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.model.query.ResponseSymptom;
import edu.upm.midas.data.validation.tvp.model.TvpConfiguration;
import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import edu.upm.midas.data.validation.tvp.model.response.Response;
import edu.upm.midas.data.validation.tvp.tvpApiResponse.impl.TvpResourceServiceImpl;
import edu.upm.midas.utilsservice.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gerardo on 24/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpService
 * @see
 */
@Service
public class TvpService {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private SourceService sourceService;
    @Autowired
    private HasSymptomService hasSymptomService;
    @Autowired
    private ConsultHelper consultHelper;
    @Autowired
    private TvpResourceServiceImpl tvpResource;
    @Autowired
    private ConfigurationHelper configurationHelper;
    @Autowired
    private Constants constants;
    @Autowired
    private UtilDate utilDate;



    /**
     * @param consult
     * @throws Exception
     */
    @Transactional
    public void validation(Consult consult) throws Exception {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TvpConfiguration tvpConfiguration = new TvpConfiguration();
        //Colocar una validación para Consult...
        String sourceId = sourceService.findByNameNative( consult.getSource() );
        System.out.println( "Source: " + sourceId);
        System.out.println( "Read symptoms to the DB..." );
        List<ResponseSymptom> responseSymptoms = consultHelper.findSymptomsByVersionAndSource( consult );
        tvpConfiguration.setNumSymptomsFound(responseSymptoms.size());
        System.out.println( "Symptoms found: " + tvpConfiguration.getNumSymptomsFound() );
        System.out.println( "Removing repeated symptoms..." );
        List<Concept> nonRepetedSymptoms = getConceptList(responseSymptoms );
        System.out.println( "NonRepetedSymptoms: " + nonRepetedSymptoms.size() );
        System.out.println( "Creating request..." );
        Request request = new Request();
        request.setConcepts( nonRepetedSymptoms );
        request.setToken( "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmFyZG9sYWdhckBob3RtYWlsLmNvbSIsImF1ZCI6IndlYiIsIm5hbWUiOiJHZXJhcmRvIExhZ3VuZXMiLCJ1c2VyIjp0cnVlLCJpYXQiOjE1MDk2MTQyNjh9.uVhDgfLrAgdnj02Hsbgfj9tkVlfni89i0hKVYW31eHApCHpheikK9ae1MhbzRhiyUcFGMKwtiyVgff5NCMY3PA" );
        //printConcepstJSON( nonRepetedSymptoms );
        System.out.println( "Connection_ with TVP API..." );
        System.out.println( "Validating symptoms... please wait, this process can take from minutes to hours... " );
        Response response = tvpResource.getValidateSymptoms( request );
        System.out.println("Authorization: "+ response.isAuthorization());


        if (response.isAuthorization()) {
            int validatedSymptoms = 0;
        /* Actualizar entidad HasSymptom con CUI y textId */
            for (ResponseSymptom symptom : responseSymptoms) {
                MatchNLP matchNLP = exist(symptom.getCui(), response.getValidatedConcepts());//antes matchNLPList
                if (matchNLP.hasMatches()) {
                    System.out.println("Symptom validated! | " + symptom.getCui() + "==" + matchNLP.getConcept().toString());
                    hasSymptomService.updateValidatedNative(consult.getVersion(), sourceId, symptom.getCui(), true);
                    validatedSymptoms++;
                    System.out.println("Insert symptom in DB ready!");
                } else {
                    System.out.println("Symptom not found:" + symptom.getCui());
                }
            }
            System.out.println("Start insert configuration...");
            tvpConfiguration.setNumSymptomsFoundValidated(validatedSymptoms);
            String configurationJson = gson.toJson(tvpConfiguration);
            configurationHelper.insert(Constants.SOURCE_WIKIPEDIA, consult.getDate(), constants.SERVICE_TVP_CODE + " - " + constants.SERVICE_TVP_NAME, configurationJson);
            System.out.println("End insert configuration ready!...");
        }else{
            System.out.println("Authorization message: " + response.getAuthorizationMessage() + " | token: " + response.getToken());
        }

    }


    public List<Concept> getConceptList(List<ResponseSymptom> responseSymptoms){
        List<Concept> concepts = new ArrayList<>();
        for (ResponseSymptom symptom: responseSymptoms) {
            Concept concept = new Concept();
            concept.setCui( symptom.getCui() );
            concept.setName( symptom.getSymptomName() );
            concepts.add( concept );
        }
        return removeRepetedConcepts( concepts );
    }


    public List<Concept> removeRepetedConcepts(List<Concept> elements){
        List<Concept> resList = elements;
        Set<Concept> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(elements);
        elements.clear();
        elements.addAll(linkedHashSet);
        return resList;
    }


    public MatchNLP exist(String cui, List<MatchNLP> matchNLPList){
        for (MatchNLP matchNLP: matchNLPList) {
            if (matchNLP.getConcept().getCui().equals( cui )){
                return matchNLP;
            }
        }
        return null;
    }

    public void printConcepstJSON(List<Concept> concepts) throws JsonProcessingException {
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(concepts));
    }

}
