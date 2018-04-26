package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className HasSection
 * @see
 */
@Entity
@Table(name = "has_section", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "HasSection.findAll", query = "SELECT h FROM HasSection h")
        , @NamedQuery(name = "HasSection.findById", query = "SELECT h FROM HasSection h WHERE h.documentId = :documentId AND h.date = :date AND h.sectionId = :sectionId")
        , @NamedQuery(name = "HasSection.findByDocumentId", query = "SELECT h FROM HasSection h WHERE h.documentId = :documentId")
        , @NamedQuery(name = "HasSection.findByDate", query = "SELECT h FROM HasSection h WHERE h.date = :date")
        , @NamedQuery(name = "HasSection.findBySectionId", query = "SELECT h FROM HasSection h WHERE h.sectionId = :sectionId")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "HasSection.findByIdNative",
                query = "SELECT h.document_id, h.date, h.section_id "
                        + "FROM has_section h WHERE h.document_id = :documentId AND h.date = :date AND h.section_id = :sectionId",
                resultSetMapping="HasSectionMapping"

        ),
        @NamedNativeQuery(
                name = "HasSection.findByIdNativeResultClass",
                query = "SELECT h.document_id, h.date, h.section_id "
                        + "FROM has_section h WHERE h.document_id = :documentId AND h.date = :date AND h.section_id = :sectionId",
                resultClass = HasSection.class
        )


        ,
        @NamedNativeQuery(
                name = "HasSection.insertNative",
                query = "INSERT IGNORE INTO has_section (document_id, date, section_id) "
                        + "VALUES (:documentId, :date, :sectionId)"
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "HasSectionMapping",
                entities = @EntityResult(
                        entityClass = HasSection.class,
                        fields = {
                                @FieldResult(name = "documentId", column = "document_id"),
                                @FieldResult(name = "date", column = "date"),
                                @FieldResult(name = "sectionId", column = "section_id")
                        }
                )
        )
})

@IdClass(HasSectionPK.class)
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="hasSectionPK")
public class HasSection {
    private String documentId;
    private Date date;
    private String sectionId;
    private Document document;
    private Section sectionBySectionId;
    private List<HasText> hasTexts;

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
    @Column(name = "section_id", nullable = false, length = 10)
    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HasSection that = (HasSection) o;
        return Objects.equals(documentId, that.documentId) &&
                Objects.equals(date, that.date) &&
                Objects.equals(sectionId, that.sectionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(documentId, date, sectionId);
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
    @JoinColumn(name = "section_id", referencedColumnName = "section_id", nullable = false, insertable = false, updatable = false)
    public Section getSectionBySectionId() {
        return sectionBySectionId;
    }

    public void setSectionBySectionId(Section sectionBySectionId) {
        this.sectionBySectionId = sectionBySectionId;
    }

    @OneToMany(mappedBy = "hasSection")
    public List<HasText> getHasTexts() {
        return hasTexts;
    }

    public void setHasTexts(List<HasText> hasTexts) {
        this.hasTexts = hasTexts;
    }

}
