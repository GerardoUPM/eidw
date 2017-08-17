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
 * @className HasSourcePK
 * @see
 */
public class HasSourcePK implements Serializable {
    private String documentId;
    private Date date;
    private String sourceId;

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

    @Column(name = "source_id", nullable = false, length = 10)
    @Id
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasSourcePK that = (HasSourcePK) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(sourceId, that.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, sourceId);
    }
}
