package edu.upm.midas.data.extraction.model.code;

import edu.upm.midas.data.extraction.model.Link;

import java.util.Objects;

/**
 * Created by gerardo on 01/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className Code
 * @see
 */
public class Code {

    private int id;
    private int uniqueId;
    private String code;
    private Link link;
    private Resource resource;

    public Code() {
    }

    public Code(String code, Resource resource) {
        this.code = code;
        this.resource = resource;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Link getLink() {
        return link;
    }

    public void setLink(Link link) {
        this.link = link;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Code)) return false;
        Code code1 = (Code) o;
        return Objects.equals(getCode(), code1.getCode()) &&
                Objects.equals(getResource(), code1.getResource());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getResource());
    }

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", resource=" + resource +
                '}';
    }
}
