package edu.upm.midas.data.validation.tvp.model;
/**
 * Created by gerardo on 22/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpConfiguration
 * @see
 */
public class TvpConfiguration {

    private int termsFound;
    private int nonRepetedTerms;
    private int validatedNonRepetedTerms;


    public int getTermsFound() {
        return termsFound;
    }

    public void setTermsFound(int termsFound) {
        this.termsFound = termsFound;
    }

    public int getNonRepetedTerms() {
        return nonRepetedTerms;
    }

    public void setNonRepetedTerms(int nonRepetedTerms) {
        this.nonRepetedTerms = nonRepetedTerms;
    }

    public int getValidatedNonRepetedTerms() {
        return validatedNonRepetedTerms;
    }

    public void setValidatedNonRepetedTerms(int validatedNonRepetedTerms) {
        this.validatedNonRepetedTerms = validatedNonRepetedTerms;
    }
}
