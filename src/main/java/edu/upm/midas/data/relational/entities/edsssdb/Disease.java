package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className Disease
 * @see
 */
@Entity
@Table(name = "disease", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Disease.findAll", query = "SELECT d FROM Disease d")
        , @NamedQuery(name = "Disease.findById", query = "SELECT d FROM Disease d WHERE d.diseaseId = :diseaseId")
        , @NamedQuery(name = "Disease.findByDiseaseId", query = "SELECT d FROM Disease d WHERE d.diseaseId = :diseaseId")
        , @NamedQuery(name = "Disease.findByName", query = "SELECT d FROM Disease d WHERE d.name = :name")
        , @NamedQuery(name = "Disease.findByCui", query = "SELECT d FROM Disease d WHERE d.cui = :cui")
        , @NamedQuery(name = "Disease.updateById", query = "UPDATE Disease d SET d.name = :name, d.cui = :cui  WHERE d.diseaseId = :diseaseId")
        , @NamedQuery(name = "Disease.findLastDisease", query = "SELECT d FROM Disease d ORDER BY d.diseaseId DESC")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Disease.findByIdNative",
                query = "SELECT d.disease_id, d.name, d.cui "
                        + "FROM disease d WHERE d.disease_id COLLATE utf8_bin = :diseaseId",
                resultSetMapping="DiseaseMapping"

        ),
        @NamedNativeQuery(
                name = "Disease.findByIdNativeResultClass",
                query = "SELECT d.disease_id, d.name, d.cui "
                        + "FROM disease d WHERE d.disease_id COLLATE utf8_bin = :diseaseId",
                resultClass = Disease.class

        ),
        @NamedNativeQuery(
                name = "Disease.findByNameNativeResultClass",
                query = "SELECT d.disease_id, d.name, d.cui "
                        + "FROM disease d WHERE d.name COLLATE utf8_bin = :name",
                resultClass = Disease.class
        ),


        @NamedNativeQuery(
                name = "Disease.insertNative",
                query = "INSERT INTO disease (disease_id, name, cui) "
                        + "VALUES (:diseaseId, :name, :cui)"
        ),
        @NamedNativeQuery(
                name = "HasDisease.insertNative",
                query = "INSERT INTO has_disease (document_id, date, disease_id) "
                        + "VALUES (:documentId, :date, :diseaseId)"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "DiseaseMapping",
                entities = @EntityResult(
                        entityClass = Disease.class,
                        fields = {
                                @FieldResult(name = "diseaseId", column = "disease_id"),
                                @FieldResult(name = "name", column = "name"),
                                @FieldResult(name = "cui", column = "cui")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="diseaseId")
public class Disease {
    private String diseaseId;
    private String name;
    private String cui;
    private List<HasDisease> hasDiseasesByDiseaseId;

    @Id
    @Column(name = "disease_id", nullable = false, length = 150)
    public String getDiseaseId() {
        return diseaseId;
    }

    public void setDiseaseId(String diseaseId) {
        this.diseaseId = diseaseId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "cui", nullable = true, length = 8)
    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Disease disease = (Disease) o;
        return Objects.equals(diseaseId, disease.diseaseId) &&
                Objects.equals(name, disease.name) &&
                Objects.equals(cui, disease.cui);
    }

    @Override
    public int hashCode() {
        return Objects.hash(diseaseId, name, cui);
    }

    @OneToMany(mappedBy = "diseaseByDiseaseId")
    public List<HasDisease> getHasDiseasesByDiseaseId() {
        return hasDiseasesByDiseaseId;
    }

    public void setHasDiseasesByDiseaseId(List<HasDisease> hasDiseasesByDiseaseId) {
        this.hasDiseasesByDiseaseId = hasDiseasesByDiseaseId;
    }
}
