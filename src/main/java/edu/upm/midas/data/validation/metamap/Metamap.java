package edu.upm.midas.data.validation.metamap;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.utilsservice.Common;
import gov.nih.nlm.nls.metamap.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo on 09/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className MetamapService
 * @see
 */
@Service
public class Metamap implements NLPInterface {

    private MetaMapApi oMmapi;

    @Autowired
    private Common common;

    public Metamap(){
        oMmapi = new MetaMapApiImpl("localhost");
        oMmapi.setOptions("-y -R SNOMEDCT_US");
    }

    /**
     * Method to process the UMLS terms loaded in a disease.
     *
     * @param signs_symptoms_text
     *            Receives the disease.
     * @throws Exception
     *             It can throws an exception.
     */
    public ArrayList<Ev> performNLP(String signs_symptoms_text)
            throws Exception {
        ArrayList<Ev> conceptsList = new ArrayList<Ev>();
        //System.out.println("HTML_A pasar a MetaMap: " + signs_symptoms_text);
        List<Result> citationsList = oMmapi
                .processCitationsFromString( signs_symptoms_text );

        for (int j = 0; j < citationsList.size(); j++) {
            Result result = citationsList.get(j);
            for (Utterance utterance : result.getUtteranceList()) {
                for (PCM pcm : utterance.getPCMList()) {
                    for (Mapping map : pcm.getMappingList()) {
                        for (Ev mapEv : map.getEvList()) {
                            if (isAValidSemanticType( mapEv.getSemanticTypes() )) {
                                conceptsList.add( mapEv );
                            }
                        }
                    }
                }
            }
        }

        return conceptsList;
    }

    /**
     * Method to check if contains a valid semantic type.
     *
     * @param semanticTypes
     *            Receive the list of semantic types of the term.
     * @return Return true or false.
     */
    private boolean isAValidSemanticType(List<String> semanticTypes) {
        for (int i = 0; i < Constants.SEMANTIC_TYPES.length; i++) {

            String validSemanticType = Constants.SEMANTIC_TYPES[i];

            if (semanticTypes.contains(validSemanticType)) {
                return true;
            }

        }
        return false;
    }

    public ArrayList<Ev> conceptsList (String text) throws Exception {

        ArrayList<Ev> conceptsList = null;

        if (!common.isEmpty( text )) {
            conceptsList = performNLP( text );
        }

        return conceptsList;

    }


}
