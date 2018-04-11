package edu.upm.midas.data.relational.entities.edsssdb;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className Term
 * @see
 */
@Entity
@Table(name = "term", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Term.findAll", query = "SELECT t FROM Term t")
        , @NamedQuery(name = "Term.findById", query = "SELECT t FROM Term t WHERE t.termId = :termId")
        , @NamedQuery(name = "Term.findByTermId", query = "SELECT t FROM Term t WHERE t.termId = :termId")
        , @NamedQuery(name = "Term.findByName", query = "SELECT t FROM Term t WHERE t.name = :name")
        , @NamedQuery(name = "Term.findByResourceId", query = "SELECT t FROM Term t WHERE t.resourceId = :resourceId")
})
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Term.findByIdNative",
                query = "SELECT t.term_id, t.resource_id, t.name " +
                        "FROM term t WHERE t.term_id = :termId",
                resultSetMapping = "TermMapping"

        ),
        @NamedNativeQuery(
                name = "Term.findByIdNativeResultClass",
                query = "SELECT t.term_id, t.resource_id, t.name " +
                        "FROM term t WHERE t.term_id = :termId",
                resultClass = Term.class

        ),
        @NamedNativeQuery(
                name = "Term.findByNameNative",
                query = "SELECT t.term_id, t.resource_id, t.name " +
                        "FROM term t WHERE t.name COLLATE utf8_bin = :name",
                resultClass = Term.class
        ),
        @NamedNativeQuery(
                name = "Term.findIdByNameNative",
                query = "SELECT t.term_id, t.resource_id " +
                        "FROM term t WHERE t.name COLLATE utf8_bin = :name"
        ),


        @NamedNativeQuery(
                name = "Term.insertNative",
                query = "INSERT INTO term (resource_id, name) " +
                        "VALUES (:resourceId, :name)"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "TermMapping",
                entities = @EntityResult(
                        entityClass = Term.class,
                        fields = {
                                @FieldResult(name = "termId", column = "term_id"),
                                @FieldResult(name = "resourceId", column = "resource_id"),
                                @FieldResult(name = "name", column = "name")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="termId")
public class Term {
    private Integer termId;
    private Integer resourceId;
    private String name;
    private List<PaperTerm> paperTermsByTermId;
    private Resource resourceByResourceId;

    @Id
    @Column(name = "term_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }

    @Basic
    @Column(name = "resource_id", nullable = false)
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 350)
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
        Term term = (Term) o;
        return Objects.equals(termId, term.termId) &&
                Objects.equals(resourceId, term.resourceId) &&
                Objects.equals(name, term.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(termId, resourceId, name);
    }

    @OneToMany(mappedBy = "termByTermId")
    public List<PaperTerm> getPaperTermsByTermId() {
        return paperTermsByTermId;
    }

    public void setPaperTermsByTermId(List<PaperTerm> paperTermsByTermId) {
        this.paperTermsByTermId = paperTermsByTermId;
    }

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)
    public Resource getResourceByResourceId() {
        return resourceByResourceId;
    }

    public void setResourceByResourceId(Resource resourceByResourceId) {
        this.resourceByResourceId = resourceByResourceId;
    }
}
