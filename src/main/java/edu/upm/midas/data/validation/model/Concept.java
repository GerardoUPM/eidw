package edu.upm.midas.data.validation.model;

import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 18/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className Concept
 * @see
 */
public class Concept {

    private String id;
    private String name;
    private String cui;/*CUI CODE*/
    private List<String> semanticTypes;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public List<String> getSemanticTypes() {
        return semanticTypes;
    }

    public void setSemanticTypes(List<String> semanticTypes) {
        this.semanticTypes = semanticTypes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Concept)) return false;
        Concept concept = (Concept) o;
        return Objects.equals(getCui(), concept.getCui());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCui());
    }

}
