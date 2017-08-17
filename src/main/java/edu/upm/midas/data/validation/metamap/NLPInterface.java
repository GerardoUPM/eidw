package edu.upm.midas.data.validation.metamap;

import gov.nih.nlm.nls.metamap.Ev;

import java.util.ArrayList;

/**
 * Created by gerardo on 24/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className NLPInterface
 * @see
 */
public interface NLPInterface {

    public ArrayList<Ev> performNLP(String signs_symptoms_text) throws Exception;

}
