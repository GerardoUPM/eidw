package edu.upm.midas.service;
import edu.upm.midas.data.extraction.xml.model.XmlLink;
import edu.upm.midas.data.relational.service.impl.PopulateDbNative;
import edu.upm.midas.utilsservice.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 29/01/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className ExtractService
 * @see
 */
@Service
public class ExtractService {

    @Autowired
    private PopulateDbNative populateDbNative;
    @Autowired
    private UtilDate utilDate;

    public boolean extract() throws Exception {
        boolean res = false;
        String inicio = utilDate.getTime();
        Date version = utilDate.getSqlDate();
        List<XmlLink> externalDiseaseLinkList = populateDbNative.getDiseaseLinkListFromDBPedia(version);

        if (externalDiseaseLinkList!=null) {
            populateDbNative.populateResource(externalDiseaseLinkList);
            populateDbNative.populateSemanticTypes();
            populateDbNative.populate(externalDiseaseLinkList, version);
            res = true;
        }else{
            System.out.println("ERROR disease album");
        }
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

        return res;
    }





    public boolean onlyExtract(List<XmlLink> externalDiseaseLinkList) throws Exception {
        boolean res = false;
        String inicio = utilDate.getTime();
        Date version = utilDate.getSqlDate();
        populateDbNative.onlyExtract(externalDiseaseLinkList);
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

        return res;



//        boolean res = false;
//        String inicio = utilDate.getTime();
//        Date version = utilDate.getSqlDate();
//        //List<XmlLink> externalDiseaseLinkList = populateDbNative.getDiseaseLinkListFromDBPedia(version);
//
//        //if (externalDiseaseLinkList!=null) {
//            populateDbNative.populateResource(null);
//            populateDbNative.populateSemanticTypes();
//            populateDbNative.populate(null, version);
//            res = true;
//        //}else{
//        //    System.out.println("ERROR disease album");
//        //}
//        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());
//
//        return res;
    }

    public void checkCodes() throws Exception {
        //extractionWikipedia.extract(null);
        //extractionWikipedia.extractResource(null);

        String inicio = utilDate.getTime();
        Date version = utilDate.getSqlDate();
        //List<XmlLink> externalDiseaseLinkList = populateDbNative.getDiseaseLinkListFromDBPedia(version);

        //if (externalDiseaseLinkList!=null) {
        populateDbNative.populateResource(null);
        populateDbNative.populateSemanticTypes();
        populateDbNative.populate(null, version);
        //}else{
        //    System.out.println("ERROR disease album");
        //}
        System.out.println("Inicio:" + inicio + " | Termino: " +utilDate.getTime());

    }

    public void checkLinks() throws Exception {
        populateDbNative.checkWikiPages();
    }
}
