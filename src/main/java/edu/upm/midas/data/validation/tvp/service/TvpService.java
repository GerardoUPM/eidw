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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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
    public void validation(Consult consult) throws Exception {

        String fileName = consult.getSnapshot() + "_updates_has_symptom.txt";
        String path = Constants.TVP_RETRIEVAL_HISTORY_FOLDER + fileName;
        FileWriter fileWriter = new FileWriter(path);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        TvpConfiguration tvpConfiguration = new TvpConfiguration();
        //Colocar una validación para Consult...
        String sourceId = sourceService.findByNameNative( consult.getSource() );
        System.out.println( "Source: " + sourceId);
        System.out.println( "Read symptoms to the DB..." );
        List<ResponseSymptom> responseSymptoms = consultHelper.findSymptomsByVersionAndSource( consult );
        tvpConfiguration.setTermsFound(responseSymptoms.size());
        System.out.println( "Symptoms found: " + tvpConfiguration.getTermsFound() );
        System.out.println( "Removing repeated symptoms..." );
        List<Concept> nonRepetedSymptoms = getConceptList(responseSymptoms );
        tvpConfiguration.setNonRepetedTerms(nonRepetedSymptoms.size());
        System.out.println( "NonRepetedSymptoms: " + nonRepetedSymptoms.size() );
        System.out.println( "Creating request..." );
        Request request = new Request();
        request.setConcepts( nonRepetedSymptoms );
        request.setSource(consult.getSource());
        request.setSnapshot(consult.getSnapshot());
        request.setToken( "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmFyZG9sYWdhckBob3RtYWlsLmNvbSIsImF1ZCI6IndlYiIsIm5hbWUiOiJHZXJhcmRvIExhZ3VuZXMiLCJ1c2VyIjp0cnVlLCJpYXQiOjE1MDk2MTQyNjh9.uVhDgfLrAgdnj02Hsbgfj9tkVlfni89i0hKVYW31eHApCHpheikK9ae1MhbzRhiyUcFGMKwtiyVgff5NCMY3PA" );
        //printConcepstJSON( nonRepetedSymptoms );
        System.out.println(request);
        System.out.println( "Connection_ with TVP API..." );
        System.out.println( "Validating symptoms... please wait, this process can take from minutes to hours... " );

        //VERDADERO Y NO FUNCIONA AHORA, NO SE PORQUE
        Response response = tvpResource.getValidateSymptoms( request );
        //CONSUMIR UN JSON
        //Response response = readTVPValidationJSON(consult.getSnapshot());
        System.out.println("Authorization: "+ response.isAuthorized());


        if (response.isAuthorized()) {
            int validatedSymptoms = 0;
            /* Actualizar entidad HasSymptom con CUI y textId */
            System.out.println("Authorization: "+ response.getValidatedConcepts().size() + "|" + responseSymptoms.size());
            for (MatchNLP matchNLP : response.getValidatedConcepts()) {//ResponseSymptom
                //MatchNLP matchNLP = exist(symptom.getCui(), response.getValidatedConcepts());//antes matchNLPList
                if (matchNLP.hasMatches()) {
                    System.out.println(validatedSymptoms +" to "+response.getValidatedConcepts().size()+" Symptom validated! | " + matchNLP.getConcept().getCui() + "==" + matchNLP.getConcept().toString());
//                    hasSymptomService.updateValidatedNative(consult.getSnapshot(), sourceId, matchNLP.getConcept().getCui(), true);
                    fileWriter.write("UPDATE has_symptom h " +
                            "SET h.validated = 1 " +
                            "WHERE h.text_id LIKE '%"+consult.getSnapshot()+"%' " +
                            "AND h.text_id LIKE '%"+sourceId+"%' " +
                            "AND h.cui = '"+matchNLP.getConcept().getCui()+"';\n");
                    validatedSymptoms++;
                    System.out.println("Update symptom in DB ready!");
                } else {
                    System.out.println("Symptom not found:" + matchNLP.getConcept().getCui());
                }
            }
            fileWriter.close();
            System.out.println("Start insert configuration...");
            tvpConfiguration.setValidatedNonRepetedTerms(validatedSymptoms);
            String configurationJson = gson.toJson(tvpConfiguration);
            configurationHelper.insert(consult.getSource(), consult.getDate(), constants.SERVICE_TVP_CODE + " - " + constants.SERVICE_TVP_NAME, configurationJson);
            System.out.println("End insert configuration ready!...");
        }else{
            System.out.println("Authorization message: " + response.getAuthorizationMessage() + " | token: " + response.getToken());
        }

    }


    public List<Concept> getConceptList(List<ResponseSymptom> responseSymptoms){
        List<Concept> concepts = new ArrayList<Concept>();
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


    /**
     * @param snapshot
     * @return
     * @throws Exception
     */
    public Response readTVPValidationJSON(String snapshot) throws Exception {
        Response response = null;
        Gson gson = new Gson();
        String fileName = snapshot + Constants.TVP_RETRIEVAL_FILE_NAME + Constants.DOT_JSON;
        String path = Constants.TVP_RETRIEVAL_HISTORY_FOLDER + fileName;
        System.out.println("Read JSON!..." + path);

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            response = gson.fromJson(br, Response.class);
        }catch (Exception e){
            System.out.println("Error to read or convert JSON!...");
        }

        return response;
    }

}
