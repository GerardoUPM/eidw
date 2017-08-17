package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className DocumentUrlPK
 * @see
 */
public class DocumentUrlPK implements Serializable {
    private String documentId;
    private Date date;
    private String urlId;

    @Column(name = "document_id", nullable = false, length = 30)
    @Id
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Column(name = "date", nullable = false)
    @Id
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        DocumentUrlPK that = (DocumentUrlPK) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(urlId, that.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, urlId);
    }
}
