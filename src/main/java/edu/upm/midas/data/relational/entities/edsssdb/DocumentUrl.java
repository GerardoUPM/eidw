package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className DocumentUrl
 * @see
 */
@Entity
@Table(name = "document_url", schema = "edsssdb", catalog = "")
@IdClass(DocumentUrlPK.class)
public class DocumentUrl {
    private String documentId;
    private Date date;
    private String urlId;
    private Document document;
    private Url urlByUrlId;

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
    @Column(name = "url_id", nullable = false, length = 250)
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DocumentUrl that = (DocumentUrl) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(urlId, that.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, urlId);
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
    @JoinColumn(name = "url_id", referencedColumnName = "url_id", nullable = false, insertable = false, updatable = false)
    public Url getUrlByUrlId() {
        return urlByUrlId;
    }

    public void setUrlByUrlId(Url urlByUrlId) {
        this.urlByUrlId = urlByUrlId;
    }
}
