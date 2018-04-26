package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "HasDisease.findAll", query = "SELECT h FROM HasDisease h")
        , @NamedQuery(name = "HasDisease.findById", query = "SELECT h FROM HasDisease h WHERE h.documentId = :documentId AND h.date = :date AND h.diseaseId = :diseaseId")
        , @NamedQuery(name = "HasDisease.findByDocumentId", query = "SELECT h FROM HasDisease h WHERE h.documentId = :documentId")
        , @NamedQuery(name = "HasDisease.findByDate", query = "SELECT h FROM HasDisease h WHERE h.date = :date")
        , @NamedQuery(name = "HasDisease.findByDiseaseId", query = "SELECT h FROM HasDisease h WHERE h.diseaseId = :diseaseId")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "HasDisease.findByIdNative",
                query = "SELECT h.document_id, h.date, h.section_id "
                        + "FROM has_disease h WHERE h.document_id = :documentId AND h.date = :date AND h.disease_id = :diseaseId",
                resultSetMapping="HasDiseaseMapping"

        ),
        @NamedNativeQuery(
                name = "HasDisease.findByIdNativeResultClass",
                query = "SELECT h.document_id, h.date, h.section_id "
                        + "FROM has_disease h WHERE h.document_id = :documentId AND h.date = :date AND h.disease_id = :diseaseId",
                resultClass = HasDisease.class
        )


        ,
        @NamedNativeQuery(
                name = "HasDisease.insertNative__",
                query = "INSERT INTO has_section (document_id, date, disease_id) "
                        + "VALUES (:documentId, :date, :diseaseId)"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "HasDiseaseMapping",
                entities = @EntityResult(
                        entityClass = HasDisease.class,
                        fields = {
                                @FieldResult(name = "documentId", column = "document_id"),
                                @FieldResult(name = "date", column = "date"),
                                @FieldResult(name = "diseaseId", column = "disease_id")
                        }
                )
        )
})
@IdClass(HasDiseasePK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="HasDiseasePK")
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
