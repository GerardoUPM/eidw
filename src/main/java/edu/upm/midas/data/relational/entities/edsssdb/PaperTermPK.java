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
 * @className PaperTermPK
 * @see
 */
public class PaperTermPK implements Serializable {
    private String paperId;
    private Integer termId;

    @Column(name = "paper_id", nullable = false, length = 250)
    @Id
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Column(name = "term_id", nullable = false)
    @Id
    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTermPK that = (PaperTermPK) o;
        return Objects.equals(paperId, that.paperId) &&
                Objects.equals(termId, that.termId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }
}
