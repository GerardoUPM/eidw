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
 * @className HasSectionPK
 * @see
 */
public class HasSectionPK implements Serializable {
    private String documentId;
    private Date date;
    private String sectionId;

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

    @Column(name = "section_id", nullable = false, length = 10)
    @Id
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasSectionPK that = (HasSectionPK) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(sectionId, that.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, sectionId);
    }
}
