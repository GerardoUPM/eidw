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
 * @className HasCode
 * @see
 */
@Entity
@Table(name = "has_code", schema = "edsssdb", catalog = "")
@IdClass(HasCodePK.class)
public class HasCode {
    private String documentId;
    private Date date;
    private String code;
    private Integer resourceId;
    private Document document;
    private Code code_0;

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
    @Column(name = "code", nullable = false, length = 150)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Id
    @Column(name = "resource_id", nullable = false)
    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasCode hasCode = (HasCode) o;
        return Objects.equals(documentId, hasCode.documentId) &&
                Objects.equals(date, hasCode.date) &&
                Objects.equals(code, hasCode.code) &&
                Objects.equals(resourceId, hasCode.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, code, resourceId);
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
    @JoinColumns({@JoinColumn(name = "code", referencedColumnName = "code", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "resource_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)})
    public Code getCode_0() {
        return code_0;
    }

    public void setCode_0(Code code_0) {
        this.code_0 = code_0;
    }
}
