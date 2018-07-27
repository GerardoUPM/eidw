package edu.upm.midas.data.extraction.model;

import edu.upm.midas.data.extraction.model.code.Code;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 08/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edsssdb
 * @className Doc
 * @see
 */
public class Doc {

    private int id;
    private Date date;
    private Link url;
    private List<Link> urlList;
    private boolean hasConnected;
    private boolean diseaseArticle;

    private Disease disease;
    private Integer sectionCount;
    private List<Section> sectionList;
    private Integer codeCount;
    private List<Code> codeList;
    private Integer paperCount;
    private List<PubMedDoc> paperList;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Link getUrl() {
        return url;
    }

    public void setUrl(Link url) {
        this.url = url;
    }

    public List<Link> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Link> urlList) {
        this.urlList = urlList;
    }

    public Disease getDisease() {
        return disease;
    }

    public void setDisease(Disease disease) {
        this.disease = disease;
    }

    public List<Section> getSectionList() {
        return sectionList;
    }

    public void setSectionList(List<Section> sectionList) {
        this.sectionList = sectionList;
    }

    public List<Code> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Code> codeList) {
        this.codeList = codeList;
    }

    public boolean isDiseaseArticle() {
        return diseaseArticle;
    }

    public void setDiseaseArticle(boolean diseaseArticle) {
        this.diseaseArticle = diseaseArticle;
    }

    public boolean isHasConnected() {
        return hasConnected;
    }

    public void setHasConnected(boolean hasConnected) {
        this.hasConnected = hasConnected;
    }

    public Integer getSectionCount() {
        return sectionCount;
    }

    public void setSectionCount(Integer sectionCount) {
        this.sectionCount = sectionCount;
    }

    public Integer getCodeCount() {
        return codeCount;
    }

    public void setCodeCount(Integer codeCount) {
        this.codeCount = codeCount;
    }

    public Integer getPaperCount() {
        return paperCount;
    }

    public void setPaperCount(Integer paperCount) {
        this.paperCount = paperCount;
    }

    public List<PubMedDoc> getPaperList() {
        return paperList;
    }

    public void setPaperList(List<PubMedDoc> paperList) {
        this.paperList = paperList;
    }
}
