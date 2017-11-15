package edu.upm.midas.data.extraction.album.model.response;

import java.util.List;

public class ResponseGDLL extends ResponseFather{

    private List<Disease> diseases;


    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }
}
