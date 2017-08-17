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
 * @className HasDisease
 * @see
 */
@Entity
@Table(name = "has_disease", schema = "edsssdb", catalog = "")
@IdClass(HasDiseasePK.class)
public class HasDisease {
    private String documentId;
    private Date date;
    private String diseaseId;
    private Document document;
    private Disease diseaseByDiseaseId;

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
    @Column(name = "disease_id", nullable = false, length = 150)
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
        HasDisease that = (HasDisease) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(diseaseId, that.diseaseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, diseaseId);
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
    @JoinColumn(name = "disease_id", referencedColumnName = "disease_id", nullable = false, insertable = false, updatable = false)
    public Disease getDiseaseByDiseaseId() {
        return diseaseByDiseaseId;
    }

    public void setDiseaseByDiseaseId(Disease diseaseByDiseaseId) {
        this.diseaseByDiseaseId = diseaseByDiseaseId;
    }
}
