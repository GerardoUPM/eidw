package edu.upm.midas.data.relational.entities.edsssdb;

import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className SynonymCode
 * @see
 */
@Entity
@Table(name = "synonym_code", schema = "edsssdb", catalog = "")
@IdClass(SynonymCodePK.class)
public class SynonymCode {
    private Integer synonymId;
    private String code;
    private Integer resourceId;
    private Synonym synonymBySynonymId;
    private Code code_0;

    @Id
    @Column(name = "synonym_id", nullable = false)
    public Integer getSynonymId() {
        return synonymId;
    }

    public void setSynonymId(Integer synonymId) {
        this.synonymId = synonymId;
    }

    @Id
    @Column(name = "code", nullable = false, length = 150)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Id
    @Column(name = "resource_id", nullable = false)
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SynonymCode that = (SynonymCode) o;
        return Objects.equals(synonymId, that.synonymId) &&
                Objects.equals(code, that.code) &&
                Objects.equals(resourceId, that.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(synonymId, code, resourceId);
    }

    @ManyToOne
    @JoinColumn(name = "synonym_id", referencedColumnName = "synonym_id", nullable = false, insertable = false, updatable = false)
    public Synonym getSynonymBySynonymId() {
        return synonymBySynonymId;
    }

    public void setSynonymBySynonymId(Synonym synonymBySynonymId) {
        this.synonymBySynonymId = synonymBySynonymId;
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "code", referencedColumnName = "code", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "resource_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)})
    public Code getCode_0() {
        return code_0;
    }

    public void setCode_0(Code code_0) {
        this.code_0 = code_0;
    }
}
