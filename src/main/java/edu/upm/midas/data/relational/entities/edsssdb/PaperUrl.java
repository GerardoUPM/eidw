package edu.upm.midas.data.relational.entities.edsssdb;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperUrl
 * @see
 */
@Entity
@Table(name = "paper_url", schema = "edsssdb", catalog = "")
@IdClass(PaperUrlPK.class)
public class PaperUrl {
    private String paperId;
    private String urlId;
    private Paper paperByPaperId;
    private Url urlByUrlId;

    @Id
    @Column(name = "paper_id", nullable = false, length = 250)
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "url_id", nullable = false, length = 250)
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperUrl paperUrl = (PaperUrl) o;
        return Objects.equals(paperId, paperUrl.paperId) &&
                Objects.equals(urlId, paperUrl.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, urlId);
    }

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "paper_id", nullable = false, insertable = false, updatable = false)
    public Paper getPaperByPaperId() {
        return paperByPaperId;
    }

    public void setPaperByPaperId(Paper paperByPaperId) {
        this.paperByPaperId = paperByPaperId;
    }

    @ManyToOne
    @JoinColumn(name = "url_id", referencedColumnName = "url_id", nullable = false, insertable = false, updatable = false)
    public Url getUrlByUrlId() {
        return urlByUrlId;
    }

    public void setUrlByUrlId(Url urlByUrlId) {
        this.urlByUrlId = urlByUrlId;
    }
}
