package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className CodeUrlPK
 * @see
 */
public class CodeUrlPK implements Serializable {
    private String code;
    private Integer resourceId;
    private String urlId;

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

    @Column(name = "url_id", nullable = false, length = 250)
    @Id
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
        CodeUrlPK codeUrlPK = (CodeUrlPK) o;
        return Objects.equals(code, codeUrlPK.code) &&
                Objects.equals(resourceId, codeUrlPK.resourceId) &&
                Objects.equals(urlId, codeUrlPK.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, resourceId, urlId);
    }
}
