package edu.upm.midas.data.extraction.model;


import edu.upm.midas.data.extraction.model.code.Resource;

/**
 * Created by gerardo on 09/03/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project pubmed_text_extraction_rest
 * @className Term
 * @see
 */
public class Term {

    private Integer id;
    private String name;
    private Resource resource;


    public Term() {
    }

    public Term(String name, Resource resource) {
        this.name = name;
        this.resource = resource;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }


    @Override
    public String toString() {
        return "Term{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", resource=" + resource +
                '}';
    }
}
