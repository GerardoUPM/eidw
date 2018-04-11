package edu.upm.midas.data.relational.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.extraction.model.Source;
import edu.upm.midas.data.extraction.sources.pubmed.model.Request;
import edu.upm.midas.data.extraction.sources.pubmed.model.Response;
import edu.upm.midas.data.extraction.sources.pubmed.pubMedTextExtractionApiResponse.PubMedTextExtractionResourceService;
import edu.upm.midas.data.extraction.sources.wikipedia.service.ExtractionWikipedia;
import edu.upm.midas.data.relational.service.helperNative.*;
import edu.upm.midas.enums.StatusHttpEnum;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import edu.upm.midas.utilsservice.UtilDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by gerardo on 29/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className PopulateDbNative
 * @see
 */
@Service
public class PopulatePubMedTextsDbNative {



    private static final Logger logger = LoggerFactory.getLogger(PopulatePubMedTextsDbNative.class);

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private ExtractionWikipedia extractionWikipedia;
    @Autowired
    private UtilDate date;



    @Autowired
    private SemanticTypeHelperNative semanticTypeHelperNative;
    @Autowired
    private ResourceHelperNative resourceHelperNative;
    @Autowired
    private DiseaseHelperNative diseaseHelperNative;
    @Autowired
    private SourceHelperNative sourceHelperNative;
    @Autowired
    private SectionHelperNative sectionHelperNative;
    @Autowired
    private HasSectionHelperNative hasSectionHelperNative;
    @Autowired
    private UrlHelperNative urlHelperNative;
    @Autowired
    private CodeHelperNative codeHelperNative;
    @Autowired
    private TextHelperNative textHelperNative;
    @Autowired
    private DocumentHelperNative documentHelperNative;

    @Autowired
    private ConfigurationHelper confHelper;

    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Constants constants;
    @Autowired
    private Common common;

    @Autowired
    private PubMedTextExtractionResourceService pubMedTextExtractionResource;


    
    /**
     * @throws Exception
     */
    @Transactional
    public void populate(String snapshot) throws Exception {

        Source source = getPubMedSource(snapshot);

        Date version = date.stringToDate(snapshot);

        System.out.println("-------------------- POPULATE DATABASE --------------------");
        System.out.println("Populate start...");
        if (source!=null) {
            String sourceId = sourceHelperNative.insertIfExist( source );
            System.out.println("Source: " + sourceId + " - " + source.getName());

            //<editor-fold desc="PERSISTIR TODAS LAS SECCIONES">
            System.out.println("Insert all sections, if exists...");
            sectionHelperNative.insertIfExist( source.getSectionMap() );
            System.out.println("Insert all sections ready!");
            System.out.println("Insert documents start!");
            //</editor-fold>
            int docsCount = 1, invalidCount = 1;
            for (Doc document: source.getDocuments()) {
                //Solo inserta aquellos documentos que al menos tengan códigos o secciones
                if (document.isDiseaseArticle()) {
                    String documentId = documentHelperNative.insert(sourceId, document, version);

                    System.out.println(docsCount + " Insert document: " + document.getDisease().getName() + "_" + documentId);

                    //<editor-fold desc="PERSISTIR ENFERMEDAD DEL DOCUMENTO">
                    String diseaseId = diseaseHelperNative.insertIfExist(document, documentId, version);
                    //</editor-fold>

                    //<editor-fold desc="PERSISTIR CÓDIGOS DEL DOCUMENTO">
                    codeHelperNative.insertIfExist(document.getCodeList(), documentId, version);
                    //</editor-fold>

                    //<editor-fold desc="RECORRIDO DE SECCIONES PARA ACCEDER A LOS TEXTOS">
                    for (edu.upm.midas.data.extraction.model.Section section : document.getSectionList()) {
                        //<editor-fold desc="PERSISTIR has_section">
                        String sectionId = hasSectionHelperNative.insert(documentId, version, section);
                        //</editor-fold>

                        int textCount = 0;
                        for (edu.upm.midas.data.extraction.model.text.Text text : section.getTextList()) {
                            //<editor-fold desc="INSERTAR TEXTO">
                            textHelperNative.insert(text, sectionId, documentId, version);
                            //</editor-fold>

                            textCount++;
                        }// Textos

                    }// Secciones
                    //</editor-fold>
                    docsCount++;
                }else{
                    invalidCount++;
                }
            }// Documentos
            System.out.println("Inserted Documents: " + docsCount);
            System.out.println("No inserted Documents(invalid): " + invalidCount);
        }// Fuente "Source"
        System.out.println("Populate end...");
        //extractionWikipedia.extractionReport();

    }


    public Source getPubMedSource(String snapshot){
        Request request = new Request();
        Source source = null;
        //request.setSnapshot("2018-04-03");
        request.setSnapshot(snapshot);
        request.setJson(true);
        System.out.println("Start Connection_ with PUBMED TEXT EXTRACTION REST API...");
        System.out.println("Get PubMed Information by JSON... please wait, this process can take from minutes... ");
        System.out.println(request);
        Response response = pubMedTextExtractionResource.getPubMedTextsByJSON(request);
        System.out.println("End Connection_ with PUBMED TEXT EXTRACTION REST API...");
        System.out.println("Response...");
        System.out.println("Response code: " + response.getResponseCode());
        System.out.println("Response message: " + response.getResponseMessage());
        //System.out.println("Response resources: " + response.getResourceHashMap());
        System.out.println("Response message: " + response.getSource().getDocumentCount());
        if (response.getResponseCode().equals(StatusHttpEnum.OK.getClave()))
            source = response.getSource();
        return source;
    }



}
