package edu.upm.midas.data.extraction.sources.wikipedia.service;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.*;
import edu.upm.midas.data.extraction.model.code.Code;
import edu.upm.midas.data.extraction.model.code.Resource;
import edu.upm.midas.data.extraction.model.text.*;
import edu.upm.midas.data.extraction.component.ConnectDocument;
import edu.upm.midas.data.extraction.component.LoadSource;
import edu.upm.midas.data.extraction.xml.model.XmlHighlight;
import edu.upm.midas.data.extraction.xml.model.XmlLink;
import edu.upm.midas.data.extraction.xml.model.XmlSection;
import edu.upm.midas.data.extraction.xml.model.XmlSource;
import edu.upm.midas.enums.StatusHttpEnum;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UtilDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gerardo on 31/3/17.
 *
 * Clase servicio que tiene la lógica para extraer información de documentos Wikipedia (páginas Web)
 * de secciones específicas .
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationWikipedia
 * @className ExtractionWikipedia
 * @see Source
 */
@Service
public class ExtractionWikipedia {
    
    @Autowired
    private LoadSource loadSource;
    @Autowired
    private ConnectDocument connectDocument;



    @Autowired
    private Common common;
    @Autowired
    private UtilDate date;



    /**
     * Método para extraer información de una página Web de Wikipedia (documento)
     * Datos a estraer: 1) códigos del infobox, 2) secciones establecidas que hablan
     * de los signos o síntomas de una enfermedad y 3) sus enlaces (url)
     *
     * Lee las clases que obtuvieron información del archivo de configuración XML.
     *
     * Almacenará toda la información extraida desde wikipedia en una estructura de clases
     * hecha a medida de las páginas Web de wikipedia.
     *
     * Dicha estructura posteriormente se leerá para dar paso a insertar la información almacenada
     * en la base de datos genérica para diferentes tipos de fuentes "Sources".
     *
     * @return lista de fuentes de información "Source". Para ser insertados en la BD.
     * @throws Exception
     */
    public List<Source> extract(List<XmlLink> externalDiseaseLinkList) throws Exception {

        //<editor-fold desc="VARIABLES DE INICO">
        Connection_ connection_;
        Document document;

        Source source;
        Doc doc;
        Disease disease;
        Section section;
        Paragraph paragraph;
        List_ list_ = null;
        Table table;
        Link url;

        List<Source> sourceList;
        List<Doc> docList;
        List<Section> sectionList;
        List<Text> textList;

        TreeMap<String, String> sectionMap;

        int countDoc;
        int countSections;
        int countText;

        Boolean isSection;
        Boolean isText;

        Date version = date.getSqlDate();
        //</editor-fold>

        System.out.println("Preparing model...");
        System.out.println("Reading data source...");

        // Se inicializa la lista de "Source"
        sourceList = new ArrayList<>();
        // Se leen y recorren los "Sources": wikipedia, medline, etc. NOTA. Aquí solo se leen páginas de wikipedia.
        for (XmlSource xmlSource : loadSource.loadSources()) {
            // VALIDAR QUE SOLO SE RECUPERE INFORMACIÓN DE WIKIPEDIA
            if (xmlSource.getName().equals(Constants.SOURCE_WIKIPEDIA)) {
                // Se crea una nueva fuente "Source" y un enlace "Link"
                //<editor-fold desc="FUENTE">
                source = new Source();
                url = new Link();
                // Se lee del XML y almacena la información básica de una fuente
                source.setId(xmlSource.getId());
                source.setName(xmlSource.getName());
                // Se lee del XML la información básica del enlace de la fuente
                url.setId(xmlSource.getId());
                url.setUrl(xmlSource.getLink());
                // Se liga el enlace a la fuente
                source.setUrl(url);
                //</editor-fold>
                System.out.println("-----------------------------------------");
                System.out.println("Connecting to... " + xmlSource.getName());
                System.out.println("-----------------------------------------");
                System.out.println("List of sections...");
                //<editor-fold desc="LISTA DE SECCIONES">
                // Se crea un Map donde para almacenar todas las secciones importantes a leer de wikipedia
                sectionMap = new TreeMap<>();
                // Se leen las secciones del XML
                for (XmlSection xmlSection : xmlSource.getSectionList()) {
                    // Se almacenan las secciones en el Map
                    sectionMap.put(xmlSection.getId().trim(), xmlSection.getName());
                    System.out.println(xmlSection.getId());
                }
                // Se enlaza la lista de secciones a la fuente "Source"
                source.setSectionMap(sectionMap);
                //</editor-fold>
                System.out.println("Procesing links and extracting...");
                //READ DISEASE ALBUM LINKS
                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
                // Se crea una lista de documentos "Doc". Doc: es una documento de wikipedia
                docList = new ArrayList<>();
                // Se inicializa un contador para todos los documentos
                countDoc = 1;
                // Se leen todos los enlaces a los documentos de wikipedia (https...)
                //<editor-fold desc="LISTA DE ENFERMEDADES LLAMANDO A LA API REST GET_DISEASELIST_FROM_DBPEDIA">
                List<XmlLink> linkListFromDBPedia = externalDiseaseLinkList;
                if (linkListFromDBPedia != null) {
                    if (!linkListFromDBPedia.isEmpty()) {
                        xmlSource.setLinkList(linkListFromDBPedia);
                        System.out.println("getDiseaseLinkListFromDBPedia.size(): " + xmlSource.getLinkList().size());
                    }
                }else{
                    System.out.println("Using the xml configuration url list...");
                }
                //</editor-fold>
                for (XmlLink xmlLink : xmlSource.getLinkList()) {
                    // Se conecta con el documento wikipedia por medio de su enlace
                    connection_ = connectDocument.connect(xmlLink.getUrl());
                    // Se crea un nuevo documento (Doc), url (Link) y enfermedad (Disease)
                    doc = new Doc();
                    url = new Link();
                    disease = new Disease();

                    // Se obtiene el elemento HTML que almacena el nombre de la enfermedad
                    String idElementName = getHighlightXmlByDescription(
                            Constants.XML_HL_DISEASENAME, xmlSource).getId();

                    // Se verifica si hubo conexión con el documento (enlace Web)
                    // Se pinta en pantalla el status OK (esta disponible el enlace)
                    System.out.println(countDoc + " wikipediaExtract " + xmlLink.getUrl() + " ==> " + connection_.getStatus() + "("+connection_.getStatusCode()+")");
                    if (connection_.getStatus().equals(StatusHttpEnum.OK.getDescripcion()) && connection_.getoDoc() != null && connection_.getoDoc().getElementById( idElementName ).text()!=null) {
                        // Se obtiene el documento HTML (página wikipedia)
                        //<editor-fold desc="DOCUMENTOS">
                        document = connection_.getoDoc();
                        // Se inicia a introducir información de un documento
                        doc.setId(countDoc);
                        doc.setDate(version);

                        // Enlace del documento
                        url.setId(countDoc);
                        url.setUrl(xmlLink.getUrl());

                        // Agrega el enlace al documento
                        doc.setUrl(url);

                        // Almacena información de la enfermdad
                        disease.setId(countDoc);
                        disease.setName(document.getElementById(idElementName).text());
                        //System.out.println("name; " + xmlLink.getConsult() + " ;url; " + xmlLink.getUrl() + " ;extractName; " + disease.getName());
                        // Agrega la enfermedad al documento. En un documento se habla de una enfermedad
                        doc.setDisease(disease);
                        //</editor-fold>

                        // Se llama al método que lee todos los códigos (de los infoboxs) de un documento, si los hay
                        //<editor-fold desc="EXTRAER CÓDIGOS DE LOS INFOBOX">
                        List<Code> codes = removeRepetedCodes( getCodes(document, xmlSource) );
                        doc.setCodeList( codes );
                        /*for (Code code: codes) {
                            System.out.println(code.getCode() +" "+ code.getResource().getName());
                        }*/
                        //</editor-fold>

                        // Crea lista de secciones
                        sectionList = new ArrayList<>();
                        countSections = 1;
                        // Lee todas las secciones del XML (No todos los documentos tienen
                        // información en las mismas secciones o incluso no las tienen)
                        //<editor-fold desc="RECORRIDO DE SECCIONES DEL XML">
                        for (XmlSection xmlSection : xmlSource.getSectionList()) {
                            // Crea una sección
                            section = new Section();
                            isSection = false;
                            //System.out.println("Analizando sección: " + section);

                            // Encuentra y almacena una sección <h2>
                            Element sectionElement = document.getElementById(xmlSection.getId()); //One section is returned!
                            //<editor-fold desc="PROCESO SOBRE LOS ELEMENTOS DE UNA SECCIÓN">
                            // Validar que la sección fue encontrada y tiene información
                            if (sectionElement != null) {
                                // Crea una lista de textos
                                textList = new ArrayList<>();
                                isSection = true;

                                //<editor-fold desc="SECCIONES">
                                // Almacena la información de una sección
                                section.setId(countSections);
                                section.setName(xmlSection.getId());
                                section.setDescription(sectionElement.text().trim());

                                //System.out.println(sectionElement);
                                //System.out.println(sectionElement.parent());

                                // Obtiene el padre de la sección
                                sectionElement = sectionElement.parent();
                                // Obtiene el siguiente hermano de la sección
                                Element nextElementBro = sectionElement.nextElementSibling();
                                //</editor-fold>

                                countText = 1;
                                // Recorrido de los elementos que contienen texto dentro de una sección (<p>, <ul><ol>)
                                // Mientras no sea nulo, y el tag sea diferente <h2> no saldrá del ciclo
                                //<editor-fold desc="OBTENER TEXTOS DE PARRAFOS O LISTAS">
                                while (nextElementBro != null && nextElementBro.tagName() != xmlSection.getTypeTitle().getName()) {
                                    //RECORRER LOS TAG QUE DEBEN TENER INFORMACIÓN <p>, <ul>, <ol>
                                    // Se crea: 1) un parrafo y una lista
                                    isText = false;

                                    //<editor-fold desc="TITULO DE PÁRRAFO O LISTA">
                                    //System.out.println(nextElementBro.tagName() + " == " + nextElementBro.text());
                                    //Obtener el hermano anterior para ver si tiene titulo el parrafo o la lista
                                    Element prevElementBro = nextElementBro.previousElementSibling();

                                    // Obtiene el titulo del párrafo o nombre del síntoma si existe
                                    String title = "";
                                    if (prevElementBro.tagName() == Constants.HTML_H3 || prevElementBro.tagName() == Constants.HTML_H4) {
                                        if (prevElementBro.text() != null) title = prevElementBro.text();
                                    }
                                    //</editor-fold>

                                    // Extrae el texto si es una etiqueta <p> (paragraph)
                                    if (nextElementBro.tagName() == Constants.HTML_P) {//&& !nextElementBro.text().isEmpty()
                                        //<editor-fold desc="EXTRAE TEXTO DE UN PARRAFO Y LO ALMACENA EN UN OBJETO PARAGRAPH">
                                        // Guarda la información extraida de un párrafo wikipedia en un objeto
                                        // Se crea un párrafo y se extrae su información
                                        paragraph = setParagraphData(nextElementBro, countText, title);
                                        if (paragraph!=null) {//System.out.println("ENTRA_paragraph");
                                            isText = true;
                                            // Agrega el párrafo a la lista de textos
                                            textList.add(paragraph);
                                        }
                                        //</editor-fold>
                                        //Extrae el texto si es una etiqueta <ul> o <ol>
                                        //Actualización para extraer <dl>
                                    } else if (isElementIsATypeOfList(nextElementBro.tagName())) {//&& !nextElementBro.text().isEmpty()
                                        //<editor-fold desc="EXTRAE TEXTO DE UN PARRAFO Y LO ALMACENA EN UN OBJETO LIST_">
                                        // Guarda la información extraida de una lista wikipedia en un objeto
                                        list_ = setList_Data(nextElementBro, countText, title);
                                        if (list_!=null) {//System.out.println("ENTRA_list_");
                                            isText = true;
                                            // Agrega la lista "List_" a la lista de textos
                                            textList.add(list_);
                                        }
                                        //</editor-fold>
                                        //Extrae el texto si es una etiqueta <table> y class="wikitable"
                                    } else if (nextElementBro.tagName() == Constants.HTML_TABLE){
                                        Elements trs = nextElementBro.select("table.wikitable tr");
                                        if (trs != null) {
                                            //System.out.println("Wikitable" + trs.toString());
                                            // Guarda la información extraida de una wikitable en un objeto
                                            table = extractWikitableTexts(trs, countText, title);
                                            // Agrega la lista "List_" a la lista de textos
                                            if (table!=null) {//System.out.println("ENTRA_table");
                                                isText = true;
                                                textList.add(table);
                                            }
                                        }
                                    } else if (nextElementBro.tagName() == Constants.HTML_DIV){
                                        //Verifica dentro del DIV si:
                                        boolean findList = verifyList(nextElementBro, isText, list_, countText, title, textList);
                                        if (findList){//System.out.println("ENTRA_insideDIVtoFindLists");
                                            countText = textList.size();
                                            isText = true;
                                        }
                                    }

                                    if (isText) {
                                        countText++;
                                    }

                                    // Obtiene el siguiente hermano del nodo para seguir con el ciclo while
                                    nextElementBro = nextElementBro.nextElementSibling();
                                    //if (i == 100) break;  i++;
                                }//end while (nextElementBro != null && nextElementBro.tagName() != "h2")
                                //</editor-fold>

                                section.setTextList(textList);

                            } else {//end if sectionElement != null
                                //System.out.println("XmlSection " + section + " empty or does not exist");
                            }//end else if sectionElement != null
                            //</editor-fold>

                            // Hace una cuenta si ha sido un sección válida
                            if (isSection) {
                                // Agrega una sección a la lista de secciones
                                sectionList.add(section);
                                countSections++;
                            }

                        }//end foreach << Se recorren las secciones
                        //</editor-fold>

                        // Relaciona (agrega) la lista de secciones al documento
                        doc.setSectionList(sectionList);

                        // Agrega un documento a la lista de documentos
                        docList.add(doc);

                    } else {//end if oConnect.connection_().equals("OK")
                        // Mensaje mostrado al documento que no se pudo conectar
                        System.out.println(xmlLink.getUrl() + " ==> " + connection_.getStatus());
                    }//end else if oConnect.connection_().equals("OK")

                    // Relaciona (agrega) la lista de documentos a la fuente "Source"
                    source.setDocuments(docList);
                    countDoc++;
                }//end for String link: source.getLinkList()

                // Relaciona (agrega) una fuente "Source" a la lista de fuentes
                sourceList.add(source);

            }//end que solo sea la fuente wikipedia

            System.out.println("End to extraction...");
        }//end for XmlSource source: sourcesList

        // Retorna la lista de fuentes, con sus documentos, enfermedades, secciones, códigos y textos...
        return sourceList;

    }


    /**
     * @param tagName
     * @return
     */
    public boolean isElementIsATypeOfList(String tagName){
        boolean res = false;
        for (String listElement: Constants.HTML_LIST_TAG_PARENTS_LIST_TAG){
            res = tagName.equals(listElement);
            if(res) break;
        }
        return res;
    }


    /**
     * @param element
     * @param isText
     * @param list_
     * @param countText
     * @param title
     * @param textList
     * @return
     */
    public boolean verifyList(Element element, boolean isText, List_ list_, int countText, String title, List<Text> textList){
        boolean res = false;
        Elements luChildrens = element.children();//System.out.println(element.toString() +" - " + nextElementBro.toString());
        for (Element childElement: luChildrens) {
            if (childElement != null && (childElement.tagName() == Constants.HTML_UL || childElement.tagName() == Constants.HTML_OL) ){
                //System.out.println("ENTRA_3");
                res = verifyExistList(childElement, isText, list_, countText, title, textList);
                if (res){
                    countText++;
                    verifyList(childElement, isText, list_, countText, title, textList);
                }
            }
        }
        return res;
    }


    /**
     * @param element
     * @param isText
     * @param list_
     * @param countText
     * @param title
     * @param textList
     * @return
     */
    public boolean verifyExistList(Element element, boolean isText, List_ list_, int countText, String title, List<Text> textList){
        boolean res = false;
        if (element.tagName() == Constants.HTML_UL || element.tagName() == Constants.HTML_OL) {//&& !nextElementBro.text().isEmpty()
            //<editor-fold desc="EXTRAE TEXTO DE UN PARRAFO Y LO ALMACENA EN UN OBJETO LIST_">
            // Guarda la información extraida de una lista wikipedia en un objeto
            //System.out.println("ENTRA_2");
            isText = true;
            list_ = setList_Data(element, countText, title);
            if (list_ != null) {
                // Agrega la lista "List_" a la lista de textos
                textList.add(list_);
                res = true;
                //verifyMoreListInsideAList(element, isText, list_, countText, title, textList);
            }
            //</editor-fold>
        }
        return res;
    }


    public void checkWikiPages() throws Exception {
        Connection_ connection_;
        for (XmlSource xmlSource : loadSource.loadSources()) {
            // VALIDAR QUE SOLO SE RECUPERE INFORMACIÓN DE WIKIPEDIA
            if (xmlSource.getName().equals(Constants.SOURCE_WIKIPEDIA)) {
                // Se leen las secciones del XML
                for (XmlSection xmlSection : xmlSource.getSectionList()) {
                    System.out.println(xmlSection.getId());
                }
                // Se inicializa un contador para todos los documentos
                int countDoc = 1;
                // Se leen todos los enlaces a los documentos de wikipedia (https...)
                for (XmlLink xmlLink : xmlSource.getLinkList()) {
                    // Se conecta con el documento wikipedia por medio de su enlace
                    connection_ = connectDocument.connect(xmlLink.getUrl());
                    // Se verifica si hubo conexión con el documento (enlace Web)

                        // Se pinta en pantalla el status OK (esta disponible el enlace)
                        System.out.println(countDoc + " check " + xmlLink.getUrl() + " ==> " + connection_.getStatus());
                    countDoc++;
                }
            }
        }
    }


    /**
     * Método que extrae de todos los documentos wikipedia las fuentes de sus códigos de su infobox
     *
     * @return lista "HashMap" sin elementos repetidos de las fuentes de códigos. Ej. eMedicine, Medline, Patient PK
     * @throws Exception
     */
    public HashMap<String, Resource> extractResource(List<XmlLink> externalDiseaseLinkList) throws Exception {

        System.out.println("Preparing resource model...");
        System.out.println("Reading data resource from Wikipedia...");//System.out.println(date.getSqlDate());

        //<editor-fold desc="VARIABLES DE INICIO">
        Connection_ connection_;
        Document oDoc;
        Resource resource;
        Code oCode;
        Link url;

        HashMap<String, Resource> resourceMap = new HashMap<>();
        List<Code> codeList = new ArrayList<>();
        //</editor-fold>

        for (XmlSource xmlSource: loadSource.loadSources()) {

            // VALIDAR QUE SOLO SE RECUPERE INFORMACIÓN DE WIKIPEDIA
            if (xmlSource.getName().equals(Constants.SOURCE_WIKIPEDIA)) {

                //<editor-fold desc="LISTA DE ENFERMEDADES LLAMANDO A LA API REST GET_DISEASELIST_FROM_DBPEDIA">
                List<XmlLink> linkListFromDBPedia = externalDiseaseLinkList;
                if (linkListFromDBPedia != null) {
                    if (!linkListFromDBPedia.isEmpty()) {
                        xmlSource.setLinkList(linkListFromDBPedia);
                        System.out.println("getDiseaseLinkListFromDBPedia.size(): " + xmlSource.getLinkList().size());
                    }
                }
                //</editor-fold>
                int x = 1;
                for (XmlLink oXmlLink: xmlSource.getLinkList()) {
                    connection_ = connectDocument.connect(oXmlLink.getUrl());

                    /* Se obtiene el nombre de la enfermedad dentro del documento */
                    String idElementName = getHighlightXmlByDescription(Constants.XML_HL_DISEASENAME, xmlSource).getId();

                    //Verificación de la conexión del enlace >
                    System.out.println(x + " wikipediaExtract codes (" + oXmlLink.getUrl() + ") ==> " + connection_.getStatus() + "("+connection_.getStatusCode()+")");
                    if (connection_.getStatus().equals(StatusHttpEnum.OK.getDescripcion()) && connection_.getoDoc()!=null && connection_.getoDoc().getElementById( idElementName ).text()!=null ) {

                            //Se obtiene el documento html "DOM"
                            oDoc = connection_.getoDoc();


                            String diseaseName = oDoc.getElementById( idElementName ).text();
                            //                    System.out.println("Disease: " + oDoc.getElementById( idElementName ).text() );


                            /*
                                Se obtiene el elemento (tabla) con clase "infobox" NOTA. infobox es un elemento Highlight
                                del documento source.xml
                                IMPORTANTE. La siguiente rutina sirve para los infobox que se encuentran en el pie
                                del documento wikipedia. Es necesario otro para el infobox ubicado al principio.
                             */

                            // Se arma la consulta para buscar el elemento  */
                            String query = getHighlightXmlByDescription( Constants.XML_HL_INFOBOX, xmlSource).getClass_();
                            // Se ejecuta la consulta
                            Elements infoboxElements = oDoc.select( consultTabByClass( query ) );

                            int countCode = 0;
                            for (Element infobox:
                                    infoboxElements) {

                                String infoboxSection = "";
                                boolean hasValidSection = false;

                                // Se obtienen los elemtos <tr> (filas) de la tabla con class=infobox
                                Elements rowElements = infobox.select(Constants.HTML_TABLE_TR);

                                //<editor-fold desc="RECORRIDO DE LAS FILAS DE LA TABLA INFOBOX">
                                for (Element row: rowElements) {

                                /* Se almecenan por cada fila <tr> el valor llave <th> y su valor <td> */
                                    Elements thElements = row.select(Constants.HTML_TABLE_TH);
                                    Elements tdElements = row.select(Constants.HTML_TABLE_TD);
                                    Elements liElements_ = row.select(Constants.HTML_LI);
                                    Elements divElements = row.select(Constants.HTML_DIV);

                                    //<editor-fold desc="PROCESO PARA EL INFOBOX EN EL PRINCIPIO DEL DOCUMENTO">

                                    //<editor-fold desc="VALIDACIONES">
                                    // Se obtienen los elementos <a> que tengan class: external text
                                    // No se pueden leer los códigos que no tengan algun enlace (ver:ICD-10 en.wikipedia.org/wiki/Factor_V_Leiden )
                                    String findExternalText = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                                    Elements externalTextElements = tdElements.select(Constants.QUERY_A_CLASS + findExternalText + Constants.RIGHT_PARENTHESIS);
                                    // Se verifica (boolean) que en la fila se encuentre un class: external text
                                    boolean hasExternalText = (externalTextElements.size()>0);

                                    // Se obtienen los elementos <div>??? que tengan class: plainlist
                                    String findPlainList = getHighlightXmlByDescription(Constants.XML_HL_PLAIN_LIST + "", xmlSource).getClass_();
                                    Elements plainListElements = tdElements.select(Constants.QUERY_TD_CLASS + findPlainList + Constants.RIGHT_PARENTHESIS);
                                    Elements divPlainListElements = tdElements.select(Constants.QUERY_DIV_CLASS + findPlainList + Constants.RIGHT_PARENTHESIS);

                                    // Se verifica (boolean) que en la fila se encuentre un class: plainlist
                                    boolean hasPlainList = ( plainListElements.size()>0) ;
                                    boolean hasDivPlainList = ( divPlainListElements.size()>0) ;

                                    // Se obtienen los elementos <div>??? que tengan class: hlist
                                    //<<CAMBIO>> Se ha cambiado la clase hlist a hlist hlist-separated
                                    // Se verifica (boolean) que en la fila se encuentre un class: hlist
                                    boolean hasHorizontalList = existHorizontalInfobox(xmlSource, tdElements);

                                    // Verificar si la fila con <th></th> sin <td></td> es una de las secciones válidas
                                    String findTextAlignCenter = getHighlightXmlByDescription(Constants.XML_HL_TEXT_ALIGN_CENTER + "", xmlSource).getId();
                                    boolean hasStyleAlignCenterElements = common.itsFound( row.toString().replaceAll("\\s",""),  findTextAlignCenter);

                                    if( ( thElements.hasAttr(Constants.HTML_COLSPAN ) || tdElements.hasAttr(Constants.HTML_COLSPAN) )
                                            && hasStyleAlignCenterElements && divElements.size() < 1
                                            ){
                                        if (!thElements.text().trim().equals("")){
                                            infoboxSection = thElements.text().trim();
                                        }else if (!tdElements.text().trim().equals("")) {
                                            infoboxSection = tdElements.text().trim();
                                        }
                                    }
                                    hasValidSection = isAValidInfoboxSection( infoboxSection );
                                    //                            System.out.println("<<hasValidSection>>: " + hasValidSection);

                                    boolean validLiElement = false;
                                    if(liElements_.size() <= 0){ validLiElement = true; }
                                    else if(liElements_.size() > 0){
                                        validLiElement = hasPlainList || hasDivPlainList;
                                    }
                                    //</editor-fold>

                                    /** Se valida: 1) que en la fila se encuente un elemento con class: external text,
                                     * 2) que no tenga elementos <li>, 3) que no tenga colspan (que no se una sola columna) */
                                    //                            System.out.println(thElements.text() + " => hasExternalText: " + hasExternalText + " && validLiElement: " + validLiElement + " && hasValidSection("+infoboxSection+"):" + hasValidSection + " && !thElements.hasAttr(Constants.HTML_COLSPAN): " + !thElements.hasAttr(Constants.HTML_COLSPAN));
                                    if( hasExternalText && validLiElement && hasValidSection ){

                                    /* Dentro deL infobox <table> se analiza sus elementos <th> */
                                        //<editor-fold desc="OBTERNER FUENTES EXTERNAS PARA UNA ENFERMEDAD">
                                        //resource = null;
                                        resource = new Resource();
                                        for (Element thElement:
                                                thElements) {
                                            List<String> linkList = new ArrayList<>();
                                        /* Obtener los "resources" y sus enlaces (vocabularios, bases online libres o servicios) */
                                            //                                    System.out.println( "    Resource(HTML_A): " + thElements.text() );
                                            Elements links = thElement.getElementsByTag( Constants.HTML_A +"" );
                                            for (Element link:
                                                    links) {
                                                //                                        System.out.println( "           URL:" + link.attr(Constants.HTML_HREF).toString() );
                                                linkList.add( link.attr(Constants.HTML_HREF).trim() );
                                            }
                                            resource.setName( thElements.text().trim() );
                                            //                                    resource.setLinkList( linkList );
                                            resource.setNameDisease( diseaseName );

                                            resourceMap.put( resource.getName(), resource );

                                        }//</editor-fold>

                                    /* Dentro deL infobox <table> se analiza sus elementos <td> */
                                        //<editor-fold desc="OBTERNER CÓDIGOS DE LAS FUENTES EXTERNAS">
                                        for (Element tdElement:
                                                tdElements) {
                                        /* Obtener los códigos de los vocabularios, bases online libres o servicios
                                         * Se obtienen los elementos <a> con class="external text" y su atributo href */
                                            String class_ = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                                            Elements codeElements = tdElement.select(Constants.QUERY_A_CLASS + class_ + Constants.RIGHT_PARENTHESIS);
                                            for (Element code :
                                                    codeElements) {
                                                oCode = new Code();
                                                url = new Link();

                                                oCode.setId( countCode );
                                                oCode.setCode( code.text() );

                                                url.setId( countCode );
                                                url.setUrl( code.attr(Constants.HTML_HREF).toString() );

                                                oCode.setLink( url );
                                                oCode.setResource( resource );
                                                codeList.add( oCode );
                                                //                                        System.out.println("       Code: " + code.text() + " | URL:" + code.attr(Constants.HTML_HREF).toString());
                                            }
                                        }//</editor-fold>
                                    }//</editor-fold>
                                    //System.out.println("    QUE PASA " );

                                    //<editor-fold desc="PROCESO PARA EL INFOBOX EN EL PIE DEL DOCUMENTO">
                                /* Dentro de una fila <tr> se recorren los elementos <td> */
                                    if(hasHorizontalList) {//System.out.println("    ENTRA " );
                                        Resource resourceFather = new Resource();
                                        for (Element tdElement : tdElements) {
                                        /* Dentro de un <td> se seleccionan todos los elementos <li> */
                                            Elements liElements = tdElement.select(Constants.HTML_LI);
                                            for (int i = 0; i < liElements.size(); i++) {
                                        /* Obtiene los "resources" y sus enlaces (vocabularios, bases online libres o servicios) */
                                                //<editor-fold desc="OBTERNER FUENTES EXTERNAS PARA UNA ENFERMEDAD">
                                        /* Se obtiene el elemento <b> que es el nombre del "resource"*/
                                                Elements bElements = liElements.get(i).getElementsByTag(Constants.HTML_B);
                                                resource = new Resource();
                                                List<String> linkList = new ArrayList<>();
                                                for (Element b :
                                                        bElements) {
                                                    Elements aElements = bElements.select(Constants.HTML_A);
                                                    /** Condición para eliminar los resources no válidos (aquellos que no contiene
                                                     *  un enlace de cualquier tipo * no es lo mejor) */
                                                    if (aElements.size() > 0) {

                                                        //System.out.println("    Resource(HTML_B): " + b.text());
                                            /* Se obtienen los enlaces de un "resource"*/
                                                        Elements links = b.getElementsByTag(Constants.HTML_A + "");
                                                        for (Element link :
                                                                links) {
                                                            //                                                    System.out.println("           URL(HTML_B):" + link.attr(Constants.HTML_HREF).toString());
                                                            linkList.add(link.attr(Constants.HTML_HREF).toString().trim());
                                                        }
                                                        resource.setName(b.text().trim());
                                                        //                                                resource.setLinkList(linkList);
                                                        resource.setNameDisease(diseaseName);

                                                        resourceFather = resource;

                                                        resourceMap.put(resource.getName(), resource );
                                                    }
                                                }
                                                //</editor-fold>

                                        /* Obtiene los códigos y su enlace de los vocabularios, bases online libres o servicios */
                                                //<editor-fold desc="OBTERNER CÓDIGOS DE LAS FUENTES EXTERNAS">
                                        /* Se obtienen los elementos <a> con class="external text" y su attr href */
                                                String class_ = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                                                Elements codeElements = liElements.get(i).select(Constants.QUERY_A_CLASS + class_ + Constants.RIGHT_PARENTHESIS);
                                                for (Element code :
                                                        codeElements) {
                                                    oCode = new Code();
                                                    url = new Link();

                                                    oCode.setId( countCode );
                                                    oCode.setCode( code.text() );

                                                    url.setId( countCode );
                                                    url.setUrl( code.attr(Constants.HTML_HREF).toString() );

                                                    oCode.setLink( url );
                                                    oCode.setResource( resourceFather );
                                                    codeList.add( oCode );
                                                    //                                            System.out.println("       Code(HTML_B): " + code.text() + " | URL:" + code.attr(Constants.HTML_HREF).toString() + " R: " + resourceFather.getName());
                                                }
                                                //</editor-fold>
                                            }
                                        }
                                    }//</editor-fold>

                                }//end for (Element row: rowElements)
                                //</editor-fold>


                            }
                    }
                    x++;

                }

            }// end validación de solo leer la fuente wikipedia
        }//end recorrido de todas las fuentes

/*
        System.out.println("Code list...");
        for (Code code:
                codeList) {
            System.out.println("Code: " + code.getCui() + " URL_CODE:" + code.getLink().getUrl() );
            System.out.println("    Resource: " + code.getResource().getName());
        }
        System.out.println("------------------------------------");
*/

        System.out.println("Report the unique resources find in Wikipedia... (Complete list)");
        resourceMap.forEach( (k,v) -> System.out.println(" Resource name(Key): " + k + " : Disease (Value): " + v.getNameDisease() + " | " + v.getName()) );



        return resourceMap;

    }


    public boolean existHorizontalInfobox(XmlSource xmlSource, Elements tdElements){
        //<<CAMBIO>> Se ha modificado el nombre del class "hlist" a "hlist hlist-separated"
        String findHorizontalList = getHighlightXmlByDescription(Constants.XML_HL_HORIZONTAL_LIST + "", xmlSource).getClass_();
        Elements hListElements = tdElements.select(Constants.QUERY_DIV_CLASS + findHorizontalList + Constants.RIGHT_PARENTHESIS);
        String findHorizontalList_v1 = getHighlightXmlByDescription(Constants.XML_HL_HORIZONTAL_LIST + "_v1", xmlSource).getClass_();
        Elements hListElements_v1 = tdElements.select(Constants.QUERY_DIV_CLASS + findHorizontalList_v1 + Constants.RIGHT_PARENTHESIS);
        // Se verifica (boolean) que en la fila se encuentre un class: hlist
        boolean hasHorizontalList = ( hListElements.size()>0 || hListElements_v1.size()>0);//System.out.println("QUE ES: "+hListElements.size() + "QUE ES: "+hListElements_v1.size());
        return hasHorizontalList;

    }


    /**
     * Método que extrae los códigos y sus fuentes de un documento
     *
     * @param document
     * @param xmlSource
     * @return lista de objetos Code
     * @throws Exception
     */
    public List<Code> getCodes(Document document, XmlSource xmlSource) throws Exception{

        Resource resource;
        Code oCode;
        Link url;

        Map<String, Resource> resourceMap = new HashMap<>();
        List<Code> codeList = new ArrayList<>();

        // Se arma la consulta para buscar el elemento  */
        String query = getHighlightXmlByDescription( Constants.XML_HL_INFOBOX, xmlSource).getClass_();
        // Se ejecuta la consulta
        Elements infoboxElements = document.select( consultTabByClass( query ) );

        int countResource = 1;
        int countCode = 1;
        for (Element infobox:
                infoboxElements) {

            String infoboxSection = "";
            boolean hasValidSection = false;

            // Se obtienen los elemtos <tr> (filas) de la tabla con class=infobox
            Elements rowElements = infobox.select(Constants.HTML_TABLE_TR);

            //<editor-fold desc="RECORRIDO DE LAS FILAS DE LA TABLA INFOBOX">
            for (Element row: rowElements) {

                        /* Se almecenan por cada fila <tr> el valor llave <th> y su valor <td> */
                Elements thElements = row.select(Constants.HTML_TABLE_TH);
                Elements tdElements = row.select(Constants.HTML_TABLE_TD);
                Elements liElements_ = row.select(Constants.HTML_LI);
                Elements divElements = row.select(Constants.HTML_DIV);

                //<editor-fold desc="PROCESO PARA EL INFOBOX EN EL PRINCIPIO DEL DOCUMENTO">
                //<editor-fold desc="VALIDACIONES">
                // Se obtienen los elementos <a> que tengan class: external text
                // No se pueden leer los códigos que no tengan algun enlace (ver:ICD-10 en.wikipedia.org/wiki/Factor_V_Leiden )
                String findExternalText = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                Elements externalTextElements = tdElements.select(Constants.QUERY_A_CLASS + findExternalText + Constants.RIGHT_PARENTHESIS);
                // Se verifica (boolean) que en la fila se encuentre un class: external text
                boolean hasExternalText = (externalTextElements.size()>0);

                // Se obtienen los elementos <div>??? que tengan class: plainlist
                String findPlainList = getHighlightXmlByDescription(Constants.XML_HL_PLAIN_LIST + "", xmlSource).getClass_();
                Elements plainListElements = tdElements.select(Constants.QUERY_TD_CLASS + findPlainList + Constants.RIGHT_PARENTHESIS);
                Elements divPlainListElements = tdElements.select(Constants.QUERY_DIV_CLASS + findPlainList + Constants.RIGHT_PARENTHESIS);

                // Se verifica (boolean) que en la fila se encuentre un class: plainlist
                boolean hasPlainList = ( plainListElements.size()>0) ;
                boolean hasDivPlainList = ( divPlainListElements.size()>0) ;

                // Se obtienen los elementos <div>??? que tengan class: "hlist"
                //<<CAMBIO>> Se ha modificado el nombre del class "hlist" a "hlist hlist-separated"
                boolean hasHorizontalList = existHorizontalInfobox(xmlSource, tdElements);

                // Verificar si la fila con <th></th> sin <td></td> es una de las secciones válidas
                String findTextAlignCenter = getHighlightXmlByDescription(Constants.XML_HL_TEXT_ALIGN_CENTER + "", xmlSource).getId();
                boolean hasStyleAlignCenterElements = common.itsFound( row.toString().replaceAll("\\s",""),  findTextAlignCenter);

                if( ( thElements.hasAttr(Constants.HTML_COLSPAN ) || tdElements.hasAttr(Constants.HTML_COLSPAN) )
                        && hasStyleAlignCenterElements && divElements.size() < 1
                        ){
                    if (!thElements.text().trim().equals("")){
                        infoboxSection = thElements.text().trim();
                    }else if (!tdElements.text().trim().equals("")) {
                        infoboxSection = tdElements.text().trim();
                    }
                }
                hasValidSection = isAValidInfoboxSection( infoboxSection );
//                            System.out.println("<<hasValidSection>>: " + hasValidSection);

                boolean validLiElement = false;
                if(liElements_.size() <= 0){ validLiElement = true; }
                else if(liElements_.size() > 0){
                    validLiElement = hasPlainList || hasDivPlainList;
                }
                //</editor-fold>

                /** Se valida: 1) que en la fila se encuente un elemento con class: external text,
                 * 2) que no tenga elementos <li>, 3) que no tenga colspan (que no se una sola columna)
                 * */
//                System.out.println(thElements.text() + " => hasExternalText: " + hasExternalText + " && validLiElement: " + validLiElement + " && hasValidSection("+infoboxSection+"):" + hasValidSection + " && !thElements.hasAttr(Constants.HTML_COLSPAN): " + !thElements.hasAttr(Constants.HTML_COLSPAN));
                if( hasExternalText && validLiElement && hasValidSection ){

                    /* Dentro deL infobox <table> se analiza sus elementos <th> */
                    //<editor-fold desc="OBTERNER FUENTES EXTERNAS PARA UNA ENFERMEDAD">
                    //resource = null;
                    resource = new Resource();
                    for (Element thElement:
                            thElements) {
                        List<String> linkList = new ArrayList<>();
                                /* Obtener los "resources" y sus enlaces (vocabularios, bases online libres o servicios) */
//                                    System.out.println( "    Resource(HTML_A): " + thElements.text() );
                        Elements links = thElement.getElementsByTag( Constants.HTML_A +"" );
                        for (Element link:
                                links) {

//                                        System.out.println( "           URL:" + link.attr(Constants.HTML_HREF).toString() );
                            linkList.add( link.attr(Constants.HTML_HREF).trim() );
                        }
                        resource.setId( countResource );
                        resource.setName( thElements.text().trim() );
//                        resource.setLinkList( linkList );
//                        resource.setNameDisease( diseaseName );
                    }//</editor-fold>

                            /* Dentro deL infobox <table> se analiza sus elementos <td> */
                    //<editor-fold desc="OBTERNER CÓDIGOS DE LAS FUENTES EXTERNAS">
                    for (Element tdElement:
                            tdElements) {
                                /* Obtener los códigos de los vocabularios, bases online libres o servicios
                                 * Se obtienen los elementos <a> con class="external text" y su atributo href */
                        String class_ = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                        Elements codeElements = tdElement.select(Constants.QUERY_A_CLASS + class_ + Constants.RIGHT_PARENTHESIS);
                        for (Element code :
                                codeElements) {
                            oCode = new Code();
                            url = new Link();

                            oCode.setId( countCode );
                            oCode.setCode( code.text() );

                            url.setId( countCode );
                            url.setUrl( code.attr(Constants.HTML_HREF).toString() );

                            oCode.setLink( url );
                            oCode.setResource( resource );
                            codeList.add( oCode );
                            countCode++;
//                                        System.out.println("       Code: " + code.text() + " | URL:" + code.attr(Constants.HTML_HREF).toString());
                        }
                    }//</editor-fold>
                }//</editor-fold>


                //<editor-fold desc="PROCESO PARA EL INFOBOX EN EL PIE DEL DOCUMENTO">
                        /* Dentro de una fila <tr> se recorren los elementos <td> */
                if(hasHorizontalList) {
                    getCodesAndResourceFromHorizontalList(xmlSource, tdElements, countResource, resourceMap, countCode, codeList);
                }//</editor-fold>

            }//</editor-fold> //end for (Element row: rowElements)
            countResource++;
        }

        return codeList;

    }


    public void getCodesAndResourceFromHorizontalList(XmlSource xmlSource, Elements tdElements, int countResource, Map<String, Resource> resourceMap, int countCode, List<Code> codeList){
        Resource resourceFather = new Resource();
        String resourceWithoutUrl = "";
        for (Element tdElement : tdElements) {
                                /* Dentro de un <td> se seleccionan todos los elementos <li> */
            Elements liElements = tdElement.select(Constants.HTML_LI);
            for (int i = 0; i < liElements.size(); i++) {
                                /* Obtiene los "resources" y sus enlaces (vocabularios, bases online libres o servicios) */
                //<editor-fold desc="OBTERNER FUENTES EXTERNAS PARA UNA ENFERMEDAD">
                                /* Se obtiene el elemento <b> que es el nombre del "resource"*/
                Elements bElements = liElements.get(i).getElementsByTag(Constants.HTML_B);
                Resource resource = new Resource();
                List<String> linkList = new ArrayList<>();
                for (Element b : bElements) {
                    resourceWithoutUrl = b.text().trim();
                    Elements aElements = bElements.select(Constants.HTML_A);
                    /** Condición para eliminar los resources no válidos (aquellos que no contiene
                     *  un enlace de cualquier tipo * no es lo mejor) */
                    if (aElements.size() > 0) {

//                                                System.out.println("    Resource(HTML_B): " + b.text());
                                    /* Se obtienen los enlaces de un "resource"*/
                        Elements links = b.getElementsByTag(Constants.HTML_A + "");
                        for (Element link :
                                links) {
//                                                    System.out.println("           URL(HTML_B):" + link.attr(Constants.HTML_HREF).toString());
                            linkList.add(link.attr(Constants.HTML_HREF).toString().trim());
                        }
                        resource.setId( countResource );
                        resource.setName(b.text().trim());
//                                    resource.setLinkList(linkList);
//                                    resource.setNameDisease(diseaseName);
                        resourceFather = resource;
                        resourceMap.put(resource.getName(), resource);
                    }else{
                        //System.out.println("aElements.size()<0");
                    }
                }
                //</editor-fold>

                /* Obtiene los códigos y su enlace de los vocabularios, bases online libres o servicios */
                //<editor-fold desc="OBTERNER CÓDIGOS DE LAS FUENTES EXTERNAS">
                /* Se obtienen los elementos <a> con class="external text" y su attr href */
                String class_ = getHighlightXmlByDescription(Constants.XML_HL_EXTERNAL_TEXT + "", xmlSource).getClass_();
                Elements codeElements = liElements.get(i).select(Constants.QUERY_A_CLASS + class_ + Constants.RIGHT_PARENTHESIS);
                for (Element code : codeElements) {
                    Code oCode = new Code();
                    Link url = new Link();

                    oCode.setId( countCode );
                    oCode.setCode( code.text() );

                    url.setId( countCode );
                    url.setUrl( code.attr(Constants.HTML_HREF).toString() );

                    oCode.setLink(url);
                    if (common.isEmpty(resourceFather.getName()) && !common.isEmpty(oCode.getCode())){
                        //System.out.println("resource null " + oCode.getCode() + " " + resourceWithoutUrl);
                        oCode.setResource(new Resource(99, resourceWithoutUrl));
                    }else{
                        oCode.setResource( resourceFather );
                    }
                    codeList.add(oCode);
                    countCode++;
                    //System.out.println("       Code(HTML_B): " + code.text() + " | URL:" + code.attr(Constants.HTML_HREF).toString() + " R: " + resourceFather.getName());
                }
                //</editor-fold>
            }
        }
    }


    /**
     * Método que muestra un reporte de la extracción recien hecha
     *
     * @throws Exception
     */
    public void extractionReport(List<XmlLink> externalDiseaseLinkList) throws Exception {

        List<Integer> countCharacteresList = new ArrayList<>();

        long time_start, time_end;

        time_start = System.currentTimeMillis();
        List<Source> sourceList = extract(externalDiseaseLinkList);
        time_end = System.currentTimeMillis();


        System.out.println("-------------------- EXTRACTION REPORT --------------------");
        for (Source source :
                sourceList) {

            System.out.println("\n");
            System.out.println("-------------------- SOURCE(" + source.getId() + "_" + source.getName() + ") --------------------");

            for (Doc document: source.getDocuments()) {

                System.out.println("Document(" + document.getId() + "_" + document.getDate() + ") => " + document.getUrl().getUrl());
                System.out.println("    Disease(" + document.getDisease().getId() + "_" + document.getDisease().getName() + ") ");

                System.out.println("    Codes list...:");
                for (Code code:
                        document.getCodeList()) {
                    System.out.println("        Code_" + code.getId() + "[" + code.getResource().getName() + "]: " + code.getCode() + " URL_CODE:" + code.getLink().getUrl() );
                }

                for (Section section:
                        document.getSectionList()) {
                    System.out.println("    Section(" + section.getId() + ") " + section.getName() );

                    for (Text text :
                            section.getTextList()) {
                        System.out.println("    ------------ TEXT(" + text.getTitle() + ") -----------");
                        System.out.println("        Text_" + text.getTextOrder() + "(" + text.getId() + ") (" + text.getClass() + ")" );

                        String aux = "";
                        if(text instanceof Paragraph){
                            System.out.println("            " + ( (Paragraph) text).getText() );
                            countCharacteresList.add( ( (Paragraph) text).getText().length() );
                        }else if(text instanceof List_){
                            for (String bullet: ( (List_) text).getBulletList() ) {
                                System.out.println("            -" + bullet);
                                aux = aux + bullet + "&";
                            }
                            //if(aux.length() > 2){aux = aux.substring(0, aux.length()-1);}
                            //System.out.println(" aux = " + aux);
                            countCharacteresList.add( aux.length() );
                        }

                        System.out.println("        ------------ LINKS -----------");
                        for (Link url:
                                text.getUrlList()) {
                            System.out.println("            Key: " + url.getId() + ": URL(" + url.getDescription() + "): " + url.getUrl() );
                        }

                    }
                }

            }

        }



/*
        System.out.println("=========================================================");
        Collections.sort(countCharacteresList);
        for (Integer i:
             countCharacteresList) {
            System.out.println(i);
        }
*/



        System.out.println("the task (model informtacion of diseases from wikipedia) has taken "+ ( (time_end - time_start) / 1000 ) +" seconds");

    }



    /**
     * Método que recupera información de un párrafo dentro de una sección
     *
     * @param element
     * @param countText
     * @param title
     * @return objeto Paragraph
     */
    public Paragraph setParagraphData(Element element, int countText, String title){
        Paragraph paragraph = null;

        if (!common.isEmpty(element.text())){
            paragraph = new Paragraph();
            paragraph.setId( countText );
            paragraph.setText( element.text() );
            paragraph.setTextOrder( countText );
            paragraph.setTitle(title);

            // Agrega la lista de enlaces al párrafo
            paragraph.setUrlList( getTextUrls( element ) );
        }

        return paragraph;
    }


    /**
     * Método que recupera información de una lista dentro de una sección
     *
     * @param element
     * @param countText
     * @param title
     * @return objeto List_
     */
    public List_ setList_Data(Element element, int countText, String title){
        List_ list_ = null;
        List<String> liList = new ArrayList<>();
        // Recorrido de la lista. Recorre los elementos <li> de un <ul>
//        Elements li = element.select(Constants.HTML_LI); // select all li from ul
        //Actualización para que recorra todos los elementos hijos de ol, ul y dl
        for (String listItem: Constants.HTML_LIST_TAG_CHILD_LIST_TAG){
            Elements childTagList = element.select(listItem);
            for (int i = 0; i < childTagList.size(); i++) {
                // Agrega las filas a la lista de elementos de una lista
                liList.add( childTagList.get(i).text() );
            }
            if (liList.size() > 0){
                list_ = new List_();
                list_.setId( countText );
                list_.setTextOrder( countText );
                list_.setTitle(title);
                // Agrega la lista al objeto lista "List_"
                list_.setBulletList( liList );

                // Agrega la lista de enlaces al objeto lista "List_"
                list_.setUrlList( getTextUrls( element ) );
            }
        }

        return list_;
    }


    public Table extractWikitableTexts(Elements trElements, int countText, String title){
        Table table = null;
        String head = "";
        String body = "";

        for (Element tr : trElements) {
            Elements tds = tr.getElementsByTag(Constants.HTML_TABLE_TD);
            Elements ths = tr.getElementsByTag(Constants.HTML_TABLE_TH);
            for (Element th_: ths) {
                head += th_.text() + " ";
                //System.out.println("th: " + th_.text());
            }
            for (Element td_: tds) {
                body += td_.text() + " ";
                //System.out.println("td: " + td_.text());
            }
        }

        if (!common.isEmpty(head) && !common.isEmpty(body)){
            table = new Table();
            List<Tr> trList = new ArrayList<>();;
            table.setId( countText );
            table.setTextOrder( countText );
            table.setTitle(title);//System.out.println("Wikitable: " + title);
            Tr oTr_head = new Tr(head + " headingBody ");
            Tr oTr_body = new Tr(body);
            trList.add(oTr_head);
            trList.add(oTr_body);
            table.setTrList(trList);
            //System.out.println("th: " + head);
            //System.out.println("td: " + body);

            // Agrega la lista de enlaces al objeto lista "List_"
            table.setUrlList( getTableTextUrls( trElements ) );
        }
        return table;
    }


    /**
     * Método que recupera información de enlaces encontrados en cualquier bloque "elemento" del documento
     *
     * @param element
     * @return lista de objetos Link
     */
    public List<Link> getTextUrls(Element element){
        List<Link> urlList = new ArrayList<>();
        Link url;

        // Recorre para obtener todos los enlaces de la lista
        Elements aElements = element.select( Constants.QUERY_A_HREF );
        int countUrl = 1;
        for (Element a : aElements) {
            // Crear un enlace "Link"
            url = new Link();
            url.setId( countUrl );
            url.setUrl( a.attr( Constants.QUERY_ABS_HREF ) );// Obtiene la url absoluta
            url.setDescription( a.text() );

            // Agrea un enlace a la lista de enlaces
            urlList.add( url );
//                                            linkTextMap.put(a.text(), a.attr( Constants.QUERY_ABS_HREF ));
            countUrl++;
        }

        return  urlList;
    }


    /**
     * Método que recupera información de enlaces encontrados en cualquier bloque "elemento" del documento
     *
     * @param elements
     * @return lista de objetos Link
     */
    public List<Link> getTableTextUrls(Elements elements){
        List<Link> urlList = new ArrayList<>();
        Link url;

        // Recorre para obtener todos los enlaces de la lista
        Elements aElements = elements.select( Constants.QUERY_A_HREF );
        int countUrl = 1;
        for (Element a : aElements) {
            // Crear un enlace "Link"
            url = new Link();
            url.setId( countUrl );
            url.setUrl( a.attr( Constants.QUERY_ABS_HREF ) );// Obtiene la url absoluta
            url.setDescription( a.text() );//System.out.println(a.attr( Constants.QUERY_ABS_HREF ));

            // Agrea un enlace a la lista de enlaces
            urlList.add( url );
//                                            linkTextMap.put(a.text(), a.attr( Constants.QUERY_ABS_HREF ));
            countUrl++;
        }

        return  urlList;
    }


    /**
     * Método que valida una sección de un infobox a partir de una lista de secciones válidas
     *
     * @param section
     * @return true si la sección es válida, sino false
     */
    private boolean isAValidInfoboxSection(String section) {//System.out.println("Sección a analizar: " + section);
        for (int i = 0; i < Constants.WIKI_INFOBOX_SECTIONS.length; i++) {
            String validSection = Constants.WIKI_INFOBOX_SECTIONS[i];
            if (section.contains(validSection)) return true;
        }
        return false;
    }


    /**
     * Método que consulta los elementos relevantes (Highlight) en el XML según su descripción
     *
     * Con el fin de hacer consultas JSOUP más complejas valiendose de elementos importantes del documento
     * como lo puede ser la clase de los infobox o clases de lista que contienen códigos interesantes
     *
     * Ej, de descripción: diseasename, infobox, externalresource... ver etiqueta highlight de sources.xml
     *
     * @param description
     * @param xmlSource
     * @return objeto XmlHighlight
     */
    public XmlHighlight getHighlightXmlByDescription(String description, XmlSource xmlSource){
        XmlHighlight xmlHighlight = null;
        for (XmlHighlight oHighlight:
             xmlSource.getHighlightList()) {
            if( oHighlight.getDescription().equals(description) ){
                xmlHighlight = oHighlight;
                return xmlHighlight;
            }
        }
        return xmlHighlight;
    }


    /**
     * Método que arma la consulta a una tabla HTML según su clase
     *
     * @param class_
     * @return cadena con la consulta "query" a ejecutar
     */
    public String consultTabByClass(String class_){
        String sConsult = "";
        sConsult = Constants.QUERY_TABLE_CLASS + class_ + Constants.RIGHT_PARENTHESIS;
        return sConsult;
    }


    /**
     * @param elements
     * @return
     */
    public List<Code> removeRepetedCodes(List<Code> elements){
        List<Code> resList = elements;
        Set<Code> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.addAll(elements);
        elements.clear();
        elements.addAll(linkedHashSet);

        return resList;
    }

    /**
     * Método que identifica si un elemento HTML es una imagen
     *
     * @param image
     * @return true si es una imagen, sino false
     */
    public boolean isImage(String image){
        Pattern pattern = Pattern.compile( Constants.IMAGE_PATTERN );
        Matcher matcher = pattern.matcher(image);
        return matcher.matches();
    }


}
