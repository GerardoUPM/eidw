package edu.upm.midas.data.extraction.sources.wikipedia.service.testThread;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.model.*;
import edu.upm.midas.data.extraction.model.code.Code;
import edu.upm.midas.data.extraction.model.code.Resource;
import edu.upm.midas.data.extraction.model.text.List_;
import edu.upm.midas.data.extraction.model.text.Paragraph;
import edu.upm.midas.data.extraction.model.text.Text;
import edu.upm.midas.data.extraction.service.ConnectDocument;
import edu.upm.midas.data.extraction.service.LoadSource;
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
import java.util.concurrent.*;
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
public class ExtractionWikipediaThreads {

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
    public List<Source> extract() throws Exception {

        //<editor-fold desc="VARIABLES DE INICO">
        Source source;
        Link url;

        List<Source> sourceList;
        List<Doc> docList;

        TreeMap<String, String> sectionMap;

        int countDoc;

        ExecutorService executorService = Executors.newFixedThreadPool(7);
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
                // Se crea una lista de documentos "Doc". Doc: es una documento de wikipedia
                docList = new ArrayList<>();
                // Se inicializa un contador para todos los documentos
                countDoc = 0;
                // Se leen todos los enlaces a los documentos de wikipedia (https...)
                for (XmlLink xmlLink : xmlSource.getLinkList()) {
                    // Agrega un documento a la lista de documentos
                    countDoc++;
                    try {
                        Future<Doc> docCall = executorService.submit(new Extract(xmlSource, xmlLink, countDoc));
//                        while (!docCall.isDone()){System.out.println("Esperando respuesta... ");}
//                        if (docCall.isDone()) {
                            Doc doc = docCall.get();
                            if (doc != null) {
                                docList.add(doc);
                            }
//                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                        //docList.add( getWikipediaDocument( xmlSource, xmlLink, countDoc ) );
                        // Relaciona (agrega) la lista de documentos a la fuente "Source"
                        source.setDocList(docList);


                }//end for String link: source.getLinkList()

                executorService.shutdown();
                try {
                    executorService.awaitTermination(5, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    System.out.println("Main thread interrupted while waiting for players to finish");
                }

                // Relaciona (agrega) una fuente "Source" a la lista de fuentes
                sourceList.add(source);

            }//end que solo sea la fuente wikipedia

            System.out.println("End to extraction...");
        }//end for XmlSource source: sourcesList

        // Retorna la lista de fuentes, con sus documentos, enfermedades, secciones, códigos y textos...
        return sourceList;

    }


    public Doc getWikipediaDocument(XmlSource xmlSource, XmlLink xmlLink, int countDoc) throws Exception {

        //<editor-fold desc="VARIABLES DE INICO">
        Connection_ connection_;
        Document document;

        Doc doc;
        Disease disease;
        Section section;
        Paragraph paragraph;
        List_ list_;
        Link url;

        List<Section> sectionList;
        List<Text> textList;

        int countSections;
        int countText;

        Boolean isSection;
        Boolean isText;
        //</editor-fold>

        // Se conecta con el documento wikipedia por medio de su enlace
        connection_ = connectDocument.connect(xmlLink.getUrl());
        // Se crea un nuevo documento (Doc), url (Link) y enfermedad (Disease)
        doc = new Doc();
        url = new Link();
        disease = new Disease();
        // Se verifica si hubo conexión con el documento (enlace Web)
        if (connection_.getStatus().equals(StatusHttpEnum.OK.toString())) {
            // Se pinta en pantalla el status OK (esta disponible el enlace)
            System.out.println(countDoc + " extract " + xmlLink.getUrl() + " ==> " + connection_.getStatus());
            // Se obtiene el documento HTML (página wikipedia)
            //<editor-fold desc="DOCUMENTOS">
            document = connection_.getoDoc();
            // Se obtiene el elemento HTML que almacena el nombre de la enfermedad
            String idElementName = getHighlightXmlByDescription(
                    Constants.XML_HL_DISEASENAME, xmlSource).getId();
            // Se inicia a introducir información de un documento
            doc.setId(countDoc);
            doc.setDate(date.getSqlDate());

            // Enlace del documento
            url.setId(countDoc);
            url.setUrl(xmlLink.getUrl());

            // Agrega el enlace al documento
            doc.setUrl(url);

            // Almacena información de la enfermdad
            disease.setId(countDoc);
            disease.setName(document.getElementById(idElementName).text());

            // Agrega la enfermedad al documento. En un documento se habla de una enfermedad
            doc.setDisease(disease);
            //</editor-fold>

            // Se llama al método que lee todos los códigos (de los infoboxs) de un documento, si los hay
            //<editor-fold desc="EXTRAER CÓDIGOS DE LOS INFOBOX">
            List<Code> codes = removeRepetedCodes(getCodes(document, xmlSource));
            doc.setCodeList(codes);
            //</editor-fold>

            // Crea lista de secciones
            sectionList = new ArrayList<>();
            countSections = 1;
            // Lee todas las secciones del XML (No todos los documentos tienen
            // información en las mismas secciones o incluso no las tienen)
            //<editor-fold desc="RECORRIDO DE SECCIONES DEL XML">
            for (XmlSection xmlSection :
                    xmlSource.getSectionList()) {
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
                        if (prevElementBro.tagName() == Constants.HTML_H3) {
                            if (prevElementBro.text() != null) title = prevElementBro.text();
                        }
                        //</editor-fold>

                        // Extrae el texto si es una etiqueta <p> (paragraph)
                        if (nextElementBro.tagName() == Constants.HTML_P) {
                            //<editor-fold desc="EXTRAE TEXTO DE UN PARRAFO Y LO ALMACENA EN UN OBJETO PARAGRAPH">
                            // Guarda la información extraida de un párrafo wikipedia en un objeto
                            isText = true;
                            // Se crea un párrafo y se extrae su información
                            paragraph = getParagraphData(nextElementBro, countText, title);

                            // Agrega el párrafo a la lista de textos
                            textList.add(paragraph);
                            //</editor-fold>
                            //Extrae el texto si es una etiqueta <ul> o <ol>
                        } else if (nextElementBro.tagName() == Constants.HTML_UL || nextElementBro.tagName() == Constants.HTML_OL) {
                            //<editor-fold desc="EXTRAE TEXTO DE UN PARRAFO Y LO ALMACENA EN UN OBJETO LIST_">
                            // Guarda la información extraida de una lista wikipedia en un objeto
                            isText = true;
                            list_ = getList_Data(nextElementBro, countText, title);

                            // Agrega la lista "List_" a la lista de textos
                            textList.add(list_);
                            //</editor-fold>
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
        } else {//end if oConnect.connection_().equals("OK")
        // Mensaje mostrado al documento que no se pudo conectar
        System.out.println(xmlLink.getUrl() + " ==> " + connection_.getStatus());
        }//end else if oConnect.connection_().equals("OK")

        return doc;
    }


    /**
     * Método que extrae de todos los documentos wikipedia las fuentes de sus códigos de su infobox
     *
     * @return lista "HashMap" sin elementos repetidos de las fuentes de códigos. Ej. eMedicine, Medline, Patient PK
     * @throws Exception
     */
    public HashMap<String, Resource> extractResource() throws Exception {

        System.out.println("Preparing resource model...");
        System.out.println("Reading data resource from Wikipedia...");

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

                int x = 1;
                for (XmlLink oXmlLink: xmlSource.getLinkList()) {
                    connection_ = connectDocument.connect(oXmlLink.getUrl());

                    //Verificación de la conexión del enlace >
                    if (connection_.getStatus().equals(StatusHttpEnum.OK.toString())) {

                        //Se obtiene el documento html "DOM"
                        oDoc = connection_.getoDoc();

                    /* Se obtiene el nombre de la enfermedad dentro del documento */
                        String idElementName = getHighlightXmlByDescription(Constants.XML_HL_DISEASENAME, xmlSource).getId();
                        String diseaseName = oDoc.getElementById( idElementName ).text();
//                    System.out.println("Disease: " + oDoc.getElementById( idElementName ).text() );

                    System.out.println(x + " extract codes " + diseaseName + " (" + oXmlLink.getUrl() + ") ==> " + connection_.getStatus());

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
                            Elements rowElements = infobox.select(Constants.HTML_TR);

                            //<editor-fold desc="RECORRIDO DE LAS FILAS DE LA TABLA INFOBOX">
                            for (Element row: rowElements) {

                        /* Se almecenan por cada fila <tr> el valor llave <th> y su valor <td> */
                                Elements thElements = row.select(Constants.HTML_TH);
                                Elements tdElements = row.select(Constants.HTML_TD);
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
                                String findHorizontalList = getHighlightXmlByDescription(Constants.XML_HL_HORIZONTAL_LIST + "", xmlSource).getClass_();
                                Elements hListElements = tdElements.select(Constants.QUERY_DIV_CLASS + findHorizontalList + Constants.RIGHT_PARENTHESIS);
                                // Se verifica (boolean) que en la fila se encuentre un class: hlist
                                boolean hasHorizontalList = ( hListElements.size()>0 );

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


                                //<editor-fold desc="PROCESO PARA EL INFOBOX EN EL PIE DEL DOCUMENTO">
                        /* Dentro de una fila <tr> se recorren los elementos <td> */
                                if(hasHorizontalList) {
                                    Resource resourceFather = new Resource();
                                    for (Element tdElement :
                                            tdElements) {
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

//                                                System.out.println("    Resource(HTML_B): " + b.text());
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
            Elements rowElements = infobox.select(Constants.HTML_TR);

            //<editor-fold desc="RECORRIDO DE LAS FILAS DE LA TABLA INFOBOX">
            for (Element row: rowElements) {

                        /* Se almecenan por cada fila <tr> el valor llave <th> y su valor <td> */
                Elements thElements = row.select(Constants.HTML_TH);
                Elements tdElements = row.select(Constants.HTML_TD);
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
                String findHorizontalList = getHighlightXmlByDescription(Constants.XML_HL_HORIZONTAL_LIST + "", xmlSource).getClass_();
                Elements hListElements = tdElements.select(Constants.QUERY_DIV_CLASS + findHorizontalList + Constants.RIGHT_PARENTHESIS);
                // Se verifica (boolean) que en la fila se encuentre un class: hlist
                boolean hasHorizontalList = ( hListElements.size()>0 );

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
                    Resource resourceFather = new Resource();
                    for (Element tdElement :
                            tdElements) {
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
                                countCode++;
//                                            System.out.println("       Code(HTML_B): " + code.text() + " | URL:" + code.attr(Constants.HTML_HREF).toString() + " R: " + resourceFather.getName());
                            }
                            //</editor-fold>

                        }
                    }
                }//</editor-fold>

            }//</editor-fold> //end for (Element row: rowElements)
            countResource++;
        }

        return codeList;

    }


    /**
     * Método que muestra un reporte de la extracción recien hecha
     *
     * @throws Exception
     */
    public void extractionReport() throws Exception {

        List<Integer> countCharacteresList = new ArrayList<>();

        long time_start, time_end;

        time_start = System.currentTimeMillis();
        List<Source> sourceList = extract();
        time_end = System.currentTimeMillis();


        System.out.println("-------------------- EXTRACTION REPORT --------------------");
        for (Source source :
                sourceList) {

            System.out.println("\n");
            System.out.println("-------------------- SOURCE(" + source.getId() + "_" + source.getName() + ") --------------------");

            for (Doc document:
                    source.getDocList()) {

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
                        }else{
                            for (String bullet:
                                    ( (List_) text).getBulletList() ) {
                                System.out.println("            -" + bullet);
                                aux = aux + bullet + "zzzz";
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
    public Paragraph getParagraphData(Element element, int countText, String title){
        Paragraph paragraph = new Paragraph();

        paragraph.setId( countText );
        paragraph.setText( element.text() );
        paragraph.setTextOrder( countText );
        paragraph.setTitle(title);

        // Agrega la lista de enlaces al párrafo
        paragraph.setUrlList( getTextUrls( element ) );

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
    public List_ getList_Data(Element element, int countText, String title){
        List_ list_ = new List_();
        List<String> liList;

        list_.setId( countText );
        list_.setTextOrder( countText );
        list_.setTitle(title);

        liList = new ArrayList<>();
        // Recorrido de la lista. Recorre los elementos <li> de un <ul>
        Elements li = element.select(Constants.HTML_LI); // select all li from ul
        for (int i = 0; i < li.size(); i++) {
            // Agrega las filas a la lista de elementos de una lista
            liList.add( li.get(i).text() );
        }
        // Agrega la lista al objeto lista "List_"
        list_.setBulletList( liList );

        // Agrega la lista de enlaces al objeto lista "List_"
        list_.setUrlList( getTextUrls( element ) );

        return list_;
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
        for (Element a :
                aElements) {
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
