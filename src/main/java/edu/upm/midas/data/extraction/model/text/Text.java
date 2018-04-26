package edu.upm.midas.data.extraction.model.text;

import edu.upm.midas.data.extraction.model.Link;

import java.util.List;

/**
 * Created by gerardo on 3/4/17.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationWikipedia
 * @className Text
 * @see
 */
//Un Text siempre será un <p>, <ul><ol>
public class Text {

    private int id;
    private String title;//EL nombre si tiene, será un <h3>
    private int textOrder;

    private Integer urlCount;
    private List<Link> urlList;
    private String paperId;
    private String text;

    public Text() {
    }

    public Text(int id, int textOrder) {
        this.id = id;
        this.textOrder = textOrder;
    }

    public Text(Integer id, int textOrder, String paperId) {
        this.id = id;
        this.textOrder = textOrder;
        this.paperId = paperId;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTextOrder() {
        return textOrder;
    }

    public void setTextOrder(int textOrder) {
        this.textOrder = textOrder;
    }

    public Integer getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(Integer urlCount) {
        this.urlCount = urlCount;
    }

    public List<Link> getUrlList() {
        return urlList;
    }

    public void setUrlList(List<Link> urlList) {
        this.urlList = urlList;
    }

    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
