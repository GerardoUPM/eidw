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
 * @className TextUrlPK
 * @see
 */
public class TextUrlPK implements Serializable {
    private String textId;
    private String urlId;

    @Column(name = "text_id", nullable = false, length = 55)
    @Id
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
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
        TextUrlPK textUrlPK = (TextUrlPK) o;
        return Objects.equals(textId, textUrlPK.textId) &&
                Objects.equals(urlId, textUrlPK.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textId, urlId);
    }
}
