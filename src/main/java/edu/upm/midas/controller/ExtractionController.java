package edu.upm.midas.controller;

import edu.upm.midas.data.extraction.sources.wikipedia.service.ExtractionWikipedia;
import edu.upm.midas.data.extraction.xml.model.XmlLink;
import edu.upm.midas.data.relational.service.DiseaseService;
import edu.upm.midas.data.relational.service.impl.PopulateDbNative;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

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
    @Autowired
    private ExtractionWikipedia extractionWikipedia;
    @Autowired
    private UtilDate utilDate;
    @Autowired
    private Common common;
    @Autowired
    private DiseaseService diseaseService;


    @RequestMapping(path = { "/extract/wikipedia" }, //wikipedia extraction
            method = RequestMethod.GET)
    public String extract() throws Exception {
        String inicio = utilDate.getTime();
        Date version = utilDate.getSqlDate();
        List<XmlLink> externalDiseaseLinkList = populateDbNative.getDiseaseLinkListFromDBPedia(version);

        if (externalDiseaseLinkList!=null) {
            populateDbNative.populateResource(externalDiseaseLinkList);
            populateDbNative.populateSemanticTypes();
            populateDbNative.populate(externalDiseaseLinkList, version);
        }else{
            System.out.println("ERROR disease album");
        }
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

/*
        String g ="http://en.wikipedia.org/wiki/Odonto–tricho–ungual–digital–palmar_syndrome";
        String m = "http://en.wikipedia.org/wiki/Bannayan–Riley–Ruvalcaba_syndrome";
        System.out.println(g + " | " + m);
        g = StringEscapeUtils.escapeJava(g);
        m = common.replaceSpecialCharactersToUnicode(m);
        System.out.println(g + " | " + m);
        System.out.println(common.replaceUnicodeToSpecialCharacters(g) + " | " + common.replaceUnicodeToSpecialCharacters(m));
        String t = "http://en.wikipedia.org/wiki/Bannayan\\u00E2\\u20AC\\u201CRiley\\u00E2\\u20AC\\u201CRuvalcaba_syndrome";
        System.out.println(t.replace("\\", "\\\\"));
        System.out.println(common.replaceUnicodeToSpecialCharacters(t));
*/

/*
        String s = "en.wikipedia.org/wiki/Yush\u014D_disease-Δ";
        System.out.println(s);
        s = common.replaceUnicodeToSpecialCharacters(s);
        System.out.println(s);
        System.out.println(common.replaceSpecialCharactersToUnicode(s));
        diseaseService.insertNative("glg3", s, "");

*/
/*
        for (String s:
        Constants.URLS) {
            System.out.println(common.replaceUnicodeToSpecialCharacters(s));
        }
*/

        return "Successful extraction and insertion in a DB!";
    }

    @RequestMapping(path = { "/wikipedia/check" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void checkLinks() throws Exception {
        populateDbNative.checkWikiPages();
    }


    @RequestMapping(path = { "/wikipedia/codes" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void checkCodes() throws Exception {
        extractionWikipedia.extractResource(null);
    }


}
