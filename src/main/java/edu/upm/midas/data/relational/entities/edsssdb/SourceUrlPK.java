package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className SourceUrlPK
 * @see
 */
public class SourceUrlPK implements Serializable {
    private String sourceId;
    private String urlId;

    @Column(name = "source_id", nullable = false, length = 10)
    @Id
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Column(name = "url_id", nullable = false, length = 250)
    @Id
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
        SourceUrlPK that = (SourceUrlPK) o;
        return Objects.equals(sourceId, that.sourceId) &&
                Objects.equals(urlId, that.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, urlId);
    }
}
