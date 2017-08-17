package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className SourceUrl
 * @see
 */
@Entity
@Table(name = "source_url", schema = "edsssdb", catalog = "")
@IdClass(SourceUrlPK.class)
public class SourceUrl {
    private String sourceId;
    private String urlId;
    private Source sourceBySourceId;
    private Url urlByUrlId;

    @Id
    @Column(name = "source_id", nullable = false, length = 10)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
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
        SourceUrl sourceUrl = (SourceUrl) o;
        return Objects.equals(sourceId, sourceUrl.sourceId) &&
                Objects.equals(urlId, sourceUrl.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, urlId);
    }

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", nullable = false, insertable = false, updatable = false)
    public Source getSourceBySourceId() {
        return sourceBySourceId;
    }

    public void setSourceBySourceId(Source sourceBySourceId) {
        this.sourceBySourceId = sourceBySourceId;
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
