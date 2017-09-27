package edu.upm.midas.data.relational.entities.edsssdb;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Date;
import java.util.Objects;

/**
 * Created by gerardo on 20/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project demo
 * @className Configuration
 * @see
 */
@Entity
@Table(name = "configuration", catalog = "edsssdb", schema = "")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "Configuration.findAll", query = "SELECT c FROM Configuration c")
        , @NamedQuery(name = "Configuration.findByConfId", query = "SELECT c FROM Configuration c WHERE c.confId = :confId")
        , @NamedQuery(name = "Configuration.findByTool", query = "SELECT c FROM Configuration c WHERE c.tool = :tool")
})

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Configuration.insertNative",
                query = "INSERT INTO configuration (conf_id, source_id, version, tool, configuration) " +
                        "VALUES (:conf_id, :source_id, :version, :tool, :configuration) "
        )
})

@SqlResultSetMappings({
        @SqlResultSetMapping(
                name = "ConfigurationMapping",
                entities = @EntityResult(
                        entityClass = Configuration.class,
                        fields = {
                                @FieldResult(name = "confId", column = "conf_id"),
                                @FieldResult(name = "sourceId", column = "source_id"),
                                @FieldResult(name = "version", column = "version"),
                                @FieldResult(name = "tool", column = "tool"),
                                @FieldResult(name = "configuration", column = "configuration")
                        }
                )
        )
})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="confId")
public class Configuration {
    private String confId;
    private String sourceId;
    private Date version;
    private String tool;
    private String configuration;

    @Id
    @Column(name = "conf_id", nullable = false, length = 50)
    public String getConfId() {
        return confId;
    }

    public void setConfId(String confId) {
        this.confId = confId;
    }

    @Basic
    @Column(name = "source_id", nullable = false, length = 10)
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "version", nullable = false)
    public Date getVersion() {
        return version;
    }

    public void setVersion(Date version) {
        this.version = version;
    }

    @Basic
    @Column(name = "tool", nullable = false, length = 50)
    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    @Basic
    @Column(name = "configuration", nullable = true, length = -1)
    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(confId, that.confId) &&
                Objects.equals(tool, that.tool) &&
                Objects.equals(configuration, that.configuration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(confId, tool, configuration);
    }
}
