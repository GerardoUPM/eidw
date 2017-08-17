package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className Url
 * @see
 */
@Entity
@Table(name = "url", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Url.findAll", query = "SELECT u FROM Url u")
        , @NamedQuery(name = "Url.findById", query = "SELECT u FROM Url u WHERE u.urlId = :urlId")
        , @NamedQuery(name = "Url.findByUrlId", query = "SELECT u FROM Url u WHERE u.urlId = :urlId")
        , @NamedQuery(name = "Url.findByUrl", query = "SELECT u FROM Url u WHERE u.url = :url")
        , @NamedQuery(name = "Url.updateById", query = "UPDATE Url u SET u.url = :url WHERE u.urlId = :urlId")
        , @NamedQuery(name = "Url.findLastUrl", query = "SELECT u FROM Url u ORDER BY u.urlId DESC")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Url.findByIdNative",
                query = "SELECT u.url_id, u.url "
                        + "FROM url u WHERE u.url_id = :urlId",
                resultSetMapping="UrlMapping"

        ),
        @NamedNativeQuery(
                name = "Url.findByIdNativeResultClass",
                query = "SELECT u.url_id, u.url "
                        + "FROM url u WHERE u.url_id = :urlId",
                resultClass = Url.class
        ),
        @NamedNativeQuery(
                name = "Url.findByUrlNativeResultClass",
                query = "SELECT u.url_id, u.url "
                        + "FROM url u WHERE u.url COLLATE utf8_bin = :url",
                resultClass = Url.class
        ),


        @NamedNativeQuery(
                name = "Url.insertNative",
                query = "INSERT INTO url (url_id, url) "
                        + "VALUES (:urlId, :url)"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "UrlMapping",
                entities = @EntityResult(
                        entityClass = Url.class,
                        fields = {
                                @FieldResult(name = "urlId", column = "url_id"),
                                @FieldResult(name = "url", column = "url")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="urlId")
public class Url {
    private String urlId;
    private String url;
    private List<CodeUrl> codeUrlsByUrlId;
    private List<DocumentUrl> documentUrlsByUrlId;
    private List<SourceUrl> sourceUrlsByUrlId;
    private List<TextUrl> textUrlsByUrlId;

    @Id
    @Column(name = "url_id", nullable = false, length = 250)
    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    @Basic
    @Column(name = "url", nullable = false, length = 350)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url1 = (Url) o;
        return Objects.equals(urlId, url1.urlId) &&
                Objects.equals(url, url1.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlId, url);
    }

    @OneToMany(mappedBy = "urlByUrlId")
    public List<CodeUrl> getCodeUrlsByUrlId() {
        return codeUrlsByUrlId;
    }

    public void setCodeUrlsByUrlId(List<CodeUrl> codeUrlsByUrlId) {
        this.codeUrlsByUrlId = codeUrlsByUrlId;
    }

    @OneToMany(mappedBy = "urlByUrlId")
    public List<DocumentUrl> getDocumentUrlsByUrlId() {
        return documentUrlsByUrlId;
    }

    public void setDocumentUrlsByUrlId(List<DocumentUrl> documentUrlsByUrlId) {
        this.documentUrlsByUrlId = documentUrlsByUrlId;
    }

    @OneToMany(mappedBy = "urlByUrlId")
    public List<SourceUrl> getSourceUrlsByUrlId() {
        return sourceUrlsByUrlId;
    }

    public void setSourceUrlsByUrlId(List<SourceUrl> sourceUrlsByUrlId) {
        this.sourceUrlsByUrlId = sourceUrlsByUrlId;
    }

    @OneToMany(mappedBy = "urlByUrlId")
    public List<TextUrl> getTextUrlsByUrlId() {
        return textUrlsByUrlId;
    }

    public void setTextUrlsByUrlId(List<TextUrl> textUrlsByUrlId) {
        this.textUrlsByUrlId = textUrlsByUrlId;
    }
}
