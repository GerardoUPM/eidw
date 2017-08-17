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
 * @className HasSemanticTypePK
 * @see
 */
public class HasSemanticTypePK implements Serializable {
    private String cui;
    private String semanticType;

    @Column(name = "cui", nullable = false, length = 8)
    @Id
    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    @Column(name = "semantic_type", nullable = false, length = 8)
    @Id
    public String getSemanticType() {
        return semanticType;
    }

    public void setSemanticType(String semanticType) {
        this.semanticType = semanticType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasSemanticTypePK that = (HasSemanticTypePK) o;
        return Objects.equals(cui, that.cui) &&
                Objects.equals(semanticType, that.semanticType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cui, semanticType);
    }
}
