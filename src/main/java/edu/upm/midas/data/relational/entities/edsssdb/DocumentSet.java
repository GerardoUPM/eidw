package edu.upm.midas.data.relational.entities.edsssdb;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 10/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DocumentSet
 * @see
 */
@Entity
@Table(name = "document_set", schema = "edsssdb", catalog = "")

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "DocumentSetMapping",
                entities = @EntityResult(
                        entityClass = DocumentSet.class,
                        fields = {
                                @FieldResult(name = "documentId", column = "document_id"),
                                @FieldResult(name = "date", column = "date"),
                                @FieldResult(name = "paperId", column = "paper_id")
                        }
                )
        )
})

@IdClass(DocumentSetPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="DocumentSetPK")
public class DocumentSet {
    private String documentId;
    private Date date;
    private String paperId;
    private Document document;
    private Paper paperByPaperId;

    @Id
    @Column(name = "document_id", nullable = false, length = 30)
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Id
    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Id
    @Column(name = "paper_id", nullable = false, length = 250)
    public String getPaperId() {
        return paperId;
    }

    public void setPaperId(String paperId) {
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentSet that = (DocumentSet) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(paperId, that.paperId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, paperId);
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "document_id", referencedColumnName = "document_id", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "date", referencedColumnName = "date", nullable = false, insertable = false, updatable = false)})
    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "paper_id", nullable = false, insertable = false, updatable = false)
    public Paper getPaperByPaperId() {
        return paperByPaperId;
    }

    public void setPaperByPaperId(Paper paperByPaperId) {
        this.paperByPaperId = paperByPaperId;
    }
}
