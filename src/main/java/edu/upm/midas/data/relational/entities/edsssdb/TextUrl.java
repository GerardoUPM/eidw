package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.*;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className TextUrl
 * @see
 */
@Entity
@Table(name = "text_url", schema = "edsssdb", catalog = "")
@IdClass(TextUrlPK.class)
public class TextUrl {
    private String textId;
    private String urlId;
    private Text textByTextId;
    private Url urlByUrlId;

    @Id
    @Column(name = "text_id", nullable = false, length = 255)
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
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
        TextUrl textUrl = (TextUrl) o;
        return Objects.equals(textId, textUrl.textId) &&
                Objects.equals(urlId, textUrl.urlId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textId, urlId);
    }

    @ManyToOne
    @JoinColumn(name = "text_id", referencedColumnName = "text_id", nullable = false, insertable = false, updatable = false)
    public Text getTextByTextId() {
        return textByTextId;
    }

    public void setTextByTextId(Text textByTextId) {
        this.textByTextId = textByTextId;
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
