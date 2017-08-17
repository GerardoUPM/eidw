package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className CodePK
 * @see
 */
//@Embeddable
public class CodePK implements Serializable {
    private String code;
    private Integer resourceId;

    @Column(name = "code", nullable = false, length = 150)
    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "resource_id", nullable = false)
    @Id
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
        CodePK codePK = (CodePK) o;
        return Objects.equals(code, codePK.code) &&
                Objects.equals(resourceId, codePK.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, resourceId);
    }
}
