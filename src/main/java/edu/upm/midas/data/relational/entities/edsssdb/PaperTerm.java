package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
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
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PaperTerm.findAll", query = "SELECT p FROM PaperTerm p ")
        , @NamedQuery(name = "PaperTerm.findById", query = "SELECT p FROM PaperTerm p  WHERE p.paperId = :paperId AND p.termId = :termId")
        , @NamedQuery(name = "PaperTerm.findByPaperId", query = "SELECT p FROM PaperTerm p  WHERE p.paperId = :paperId")
        , @NamedQuery(name = "PaperTerm.findByTermId", query = "SELECT p FROM PaperTerm p  WHERE p.termId = :termId")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "PaperTerm.findByIdNative",
                query = "SELECT p.paper_id, p.term_id " +
                        "FROM paper_term p WHERE p.paper_id = :paperId AND p.term_id = :termId",
                resultSetMapping="PaperTermMapping"

        ),
        @NamedNativeQuery(
                name = "PaperTerm.findByIdNativeResultClass",
                query = "SELECT p.paper_id, p.term_id " +
                        "FROM paper_term p WHERE p.paper_id = :paperId AND p.term_id = :termId",
                resultClass = PaperTerm.class
        )

        ,
        @NamedNativeQuery(
                name = "PaperTerm.insertNative",
                query = "INSERT INTO paper_term (paper_id, term_id) " +
                        "VALUES (:paperId, :termId)"
        )
})
@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "PaperTermMapping",
                entities = @EntityResult(
                        entityClass = PaperTerm.class,
                        fields = {
                                @FieldResult(name = "paperId", column = "paper_id"),
                                @FieldResult(name = "termId", column = "term_id")
                        }
                )
        )
})

@IdClass(PaperTermPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="PaperTermPK")
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
