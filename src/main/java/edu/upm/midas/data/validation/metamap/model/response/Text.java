package edu.upm.midas.data.validation.metamap.model.response;

import java.util.List;

/**
 * Created by gerardo on 25/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project metamap_rest
 * @className Text
 * @see
 */
public class Text {

    private String id;
    private List<Concept> concepts;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }




}
