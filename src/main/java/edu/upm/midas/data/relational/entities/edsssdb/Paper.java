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
 * @className Paper
 * @see
 */
@Entity
public class Paper {
    private String paperId;
    private String doi;
    private String alternativeId;
    private String title;
    private String authors;
    private String keywords;
    private Byte freeText;
    private List<DocumentSet> documentSetsByPaperId;
    private List<PaperTerm> paperTermsByPaperId;
    private List<PaperUrl> paperUrlsByPaperId;

    @Id
    @Column(name = "paper_id", nullable = false, length = 255)
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Basic
    @Column(name = "doi", nullable = true, length = 255)
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Basic
    @Column(name = "alternative_id", nullable = true, length = 255)
    public String getAlternativeId() {
        return alternativeId;
    }

    public void setAlternativeId(String alternativeId) {
        this.alternativeId = alternativeId;
    }

    @Basic
    @Column(name = "title", nullable = false, length = 3000)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "authors", nullable = true, length = 3000)
    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    @Basic
    @Column(name = "keywords", nullable = true, length = 3000)
    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Basic
    @Column(name = "free_text", nullable = true)
    public Byte getFreeText() {
        return freeText;
    }

    public void setFreeText(Byte freeText) {
        this.freeText = freeText;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper paper = (Paper) o;
        return Objects.equals(paperId, paper.paperId) &&
                Objects.equals(doi, paper.doi) &&
                Objects.equals(alternativeId, paper.alternativeId) &&
                Objects.equals(title, paper.title) &&
                Objects.equals(authors, paper.authors) &&
                Objects.equals(keywords, paper.keywords) &&
                Objects.equals(freeText, paper.freeText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, doi, alternativeId, title, authors, keywords, freeText);
    }

    @OneToMany(mappedBy = "paperByPaperId")
    public List<DocumentSet> getDocumentSetsByPaperId() {
        return documentSetsByPaperId;
    }

    public void setDocumentSetsByPaperId(List<DocumentSet> documentSetsByPaperId) {
        this.documentSetsByPaperId = documentSetsByPaperId;
    }

    @OneToMany(mappedBy = "paperByPaperId")
    public List<PaperTerm> getPaperTermsByPaperId() {
        return paperTermsByPaperId;
    }

    public void setPaperTermsByPaperId(List<PaperTerm> paperTermsByPaperId) {
        this.paperTermsByPaperId = paperTermsByPaperId;
    }

    @OneToMany(mappedBy = "paperByPaperId")
    public List<PaperUrl> getPaperUrlsByPaperId() {
        return paperUrlsByPaperId;
    }

    public void setPaperUrlsByPaperId(List<PaperUrl> paperUrlsByPaperId) {
        this.paperUrlsByPaperId = paperUrlsByPaperId;
    }
}
