package edu.upm.midas.data.validation.metamap.model.special;
import java.util.List;
import java.util.Objects;

/**
 * Created by gerardo on 09/05/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className Symptom
 * @see
 */
public class Symptom {

    String cui;
    String name;
    List<String> semanticTypes;

    public Symptom() {
    }

    public Symptom(String cui, String name, List<String> semanticTypes) {
        this.cui = cui;
        this.name = name;
        this.semanticTypes = semanticTypes;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        if (!(o instanceof Symptom)) return false;
        Symptom symptom = (Symptom) o;
        return Objects.equals(getCui(), symptom.getCui());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCui());
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "cui='" + cui + '\'' +
                ", name='" + name + '\'' +
                ", semanticTypes=" + semanticTypes +
                '}';
    }
}
