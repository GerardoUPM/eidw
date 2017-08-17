package edu.upm.midas.data.extraction.model;

import edu.upm.midas.enums.StatusHttpEnum;
import org.jsoup.nodes.Document;

/**
 * Created by gerardo on 04/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className Connect
 * @see
 */
public class Connect {

    private String link;
    private String status;
    private Document oDoc;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setsStatusEnum(StatusHttpEnum sStatus) {
        this.status = sStatus.getClave();
    }

    public Document getoDoc() {
        return oDoc;
    }

    public void setoDoc(Document oDoc) {
        this.oDoc = oDoc;
    }
}
