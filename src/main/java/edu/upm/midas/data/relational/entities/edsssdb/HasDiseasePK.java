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
 * @className HasDiseasePK
 * @see
 */
public class HasDiseasePK implements Serializable {
    private String documentId;
    private Date date;
    private String diseaseId;

    public HasDiseasePK() {
    }

    public HasDiseasePK(String documentId, Date date, String diseaseId) {
        this.documentId = documentId;
        this.date = date;
        this.diseaseId = diseaseId;
    }

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

    @Column(name = "disease_id", nullable = false, length = 150)
    @Id
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasDiseasePK that = (HasDiseasePK) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(diseaseId, that.diseaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, diseaseId);
    }
}
