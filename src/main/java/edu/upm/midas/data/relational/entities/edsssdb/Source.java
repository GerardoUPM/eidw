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
 * @className Source
 * @see
 */
@Entity
@Table(name = "source", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Source.findAll", query = "SELECT s FROM Source s")
        , @NamedQuery(name = "Source.findById", query = "SELECT s FROM Source s WHERE s.sourceId = :sourceId")
        , @NamedQuery(name = "Source.findBySourceId", query = "SELECT s FROM Source s WHERE s.sourceId = :sourceId")
        , @NamedQuery(name = "Source.findByName", query = "SELECT s FROM Source s WHERE s.name = :name")
        , @NamedQuery(name = "Source.updateById", query = "UPDATE Source s SET s.name = :name WHERE s.sourceId = :sourceId")
        , @NamedQuery(name = "Source.findLastSource", query = "SELECT s FROM Source s ORDER BY s.sourceId DESC")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Source.findByIdNativeMapping",
                query = "SELECT s.source_id, s.name "
                        + "FROM source s WHERE s.source_id = :sourceId",
                resultSetMapping="SourceMapping"

        ),
        @NamedNativeQuery(
                name = "Source.findByIdNativeResultClass",
                query = "SELECT s.source_id, s.name "
                        + "FROM source s WHERE s.source_id = :sourceId",
                resultClass = Source.class
        ),
        @NamedNativeQuery(
                name = "Source.findLastSourceNativeResultClass",
                query = "SELECT s.source_id, s.name "
                        + "FROM source s ORDER BY CAST( SUBSTRING( s.source_id , 3) AS UNSIGNED) DESC",
                resultClass = Source.class
        ),
        @NamedNativeQuery(
                name = "Source.findLastIdNative",
                query = "SELECT s.source_id "
                        + "FROM source s ORDER BY CAST( SUBSTRING( s.source_id , 3) AS UNSIGNED) DESC"
        ),


        @NamedNativeQuery(
                name = "Source.insertNative",
                query = "INSERT INTO source (source_id, name) "
                        + "VALUES (:sourceId, :name) "
        ),
        @NamedNativeQuery(
                name = "HasSource.insertNative",
                query = "INSERT INTO has_source (document_id, date, source_id) "
                        + "VALUES (:documentId, :date, :sourceId)"
        ),

        @NamedNativeQuery(
                name = "SourceUrl.insertNative",
                query = "INSERT INTO source_url (source_id, url_id) "
                        + "VALUES (:sourceId, :urlId)"
        ),
        @NamedNativeQuery(
                name = "Source.findByNameNative",
                query = "SELECT s.source_id "
                        + "FROM source s WHERE s.name = :name"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "SourceMapping",
                entities = @EntityResult(
                        entityClass = Source.class,
                        fields = {
                                @FieldResult(name = "sourceId", column = "source_id"),
                                @FieldResult(name = "name", column = "name")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="sourceId")
public class Source {
    private String sourceId;
    private String name;
    private List<HasSource> hasSourcesBySourceId;
    private List<SourceUrl> sourceUrlsBySourceId;

    @Id
    @Column(name = "source_id", nullable = false, length = 10)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 150)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return Objects.equals(sourceId, source.sourceId) &&
                Objects.equals(name, source.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceId, name);
    }

    @OneToMany(mappedBy = "sourceBySourceId")
    public List<HasSource> getHasSourcesBySourceId() {
        return hasSourcesBySourceId;
    }

    public void setHasSourcesBySourceId(List<HasSource> hasSourcesBySourceId) {
        this.hasSourcesBySourceId = hasSourcesBySourceId;
    }

    @OneToMany(mappedBy = "sourceBySourceId")
    public List<SourceUrl> getSourceUrlsBySourceId() {
        return sourceUrlsBySourceId;
    }

    public void setSourceUrlsBySourceId(List<SourceUrl> sourceUrlsBySourceId) {
        this.sourceUrlsBySourceId = sourceUrlsBySourceId;
    }
}
