package edu.upm.midas.data.relational.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.extraction.model.Source;
import edu.upm.midas.data.extraction.model.code.Resource;
import edu.upm.midas.data.extraction.sources.wikipedia.service.ExtractionWikipedia;
import edu.upm.midas.data.relational.service.helperNative.*;
import edu.upm.midas.utilsservice.UtilDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
public class PopulateDbNative {



    private static final Logger logger = LoggerFactory.getLogger(PopulateDbNative.class);

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


    /**
     * @throws Exception
     */
    @Transactional
    public void populateResource() throws Exception {

        HashMap<String, Resource> resourceMap = extractionWikipedia.extractResource();

        System.out.println("INSERT RESOURCES...");
        List<edu.upm.midas.data.relational.entities.edsssdb.Resource> resourceList = resourceHelperNative.insertIfExist( resourceMap );

        if ( resourceList.size() > 0 ) System.out.println("INSERT RESOURCES READY!");

    }


    /**
     * @throws Exception
     */
    @Transactional //CAMBIAR A NATIVOS
    public void populateSemanticTypes() throws Exception {
        System.out.println("INSERT SEMANTIC TYPES...");
        for (String semanticType:
                Constants.SEMANTIC_TYPES) {
            semanticTypeHelperNative.insertIfExist( semanticType, "" );
        }
        System.out.println("INSERT SEMANTIC TYPES READY!");
    }


    @Transactional //CAMBIAR A NATIVOS
    public void populateSections() throws Exception {
        System.out.println("INSERT SECTIONS...");
//        List<edu.upm.Source> sourceList = extractionWikipedia.extract();
//        sectionHelperNative.insertIfExist( source.getSectionMap() );
        System.out.println("INSERT SECTIONS READY!");
    }



    /**
     * @throws Exception
     */
    @Transactional
    public void populate() throws Exception {

/*
        Source source = new Source();
        source.setId(1);
        source.setName("medline");
        Link link = new Link();
        link.setId(1);
        link.setUrl("www.test.com");
        source.setUrl(link);
        System.out.println("SourceId: " + sourceHelperNative.insertIfExist( source ) );
*/

        List<Source> sourceList = extractionWikipedia.extract();

        Date version = date.getSqlDate();

        System.out.println("-------------------- POPULATE DATABASE --------------------");
        System.out.println("Populate start...");
        for (Source source:
                sourceList) {

            String sourceId = sourceHelperNative.insertIfExist( source );
            System.out.println("Source: " + sourceId + " - " + source.getName());

            //<editor-fold desc="PERSISTIR TODAS LAS SECCIONES">
            System.out.println("Insert all sections, if exists...");
            sectionHelperNative.insertIfExist( source.getSectionMap() );
            System.out.println("Insert all sections ready!");
            System.out.println("Insert documents start!");
            //</editor-fold>
            int docsCount = 1;
            for (Doc document:
                    source.getDocList()) {

                String documentId = documentHelperNative.insert( sourceId, document, version );

                System.out.println(docsCount + " Insert document: " + document.getDisease().getName() + "_" + documentId);

                //<editor-fold desc="PERSISTIR ENFERMEDAD DEL DOCUMENTO">
                String diseaseId = diseaseHelperNative.insertIfExist( document, documentId, version );
                //</editor-fold>

                //<editor-fold desc="PERSISTIR CÓDIGOS DEL DOCUMENTO">
                codeHelperNative.insertIfExist( document.getCodeList(), documentId, version );
                //</editor-fold>

                //<editor-fold desc="RECORRIDO DE SECCIONES PARA ACCEDER A LOS TEXTOS">
                for (edu.upm.midas.data.extraction.model.Section section: document.getSectionList()) {
                    //<editor-fold desc="PERSISTIR has_section">
                    String sectionId = hasSectionHelperNative.insert( documentId, version, section );
                    //</editor-fold>

                    int textCount = 0;
                    for (edu.upm.midas.data.extraction.model.text.Text text:
                            section.getTextList()) {

                        //<editor-fold desc="INSERTAR TEXTO">
                        textHelperNative.insert( text, sectionId, documentId, version );
                        //</editor-fold>

                        textCount++;
                    }// Textos

                }// Secciones
                //</editor-fold>
                docsCount++;
            }// Documentos

        }// Fuentes "Sources"
        System.out.println("Populate end...");

    }

}
