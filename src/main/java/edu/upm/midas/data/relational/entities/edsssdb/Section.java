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
 * @className Section
 * @see
 */
@Entity
@Table(name = "section", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Section.findAll", query = "SELECT s FROM Section s")
        , @NamedQuery(name = "Section.findById", query = "SELECT s FROM Section s WHERE s.sectionId = :sectionId")
        , @NamedQuery(name = "Section.findBySectionId", query = "SELECT s FROM Section s WHERE s.sectionId = :sectionId")
        , @NamedQuery(name = "Section.findByName", query = "SELECT s FROM Section s WHERE s.name = :name")
        , @NamedQuery(name = "Section.findByDescription", query = "SELECT s FROM Section s WHERE s.description = :description")
        , @NamedQuery(name = "Section.findLastSection", query = "SELECT s FROM Section s ORDER BY s.sectionId DESC")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Section.findByIdNative",
                query = "SELECT s.section_id, s.name, s.description "
                        + "FROM section s WHERE s.section_id = :sectionId",
                resultSetMapping="SectionMapping"

        ),
        @NamedNativeQuery(
                name = "Section.findByIdNativeResultClass",
                query = "SELECT s.section_id, s.name, s.description "
                        + "FROM section s WHERE s.section_id = :sectionId",
                resultClass = Section.class

        ),
        @NamedNativeQuery(
                name = "Section.findByNameNativeResultClass",
                query = "SELECT s.section_id, s.name, s.description "
                        + "FROM section s WHERE s.name COLLATE utf8_bin = :name ",
                resultClass = Section.class
        ),
        @NamedNativeQuery(
                name = "Section.findLastSectionNativeResultClass",
                query = "SELECT s.section_id, s.name, s.description "
                        + "FROM section s ORDER BY CAST( SUBSTRING( s.section_id , 3) AS UNSIGNED) DESC ",
                resultClass = Section.class
        ),
        @NamedNativeQuery(
                name = "Section.findLastIdNative",
                query = "SELECT s.section_id "
                        + "FROM section s ORDER BY CAST( SUBSTRING( s.section_id , 3) AS UNSIGNED) DESC"
        ),


        @NamedNativeQuery(
                name = "Section.insertNative",
                query = "INSERT INTO section (section_id, name, description) "
                        + "VALUES (:sectionId, :name, :description)"
        ),
        @NamedNativeQuery(
                name = "HasSection.insertNative_",
                query = "INSERT INTO has_section (document_id, date, section_id) "
                        + "VALUES (:documentId, :date, :code, :sectionId)"
        )

})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "SectionMapping",
                entities = @EntityResult(
                        entityClass = Section.class,
                        fields = {
                                @FieldResult(name = "sectionId", column = "section_id"),
                                @FieldResult(name = "name", column = "name"),
                                @FieldResult(name = "description", column = "description")
                        }
                )
        )
})

@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="sectionId")
public class Section {
    private String sectionId;
    private String name;
    private String description;
    private List<HasSection> hasSectionsBySectionId;

    @Id
    @Column(name = "section_id", nullable = false, length = 10)
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 45)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "description", nullable = false, length = 45)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Section section = (Section) o;
        return Objects.equals(sectionId, section.sectionId) &&
                Objects.equals(name, section.name) &&
                Objects.equals(description, section.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sectionId, name, description);
    }

    @OneToMany(mappedBy = "sectionBySectionId")
    public List<HasSection> getHasSectionsBySectionId() {
        return hasSectionsBySectionId;
    }

    public void setHasSectionsBySectionId(List<HasSection> hasSectionsBySectionId) {
        this.hasSectionsBySectionId = hasSectionsBySectionId;
    }
}
