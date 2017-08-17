package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className HasCodePK
 * @see
 */
public class HasCodePK implements Serializable {
    private String documentId;
    private Date date;
    private String code;
    private Integer resourceId;

    @Column(name = "document_id", nullable = false, length = 30)
    @Id
    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    @Column(name = "date", nullable = false)
    @Id
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "code", nullable = false, length = 150)
    @Id
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = "resource_id", nullable = false)
    @Id
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
        HasCodePK hasCodePK = (HasCodePK) o;
        return Objects.equals(documentId, hasCodePK.documentId) &&
                Objects.equals(date, hasCodePK.date) &&
                Objects.equals(code, hasCodePK.code) &&
                Objects.equals(resourceId, hasCodePK.resourceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, code, resourceId);
    }
}
