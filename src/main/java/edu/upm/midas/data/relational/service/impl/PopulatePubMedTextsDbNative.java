package edu.upm.midas.data.relational.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

import java.io.BufferedReader;
import java.io.FileReader;
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
    //@Transactional
    public void populate(String snapshot) throws Exception {

        //Source source = getPubMedSource(snapshot);
        Source source = readPubMedRetrievalJSON(snapshot);

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
            int docsCount = 1;
            for (Doc document: source.getDocuments()) {//Poner todo esto en un metodo transaccional
                //Solo inserta aquellos documentos que al menos tengan códigos o secciones
                String documentId = documentHelperNative.insertPubMedArticles(sourceId, document, version);
                System.out.println(docsCount + " Insert document: " + document.getDisease().getName() + "_" + documentId);

                //<editor-fold desc="PERSISTIR ENFERMEDAD DEL DOCUMENTO">
                String diseaseId = diseaseHelperNative.insertIfExistPubMedArticles(document, documentId, version, source.getName());
                //</editor-fold>

                //<editor-fold desc="PERSISTIR CÓDIGOS DEL DOCUMENTO">
                if (document.getCodeList()!=null)
                    codeHelperNative.insertIfExist(document.getCodeList(), documentId, version);
                //</editor-fold>

                //<editor-fold desc="RECORRIDO DE SECCIONES PARA ACCEDER A LOS TEXTOS">
                if (document.getSectionList()!=null) {
                    for (edu.upm.midas.data.extraction.model.Section section : document.getSectionList()) {
                        //Si la sección no tiene textos no se inserta en la relación has_section
                        if (section.getTextList()!=null) {
                        //<editor-fold desc="PERSISTIR has_section">
                        //inserta la sección para ese documento
                        String sectionId = hasSectionHelperNative.insert(documentId, version, section);
                        //</editor-fold>

                        //Validar si hay textos
                        int textCount = 0;
                            for (edu.upm.midas.data.extraction.model.text.Text text : section.getTextList()) {
                                //<editor-fold desc="INSERTAR TEXTO">
                                textHelperNative.insertPubMedTextArticles(text, sectionId, documentId, version, text.getPaperId());
                                //</editor-fold>

                                textCount++;
                            }// Textos
                        }

                    }// Secciones
                }
                //</editor-fold>

                //insertAllDataOfDocument(document, sourceId, version, source, docsCount);
                docsCount++;
            }// Documentos
            System.out.println("Inserted Documents: " + docsCount);
        }// Fuente "Source"
        System.out.println("Populate end...");
        //extractionWikipedia.extractionReport();

    }


    @Transactional
    public void insertAllDataOfDocument(Doc document, String sourceId, Date version, Source source, int docsCount) throws JsonProcessingException {
        //Solo inserta aquellos documentos que al menos tengan códigos o secciones
        String documentId = documentHelperNative.insertPubMedArticles(sourceId, document, version);
        System.out.println(docsCount + " Insert document: " + document.getDisease().getName() + "_" + documentId);

        //<editor-fold desc="PERSISTIR ENFERMEDAD DEL DOCUMENTO">
        String diseaseId = diseaseHelperNative.insertIfExistPubMedArticles(document, documentId, version, source.getName());
        //</editor-fold>

        //<editor-fold desc="PERSISTIR CÓDIGOS DEL DOCUMENTO">
        if (document.getCodeList()!=null)
            codeHelperNative.insertIfExist(document.getCodeList(), documentId, version);
        //</editor-fold>

        //<editor-fold desc="RECORRIDO DE SECCIONES PARA ACCEDER A LOS TEXTOS">
        if (document.getSectionList()!=null) {
            for (edu.upm.midas.data.extraction.model.Section section : document.getSectionList()) {
                //Si la sección no tiene textos no se inserta en la relación has_section
                if (section.getTextList()!=null) {
                    //<editor-fold desc="PERSISTIR has_section">
                    //inserta la sección para ese documento
                    String sectionId = hasSectionHelperNative.insert(documentId, version, section);
                    //</editor-fold>

                    //Validar si hay textos
                    int textCount = 0;
                    for (edu.upm.midas.data.extraction.model.text.Text text : section.getTextList()) {
                        //<editor-fold desc="INSERTAR TEXTO">
                        textHelperNative.insertPubMedTextArticles(text, sectionId, documentId, version, text.getPaperId());
                        //</editor-fold>

                        textCount++;
                    }// Textos
                }

            }// Secciones
        }
        //</editor-fold>
    }


    public Source getPubMedSource(String snapshot) throws Exception {
        Request request = new Request();
        Source source = null;
        //request.setSnapshot("2018-04-03");
        request.setSnapshot(snapshot);
        request.setJson(true);
        request.setNumOfArticles(100);
        System.out.println("Start Connection_ with PUBMED TEXT EXTRACTION REST API...");
        System.out.println("Get PubMed Information by JSON... please wait, this process can take from minutes... ");
        System.out.println(request);
        Response response = pubMedTextExtractionResource.getPubMedTextsByJSON(request);
        //Response response = readPubMedRetrievalJSON(snapshot);
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


    /**
     * @param snapshot
     * @return
     * @throws Exception
     */
    public Source readPubMedRetrievalJSON(String snapshot) throws Exception {
        Source source = null;
        Gson gson = new Gson();
        String fileName = snapshot + Constants.PM_RETRIEVAL_FILE_NAME + Constants.DOT_JSON;
        String path = Constants.PM_RETRIEVAL_HISTORY_FOLDER + fileName;
        System.out.println("Read JSON!..." + path);

        try {
            /*File file = new File(path);
            if (file.exists()){
                System.out.println("length: "+*//*(*//*file.length()*//*//*(1024*1024))*//*+" mb");
                System.out.println("getTotalSpace: "+file.getTotalSpace());
            }*/
            BufferedReader br = new BufferedReader(new FileReader(path));
            source = gson.fromJson(br, Source.class);
        }catch (Exception e){
            System.out.println("Error to read or convert JSON!...");
        }

        /*for (edu.upm.midas.data.validation.metamap.model.response.Text text: resp.getTexts()) {
            System.out.println("TextId: " + text.getId() + " | Concepts: " + text.getConcepts().toString());
        }*/
        //System.out.println("source: "+source);

        return source;
    }






}
