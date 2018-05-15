package edu.upm.midas.data.validation.metamap.model.special;
import java.util.Objects;

/**
 * Created by gerardo on 09/05/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className HasSymptom
 * @see
 */
public class HasSymptom {

    private String textId;
    private String cui;
    private Byte validated;
    private String matchedWords;
    private String positionalInfo;

    public HasSymptom() {
    }

    public HasSymptom(String textId, String cui, Byte validated, String matchedWords, String positionalInfo) {
        this.textId = textId;
        this.cui = cui;
        this.validated = validated;
        this.matchedWords = matchedWords;
        this.positionalInfo = positionalInfo;
    }

    public HasSymptom(String textId, String cui, Byte validated) {
        this.textId = textId;
        this.cui = cui;
        this.validated = validated;
    }

    public String getTextId() {
        return textId;
    }

    public void setTextId(String textId) {
        this.textId = textId;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public Byte getValidated() {
        return validated;
    }

    public void setValidated(Byte validated) {
        this.validated = validated;
    }

    public String getMatchedWords() {
        return matchedWords;
    }

    public void setMatchedWords(String matchedWords) {
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
        if (!(o instanceof HasSymptom)) return false;
        HasSymptom that = (HasSymptom) o;
        return Objects.equals(getTextId(), that.getTextId()) &&
                Objects.equals(getCui(), that.getCui());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTextId(), getCui());
    }

    @Override
    public String toString() {
        return "HasSymptom{" +
                "textId='" + textId + '\'' +
                ", cui='" + cui + '\'' +
                ", validated=" + validated +
                ", matchedWords='" + matchedWords + '\'' +
                ", positionalInfo='" + positionalInfo + '\'' +
                '}';
    }
}
