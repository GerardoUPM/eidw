package edu.upm.midas.data.extraction.album.model.response;

import java.util.List;

public class ResponseGDLL extends ResponseFather{

    private Integer diseaseCount;
    private List<Disease> diseases;
    private boolean useDiseaseSafeList;


    public Integer getDiseaseCount() {
        return diseaseCount;
    }

    public void setDiseaseCount(Integer diseaseCount) {
        this.diseaseCount = diseaseCount;
    }

    public List<Disease> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Disease> diseases) {
        this.diseases = diseases;
    }

    public boolean isUseDiseaseSafeList() {
        return useDiseaseSafeList;
    }

    public void setUseDiseaseSafeList(boolean useDiseaseSafeList) {
        this.useDiseaseSafeList = useDiseaseSafeList;
    }

    @Override
    public String toString() {
        return "ResponseGDLL{" +
                "diseaseCount=" + diseaseCount +
                ", diseases=" + diseases +
                ", useDiseaseSafeList=" + useDiseaseSafeList +
                '}';
    }
}
