package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperTerm
 * @see
 */
@Entity
@Table(name = "paper_term", schema = "edsssdb", catalog = "")
@IdClass(PaperTermPK.class)
public class PaperTerm {
    private String paperId;
    private Integer termId;
    private Paper paperByPaperId;
    private Term termByTermId;

    @Id
    @Column(name = "paper_id", nullable = false, length = 250)
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "term_id", nullable = false)
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
        PaperTerm paperTerm = (PaperTerm) o;
        return Objects.equals(paperId, paperTerm.paperId) &&
                Objects.equals(termId, paperTerm.termId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "paper_id", nullable = false, insertable = false, updatable = false)
    public Paper getPaperByPaperId() {
        return paperByPaperId;
    }

    public void setPaperByPaperId(Paper paperByPaperId) {
        this.paperByPaperId = paperByPaperId;
    }

    @ManyToOne
    @JoinColumn(name = "term_id", referencedColumnName = "term_id", nullable = false, insertable = false, updatable = false)
    public Term getTermByTermId() {
        return termByTermId;
    }

    public void setTermByTermId(Term termByTermId) {
        this.termByTermId = termByTermId;
    }
}
