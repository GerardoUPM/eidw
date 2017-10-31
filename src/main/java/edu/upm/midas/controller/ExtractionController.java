package edu.upm.midas.controller;

import edu.upm.midas.data.relational.service.impl.PopulateDbNative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by gerardo on 05/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project validation_medical_term
 * @className ValidationController
 * @see
 */
@RestController
@RequestMapping("/api")
public class ExtractionController {

    @Autowired
    private PopulateDbNative populateDbNative;


    @RequestMapping(path = { "/wikipedia/extract" }, //wikipedia extraction
            method = RequestMethod.GET)
    public String extract() throws Exception {
        populateDbNative.populateResource();
        populateDbNative.populateSemanticTypes();
        populateDbNative.populate();
        return "Successful extraction and insertion in a DB!";
    }

    @RequestMapping(path = { "/wikipedia/check" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void checkLinks() throws Exception {
        populateDbNative.checkWikiPages();
    }

}
