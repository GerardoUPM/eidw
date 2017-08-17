package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className HasSource
 * @see
 */
@Entity
@Table(name = "has_source", schema = "edsssdb", catalog = "")
@IdClass(HasSourcePK.class)
public class HasSource {
    private String documentId;
    private Date date;
    private String sourceId;
    private Document document;
    private Source sourceBySourceId;

    @Id
    @Column(name = "document_id", nullable = false, length = 30)
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Id
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    @Column(name = "source_id", nullable = false, length = 10)
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
        HasSource hasSource = (HasSource) o;
        return Objects.equals(documentId, hasSource.documentId) &&
                Objects.equals(date, hasSource.date) &&
                Objects.equals(sourceId, hasSource.sourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, sourceId);
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "document_id", referencedColumnName = "document_id", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "date", referencedColumnName = "date", nullable = false, insertable = false, updatable = false)})
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @ManyToOne
    @JoinColumn(name = "source_id", referencedColumnName = "source_id", nullable = false, insertable = false, updatable = false)
    public Source getSourceBySourceId() {
        return sourceBySourceId;
    }

    public void setSourceBySourceId(Source sourceBySourceId) {
        this.sourceBySourceId = sourceBySourceId;
    }
}
