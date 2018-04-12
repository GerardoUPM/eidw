package edu.upm.midas.controller;

import edu.upm.midas.data.extraction.xml.model.XmlLink;
import edu.upm.midas.data.relational.entities.edsssdb.*;
import edu.upm.midas.data.relational.service.DocumentService;
import edu.upm.midas.data.relational.service.impl.PopulateDbNative;
import edu.upm.midas.service.ExtractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    private ExtractService extractService;
    @Autowired
    private DocumentService documentService;

    @RequestMapping(path = { "/extract/wikipedia" }, //wikipedia extraction
            method = RequestMethod.GET)
    public String extract() throws Exception {
        extractService.wikipediaExtract();
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


    @RequestMapping(path = { "/extract/pubmed" }, //pubmed extraction
            method = RequestMethod.GET)
    public String pubMedExtract() throws Exception {
        String version = "2018-04-03";//"2018-03-23";
        extractService.pubMedExtract(version);
        return "PubMed Text Extraction successfully";
    }


    @RequestMapping(path = { "/wikipedia/check" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void checkLinks() throws Exception {
        extractService.checkLinks();
    }


    @RequestMapping(path = { "/wikipedia/codes" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void checkCodes() throws Exception {
        extractService.checkCodes();
    }

    @RequestMapping(path = { "/wikipedia/report" }, //wikipedia extraction
            method = RequestMethod.GET)
    public void extractOnly() throws Exception {
        List<XmlLink> xmlLinks = new ArrayList<>();
        List<Document> documents = documentService.findAll();
        System.out.println("size: "+documents.size());
        int count = 1;
        for (Document document: documents) {
            if (document.getDate().toString().equals("2018-02-15")){
                List<HasDisease> hasDiseases = document.getHasDiseases();
                String diseaseName = "";
                for (HasDisease hasDisease:hasDiseases){
                    Disease disease = hasDisease.getDiseaseByDiseaseId();
                    diseaseName = diseaseName + disease.getName() + "; ";
                }
                List<DocumentUrl> documentUrls = document.getDocumentUrls();
                String urls = "";
                XmlLink xmlLink = new XmlLink();
                for (DocumentUrl documentUrl: documentUrls) {
                    String urlId = documentUrl.getUrlId();
                    Url url = documentUrl.getUrlByUrlId();
                    urls = urls + url.getUrl() + "; ";

                    xmlLink.setUrl(url.getUrl());
                    xmlLink.setConsult(diseaseName);
                    break;
                }
                System.out.println("Disease: (" + count +")" + xmlLink.getConsult() + " | URL: " + xmlLink.getUrl());
                xmlLinks.add(xmlLink);
                count++;
            }
        }
        extractService.onlyExtract(xmlLinks);

        //extractService.onlyExtract();
//        Gson gson = new Gson();
//        String fileName = "2018-02-01_metamap_filter.json";//adis = disease album
//        String path = Constants.EXTRACTION_HISTORY_FOLDER + fileName;
//
//        BufferedReader br = new BufferedReader( new FileReader(path));
//
//        ProcessedText resp = gson.fromJson(br, ProcessedText.class );
//
//        for (Text text: resp.getTexts()) {
//            System.out.println("TextId: " + text.getId() + " | Concepts: " + text.getConcepts().toString());
//        }
    }


}
