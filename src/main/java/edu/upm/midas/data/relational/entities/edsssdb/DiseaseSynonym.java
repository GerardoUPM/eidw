package edu.upm.midas.data.relational.entities.edsssdb;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DiseaseSynonym
 * @see
 */
@Entity
@Table(name = "disease_synonym", schema = "edsssdb", catalog = "")
@IdClass(DiseaseSynonymPK.class)
public class DiseaseSynonym {
    private String diseaseId;
    private Integer synonymId;
    private Disease diseaseByDiseaseId;
    private Synonym synonymBySynonymId;

    @Id
    @Column(name = "disease_id", nullable = false, length = 150)
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Id
    @Column(name = "synonym_id", nullable = false)
    public Integer getSynonymId() {
        return synonymId;
    }

    public void setSynonymId(Integer synonymId) {
        this.synonymId = synonymId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiseaseSynonym that = (DiseaseSynonym) o;
        return Objects.equals(diseaseId, that.diseaseId) &&
                Objects.equals(synonymId, that.synonymId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diseaseId, synonymId);
    }

    @ManyToOne
    @JoinColumn(name = "disease_id", referencedColumnName = "disease_id", nullable = false, insertable = false, updatable = false)
    public Disease getDiseaseByDiseaseId() {
        return diseaseByDiseaseId;
    }

    public void setDiseaseByDiseaseId(Disease diseaseByDiseaseId) {
        this.diseaseByDiseaseId = diseaseByDiseaseId;
    }

    @ManyToOne
    @JoinColumn(name = "synonym_id", referencedColumnName = "synonym_id", nullable = false, insertable = false, updatable = false)
    public Synonym getSynonymBySynonymId() {
        return synonymBySynonymId;
    }

    public void setSynonymBySynonymId(Synonym synonymBySynonymId) {
        this.synonymBySynonymId = synonymBySynonymId;
    }
}
