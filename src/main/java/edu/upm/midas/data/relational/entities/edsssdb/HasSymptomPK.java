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
 * @className HasSymptomPK
 * @see
 */
public class HasSymptomPK implements Serializable {
    private String textId;
    private String cui;

    @Column(name = "text_id", nullable = false, length = 55)
    @Id
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    @Column(name = "cui", nullable = false, length = 8)
    @Id
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
        HasSymptomPK that = (HasSymptomPK) o;
        return Objects.equals(textId, that.textId) &&
                Objects.equals(cui, that.cui);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textId, cui);
    }
}
