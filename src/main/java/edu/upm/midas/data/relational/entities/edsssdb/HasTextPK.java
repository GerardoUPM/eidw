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
 * @className HasTextPK
 * @see
 */
public class HasTextPK implements Serializable {
    private String documentId;
    private Date date;
    private String sectionId;
    private String textId;

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

    @Column(name = "text_id", nullable = false, length = 55)
    @Id
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasTextPK hasTextPK = (HasTextPK) o;
        return Objects.equals(documentId, hasTextPK.documentId) &&
                Objects.equals(date, hasTextPK.date) &&
                Objects.equals(sectionId, hasTextPK.sectionId) &&
                Objects.equals(textId, hasTextPK.textId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, sectionId, textId);
    }
}
