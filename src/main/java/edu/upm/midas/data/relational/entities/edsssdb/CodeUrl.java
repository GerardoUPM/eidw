package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className CodeUrl
 * @see
 */
@Entity
@Table(name = "code_url", schema = "edsssdb", catalog = "")
@IdClass(CodeUrlPK.class)
public class CodeUrl {
    private String code;
    private Integer resourceId;
    private String urlId;
    private Code code_0;
    private Url urlByUrlId;

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
        CodeUrl codeUrl = (CodeUrl) o;
        return Objects.equals(code, codeUrl.code) &&
                Objects.equals(resourceId, codeUrl.resourceId) &&
                Objects.equals(urlId, codeUrl.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, resourceId, urlId);
    }

    @ManyToOne
    @JoinColumns({@JoinColumn(name = "code", referencedColumnName = "code", nullable = false, insertable = false, updatable = false), @JoinColumn(name = "resource_id", referencedColumnName = "resource_id", nullable = false, insertable = false, updatable = false)})
    public Code getCode_0() {
        return code_0;
    }

    public void setCode_0(Code code_0) {
        this.code_0 = code_0;
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
