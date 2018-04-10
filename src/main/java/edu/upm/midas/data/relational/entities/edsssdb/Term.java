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
 * @className Term
 * @see
 */
@Entity
public class Term {
    private Integer termId;
    private Integer resourceId;
    private String name;
    private List<PaperTerm> paperTermsByTermId;
    private Resource resourceByResourceId;

    @Id
    @Column(name = "term_id", nullable = false)
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
