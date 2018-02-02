package edu.upm.midas.data.validation.metamap.model.response;

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

    private String cui;/*Código cui*/
    private String name;
    private List<String> semanticTypes;
    private List<String> matchedWords;
    private String positionalInfo;


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

    public List<String> getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(List<String> matchedWords) {
        this.matchedWords = matchedWords;
    }

    public String getPositionalInfo() {
        return positionalInfo;
    }

    public void setPositionalInfo(String positionalInfo) {
        this.positionalInfo = positionalInfo;
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

    @Override
    public String toString() {
        return "Concept{" +
                "cui='" + cui + '\'' +
                ", name='" + name + '\'' +
                ", semanticTypes=" + semanticTypes +
                ", matchedWords=" + matchedWords +
                ", positionalInfo='" + positionalInfo + '\'' +
                '}';
    }
}
