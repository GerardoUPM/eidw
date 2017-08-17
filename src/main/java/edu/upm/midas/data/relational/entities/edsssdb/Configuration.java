package edu.upm.midas.data.relational.entities.edsssdb;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
public class Configuration {
    private String confId;
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
    @Column(name = "tool", nullable = false, length = 50)
    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    @Basic
    @Column(name = "configuration", nullable = false, length = -1)
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
