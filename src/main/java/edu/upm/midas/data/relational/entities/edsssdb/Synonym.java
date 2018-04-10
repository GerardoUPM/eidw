package edu.upm.midas.data.relational.entities.edsssdb;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className Synonym
 * @see
 */
@Entity
public class Synonym {
    private Integer synonymId;
    private String name;
    private List<DiseaseSynonym> diseaseSynonymsBySynonymId;
    private List<SynonymCode> synonymCodesBySynonymId;

    @Id
    @Column(name = "synonym_id", nullable = false)
    public Integer getSynonymId() {
        return synonymId;
    }

    public void setSynonymId(Integer synonymId) {
        this.synonymId = synonymId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Synonym synonym = (Synonym) o;
        return Objects.equals(synonymId, synonym.synonymId) &&
                Objects.equals(name, synonym.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(synonymId, name);
    }

    @OneToMany(mappedBy = "synonymBySynonymId")
    public List<DiseaseSynonym> getDiseaseSynonymsBySynonymId() {
        return diseaseSynonymsBySynonymId;
    }

    public void setDiseaseSynonymsBySynonymId(List<DiseaseSynonym> diseaseSynonymsBySynonymId) {
        this.diseaseSynonymsBySynonymId = diseaseSynonymsBySynonymId;
    }

    @OneToMany(mappedBy = "synonymBySynonymId")
    public List<SynonymCode> getSynonymCodesBySynonymId() {
        return synonymCodesBySynonymId;
    }

    public void setSynonymCodesBySynonymId(List<SynonymCode> synonymCodesBySynonymId) {
        this.synonymCodesBySynonymId = synonymCodesBySynonymId;
    }
}
