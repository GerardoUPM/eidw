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

    private int numSymptomsFound;
    private int numSymptomsFoundValidated;


    public int getNumSymptomsFound() {
        return numSymptomsFound;
    }

    public void setNumSymptomsFound(int numSymptomsFound) {
        this.numSymptomsFound = numSymptomsFound;
    }

    public int getNumSymptomsFoundValidated() {
        return numSymptomsFoundValidated;
    }

    public void setNumSymptomsFoundValidated(int numSymptomsFoundValidated) {
        this.numSymptomsFoundValidated = numSymptomsFoundValidated;
    }
}
