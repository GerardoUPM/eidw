package edu.upm.midas.data.validation.tvp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.relational.service.HasSymptomService;
import edu.upm.midas.data.relational.service.SourceService;
import edu.upm.midas.data.validation.helper.ConsultHelper;
import edu.upm.midas.data.validation.model.Consult;
import edu.upm.midas.data.validation.model.query.ResponseSymptom;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import edu.upm.midas.data.validation.tvp.tvpApiResponse.impl.TvpResourceImpl;
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
    private SourceService sourceService;
    @Autowired
    private HasSymptomService hasSymptomService;
    @Autowired
    private ConsultHelper consultHelper;
    @Autowired
    private TvpResourceImpl tvpResource;
    @Autowired
    private ObjectMapper mapper;


    /**
     * @param consult
     * @throws Exception
     */
    @Transactional
    public void validation(Consult consult) throws Exception {
        String sourceId = sourceService.findByNameNative( consult.getSource() );
        System.out.println( "Source: " + sourceId);
        System.out.println( "Read symptoms to the DB..." );
        List<ResponseSymptom> responseSymptoms = consultHelper.findSymptomssByVersionAndSource( consult );
        System.out.println( "Symptoms found: " + responseSymptoms.size() );
        System.out.println( "Removing repeated symptoms..." );
        List<Concept> nonRepetedSymptoms = getConceptList(responseSymptoms );
        System.out.println( "NonRepetedSymptoms: " + nonRepetedSymptoms.size() );
        System.out.println( "Creating request..." );
        printConcepstJSON( nonRepetedSymptoms );
        System.out.println( "Connect with TVP API..." );
        System.out.println( "Validating symptoms... please wait, this process can take from minutes to hours... " );
        List<MatchNLP> matchNLPList = tvpResource.getValidateSymptoms( nonRepetedSymptoms );


        int countText = 1;
        /* Actualizar entidad HasSymptom con CUI y textId */
        for (ResponseSymptom symptom:
                responseSymptoms) {
            MatchNLP matchNLP = exist(symptom.getCui(), matchNLPList);
            if ( matchNLP.hasMatches() ){
                System.out.println("Symptom validated! | " + symptom.getCui() + "==" + matchNLP.getConcept().toString());
                //hasSymptomService.updateValidatedNative(consult.getVersion(), sourceId, symptom.getCui(), true);
            }else{
                System.out.println("Symptom not found.");
            }
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
