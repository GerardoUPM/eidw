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
 * @className Text
 * @see
 */
@Entity
@Table(name = "text", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Text.findAll", query = "SELECT t FROM Text t")
        , @NamedQuery(name = "Text.findById", query = "SELECT t FROM Text t WHERE t.textId = :textId")
        , @NamedQuery(name = "Text.findByTextId", query = "SELECT t FROM Text t WHERE t.textId = :textId")
        , @NamedQuery(name = "Text.findByContentType", query = "SELECT t FROM Text t WHERE t.contentType = :contentType")
        , @NamedQuery(name = "Text.updateById", query = "UPDATE Text t SET t.contentType = :contentType, t.text = :text WHERE t.textId = :textId")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Text.findByIdNative",
                query = "SELECT t.text_id, t.content_type, t.text "
                        + "FROM text t WHERE t.text_id = :textId",
                resultSetMapping="TextMapping"

        ),
        @NamedNativeQuery(
                name = "Text.findByIdNativeResultClass",
                query = "SELECT t.text_id, t.content_type, t.text "
                        + "FROM text t WHERE t.text_id = :textId",
                resultClass = Text.class
        ),


        @NamedNativeQuery(
                name = "Text.insertNative",
                query = "INSERT INTO text (text_id, content_type, text) "
                        + "VALUES (:textId, :contentType, :text)"
        ),
        @NamedNativeQuery(
                name = "HasText.insertNative_",
                query = "INSERT INTO has_text (document_id, date, section_id, text_id, text_order) "
                        + "VALUES (:documentId, :date, :sectionId, :textId, :textOrder)"
        ),
        @NamedNativeQuery(
                name = "TextUrl.insertNative",
                query = "INSERT INTO text_url (text_id, url_id) "
                        + "VALUES (:textId, :urlId)"
        ),


        @NamedNativeQuery(
                name = "Text.findBySourceAndVersionNative",
                query = "SELECT s.source_id, s.name, ht.document_id, ht.date, t.text_id, t.content_type, t.text " +
                        "FROM has_text ht " +
                        "LEFT JOIN text t ON t.text_id = ht.text_id " +
                        "LEFT JOIN has_source hs ON hs.document_id = ht.document_id AND hs.date = ht.date " +
                        "LEFT JOIN source s ON s.source_id = hs.source_id " +
                        "WHERE ht.date = :version " +
                        "AND s.name = :source " +
                        "AND t.text IS NOT NULL " +
                        "AND LENGTH(t.text) > 0 " //EVITAR VALORES VACIOS
        )





})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "TextMapping",
                entities = @EntityResult(
                        entityClass = Text.class,
                        fields = {
                                @FieldResult(name = "textId", column = "text_id"),
                                @FieldResult(name = "contentType", column = "content_type"),
                                @FieldResult(name = "text", column = "text")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="textId")
public class Text {
    private String textId;
    private String contentType;
    private String text;
    private List<HasSymptom> hasSymptomsByTextId;
    private List<HasText> hasTextsByTextId;
    private List<TextUrl> textUrlsByTextId;

    @Id
    @Column(name = "text_id", nullable = false, length = 55)
    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    @Basic
    @Column(name = "content_type", nullable = false, length = 10)
    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Basic
    @Column(name = "text", nullable = false, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Text text1 = (Text) o;
        return Objects.equals(textId, text1.textId) &&
                Objects.equals(contentType, text1.contentType) &&
                Objects.equals(text, text1.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(textId, contentType, text);
    }

    @OneToMany(mappedBy = "textByTextId")
    public List<HasSymptom> getHasSymptomsByTextId() {
        return hasSymptomsByTextId;
    }

    public void setHasSymptomsByTextId(List<HasSymptom> hasSymptomsByTextId) {
        this.hasSymptomsByTextId = hasSymptomsByTextId;
    }

    @OneToMany(mappedBy = "textByTextId")
    public List<HasText> getHasTextsByTextId() {
        return hasTextsByTextId;
    }

    public void setHasTextsByTextId(List<HasText> hasTextsByTextId) {
        this.hasTextsByTextId = hasTextsByTextId;
    }

    @OneToMany(mappedBy = "textByTextId")
    public List<TextUrl> getTextUrlsByTextId() {
        return textUrlsByTextId;
    }

    public void setTextUrlsByTextId(List<TextUrl> textUrlsByTextId) {
        this.textUrlsByTextId = textUrlsByTextId;
    }
}
