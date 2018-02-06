package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.relational.entities.edsssdb.HasSymptom;
import edu.upm.midas.data.relational.entities.edsssdb.HasSymptomPK;
import edu.upm.midas.data.relational.entities.edsssdb.Symptom;
import edu.upm.midas.data.relational.service.HasSymptomService;
import edu.upm.midas.data.relational.service.SymptomService;
import edu.upm.midas.data.validation.metamap.model.response.Concept;
import edu.upm.midas.utilsservice.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SemanticTypeHelper
 * @see
 */
@Service
public class SymptomHelperNative {


    @Autowired
    private SymptomService symptomService;
    @Autowired
    private SemanticTypeHelperNative semanticTypeHelperNative;
    @Autowired
    private HasSymptomService hasSymptomService;

    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(SymptomHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;



    /**
     * @param concept
     * @return
     * @throws Exception
     */
    public void insertIfExist(Concept concept, String textId) throws Exception{
        List<Symptom> symptoms;
        Symptom symptom = symptomService.findById( concept.getCui() );
        if ( symptom == null ){
            symptomService.insertNative( concept.getCui(), concept.getName() );
            setSemanticType( concept, "" );
            setHasSymptom(textId, concept.getCui(), false, concept.getMatchedWords().toString(), concept.getPositionalInfo());
        }else{
            setSemanticType( concept, "" );
            setHasSymptom(textId, concept.getCui(), false, concept.getMatchedWords().toString(), concept.getPositionalInfo());
        }
    }


    /**
     * @param concept
     * @param description
     * @throws JsonProcessingException
     */
    public void setSemanticType(Concept concept, String description) throws Exception {
        for (String semType: concept.getSemanticTypes()) {
            semanticTypeHelperNative.insertIfExist( semType, description );
            boolean hasSemanticType = symptomService.findHasSemanticTypeIdNative(concept.getCui(), semType);
            if (hasSemanticType == false){
                semanticTypeHelperNative.insertHasSemanticType(concept.getCui(), semType);
            }
        }
    }


    /**
     * @param textId
     * @param cui
     * @param validated
     */
    public void setHasSymptom(String textId, String cui, boolean validated, String matchedWords, String positionalInfo){
        HasSymptomPK hasSymptomPK = new HasSymptomPK();
        hasSymptomPK.setCui( cui );
        hasSymptomPK.setTextId( textId );
        HasSymptom hasSymptom = hasSymptomService.findById( hasSymptomPK );
        if ( hasSymptom == null ){
            hasSymptomService.insertNative(textId, cui, validated, matchedWords, positionalInfo);
        }else{
            String matchedWords_ = hasSymptom.getMatchedWords() + "&" + matchedWords;
            String positionalInfo_ = hasSymptom.getPositionalInfo() + "&" + positionalInfo;
            hasSymptomService.updateMatchedWordsAndPositionalInfoNative(hasSymptom.getTextId(),
                                                                        hasSymptom.getCui(),
                                                                        matchedWords_,
                                                                        positionalInfo_);
        }
    }


    /**
     * @param cui
     * @return
     */
    public boolean exist(String cui){
        Symptom symptom = symptomService.findById( cui );
        if( symptom != null )
            return true;
        else
            return false;
    }


    /**
     * @param cui
     * @return
     */
    public Symptom getSymptom(String cui){
        return symptomService.findById( cui );
    }


}
