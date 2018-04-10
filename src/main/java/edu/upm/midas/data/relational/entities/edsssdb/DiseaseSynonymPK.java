package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DiseaseSynonymPK
 * @see
 */
public class DiseaseSynonymPK implements Serializable {
    private String diseaseId;
    private Integer synonymId;

    @Column(name = "disease_id", nullable = false, length = 150)
    @Id
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Column(name = "synonym_id", nullable = false)
    @Id
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
        DiseaseSynonymPK that = (DiseaseSynonymPK) o;
        return Objects.equals(diseaseId, that.diseaseId) &&
                Objects.equals(synonymId, that.synonymId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diseaseId, synonymId);
    }
}
