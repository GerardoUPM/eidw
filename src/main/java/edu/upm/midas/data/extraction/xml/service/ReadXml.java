package edu.upm.midas.data.extraction.xml.service;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.xml.model.XmlHighlight;
import edu.upm.midas.data.extraction.xml.model.XmlLink;
import edu.upm.midas.data.extraction.xml.model.XmlSection;
import edu.upm.midas.data.extraction.xml.model.XmlSource;
import edu.upm.midas.data.extraction.xml.model.title.*;
import edu.upm.midas.utilsservice.Common;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by gerardo on 04/05/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project ExtractionInformationDiseasesWikipedia
 * @className ReadXml
 * @see
 */
@Component
public class ReadXml {

    private XmlSource oXml = new XmlSource();

    @Autowired
    private Common common;



    /**
     * @return
     * @throws Exception
     */
    public List<XmlSource> read() throws Exception{

        ArrayList<XmlSource> xmlList = new ArrayList<>();
        ArrayList<XmlSection> sectionList = new ArrayList<>();
        ArrayList<XmlHighlight> highlightList = new ArrayList<>();
        ArrayList<XmlLink> linkList = new ArrayList<>();

        SAXBuilder constructorXml = new SAXBuilder();
        File xmlFile = oXml.getXmlFile(); //new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/") + "/resources/bd/bd.xml");

        //Se crea el documento a traves del archivo
        Document docXml = (Document) constructorXml.build( xmlFile );
        //Se obtiene la raiz 'sources'
        Element rootSources = docXml.getRootElement();
        //Se obtiene los hijos 'source' de la raiz 'sources'
        List sourceList = rootSources.getChildren( Constants.XML_ROOT_TAG );
        //Se recorren los hijos de 'source'
        for ( int i = 0; i < sourceList.size(); i++ ){

            oXml = new XmlSource();

            //Se obtiene el elemento 'source'
            Element source = (Element) sourceList.get(i);

            //Se obtiene el atributo 'consult' e 'id' que esta en el tag 'source'
            oXml.setConsultSource( source.getAttributeValue( Constants.XML_ATT_CONSULT ).trim() );
            oXml.setId( Integer.parseInt( source.getAttributeValue( Constants.XML_ATT_ID ) ) );

            //Si se permite 'y' se consulta un recurso
            if( oXml.getConsultSource().equals( Constants.XML_ATT_CONSULT_YES ) ) {
                //Se obtiene el valor del tag 'name'
                oXml.setName(source.getChild( Constants.XML_TAG_NAME ).getTextTrim());
                oXml.setLink(source.getChild( Constants.XML_TAG_LINK ).getTextTrim() );

                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                Element sections = source.getChild( Constants.XML_TAG_SECTIONS );
                //Se permite 'y' consultar todas las secciones
                if( sections.getAttributeValue( Constants.XML_ATT_CONSULT ).trim().equals("y") ){
                    processXmlSection(sections, sectionList);
                }//end if( sections.getAttributeValue("consult").charAt(0) == 'y') Se permite 'y' consultar las secciones
                //Se almacena la lista de sections al objeto
                oXml.setSectionList(sectionList);
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                Element highlights = source.getChild( Constants.XML_TAG_HIGHLIGTS );
                //Se permite 'y' consultar todas las highlights
                if( highlights.getAttributeValue( Constants.XML_ATT_CONSULT ).trim().equals( Constants.XML_ATT_CONSULT_YES ) ){
                    processXmlHighlight(highlights, highlightList);
                }//end if( highlights.getAttributeValue("consult").charAt(0) == 'y') Se permite 'y' consultar las highlights
                //Se almacena la lista de highlights al objeto
                oXml.setHighlightList(highlightList);
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

                //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                Element links = source.getChild( Constants.XML_TAG_LINKS );
                //Se permite 'y' consultar todos las links
                if( links.getAttributeValue( Constants.XML_ATT_CONSULT ).trim().equals( Constants.XML_ATT_CONSULT_YES ) ){
                    processXmlLink(links, linkList);
                }//end if( links.getAttributeValue("consult").charAt(0) == 'y') Se permite 'y' consultar los links
                //Se almacena la lista de sections al objeto
                oXml.setLinkList(linkList);
                //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

            }//end if(getConsultSource() == 'y')

            xmlList.add(oXml);
        }

        return xmlList;
    }


    /**
     * @param xmlFile
     */
    public void file(File xmlFile){
        oXml.setXmlFile(xmlFile);
    }


    /**
     * @param elementsList
     */
    public void removeRepetedSections(List<XmlSection> elementsList){
        Set<XmlSection> linkedHashSet = new LinkedHashSet<XmlSection>();
        linkedHashSet.addAll(elementsList);
        elementsList.clear();
        elementsList.addAll(linkedHashSet);
    }


    /**
     * @param elementsList
     */
    public void removeRepetedLinks(List<XmlLink> elementsList){
        Set<XmlLink> linkedHashSet = new LinkedHashSet<XmlLink>();
        linkedHashSet.addAll(elementsList);
        elementsList.clear();
        elementsList.addAll(linkedHashSet);
    }


    /**
     * @param sType
     * @return
     */
    //Es una constante
    public Title getTypeTitle(String sType){

        Title typeTitle;

        switch (sType){
            case "h1": typeTitle = new H1('y', 1);
                break;
            case "h2": typeTitle = new H2('y', 2);
                break;
            case "h3": typeTitle = new H3('y', 3);
                break;
            case "h4": typeTitle = new H4('y', 4);
                break;
            case "h5": typeTitle = new H5('y', 5);
                break;
            default: typeTitle = new H2('y', 2);
                break;
        }

        return typeTitle;

    }


    /**
     * @param sections
     * @param sectionList
     */
    public void processXmlSection(Element sections, List<XmlSection> sectionList){
        //<<<<Se obtiene el hijo 'section' y después la lista de sus hijos>>>>
        List sectionsList = sections.getChildren();
        //Validación de lista de enlaces vacía
        if (sectionsList.size() > 0) {
            //Se recorren los hijos de 'section'
            for (int l = 0; l < sectionsList.size(); l++) {
                XmlSection oSec = new XmlSection();
                oSec.setTypeTitle( getTypeTitle( sections.getAttributeValue( Constants.XML_ATT_TYPE ).trim() ) );
                oSec.setClass_( sections.getAttributeValue( Constants.XML_ATT_CLASS ).trim() );
                //Se obtiene el prelemento 'section'
                Element section = (Element) sectionsList.get(l);

                if( section.getAttributeValue( Constants.XML_ATT_CONSULT ).trim().equals( Constants.XML_ATT_CONSULT_YES ) ) {
                    //Se obtiene el valor de los atributos de cada tag 'section'
                    oSec.setConsult( section.getAttributeValue( Constants.XML_ATT_CONSULT ).trim() );
                    oSec.setId( section.getAttributeValue( Constants.XML_ATT_ID ).trim() );
                    oSec.setType( section.getAttributeValue( Constants.XML_ATT_TYPE ).trim() );
                    oSec.setName( section.getAttributeValue( Constants.XML_ATT_NAME ).trim() );
                }

                sectionList.add(oSec);

            }//end for (int l = 0; l < sectionsList.size(); l++)
            //Se eliminan las 'section' repetidas
            removeRepetedSections(sectionList);

        }//end if (sectionsList.size() > 0)

    }//end method public void processXmlSection(Element sections, List_<XmlSection> sectionList)


    /**
     * @param highlights
     * @param highlightList
     */
    public void processXmlHighlight(Element highlights, List<XmlHighlight> highlightList){

        //<<<<Se obtiene el hijo 'highlight' y después la lista de sus hijos>>>>
        List highlightListsList = highlights.getChildren();
        //Validación de lista de enlaces vacía
        if (highlightListsList.size() > 0) {

            //Se recorren los hijos de 'highlights'
            for (int l = 0; l < highlightListsList.size(); l++) {
                XmlHighlight oHighlight = new XmlHighlight();
                //Se obtiene el prelemento 'highlight'
                Element highlight = (Element) highlightListsList.get(l);

                if( highlight.getAttributeValue( Constants.XML_ATT_CONSULT ).charAt(0) == 'y' ) {
                    //Se obtiene el valor de los atributos de cada tag 'highlight'
                    oHighlight.setConsult( highlight.getAttributeValue( Constants.XML_ATT_CONSULT ).trim() );
                    oHighlight.setId( highlight.getAttributeValue( Constants.XML_ATT_ID ).trim() );
                    oHighlight.setClass_( highlight.getAttributeValue( Constants.XML_ATT_CLASS ).trim() );
                    oHighlight.setType( highlight.getAttributeValue( Constants.XML_ATT_TYPE ).trim() );
                    oHighlight.setDescription( highlight.getAttributeValue( Constants.XML_ATT_DESC ).trim() );

                    highlightList.add(oHighlight);
                }
            }//end for (int l = 0; l < highlightListsList.size(); l++)

        }//end if (highlightListsList.size() > 0)

    }//end method public void processXmlHighlight(Element highlights, List_<XmlHighlight> highlightList){


    /**
     * @param links
     * @param linkList
     */
    public void processXmlLink(Element links, List<XmlLink> linkList){

        //<<<<Se obtiene el hijo 'link' y después la lista de sus hijos>>>>
        List linksList = links.getChildren();
        //Validación de lista de enlaces vacía
        if (linksList.size() > 0) {

            //Se recorren los hijos de 'section'
            for (int l = 0; l < linksList.size(); l++) {
                XmlLink oLink = new XmlLink();
                //Se obtiene el prelemento 'section'
                Element link = (Element) linksList.get(l);

                if( link.getAttributeValue( Constants.XML_ATT_CONSULT ).trim().equals( Constants.XML_ATT_CONSULT_YES ) ) {
                    //Se obtiene el valor de los atributos de cada tag 'section'
                    oLink.setConsult( link.getAttributeValue( Constants.XML_ATT_CONSULT ).trim() );
                    oLink.setId( Integer.parseInt( link.getAttributeValue( Constants.XML_ATT_ID ).trim() ) );
                    oLink.setUrl( common.replaceUnicodeToSpecialCharacters( link.getTextTrim() ) );

                    linkList.add(oLink);
                }
            }//end for (int l = 0; l < linksList.size(); l++)

            //Se eliminan las 'link' repetidas
            removeRepetedLinks(linkList);

        }//end if (linksList.size() > 0)

    }


}
