package edu.upm.midas.data.relational.service.helperNative;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.relational.entities.edsssdb.Disease;
import edu.upm.midas.data.relational.service.DiseaseService;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DiseaseHelper
 * @see
 */
@Service
public class DiseaseHelperNative {

    @Autowired
    private DiseaseService diseaseService;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private DocumentHelperNative documentHelperNative;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(DiseaseHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    /**
     * @param document
     * @param documentId
     * @param version
     * @return
     * @throws JsonProcessingException
     */
    public String insertIfExist(Doc document, String documentId, Date version) throws JsonProcessingException {
        String diseaseId = uniqueId.generateDisease( document.getDisease().getId() );
        String diseaseName = document.getDisease().getName();
        String url = document.getUrl().getUrl();

        Disease diseaseEntity = diseaseService.findById( diseaseId );
        if ( diseaseEntity == null ){
            diseaseService.insertNative( diseaseId, diseaseName, "" );
            diseaseService.insertNativeHasDisease( documentId, version, diseaseId );
        }else{
            System.out.println("HasDisease: "+ documentId + " | " + version + " | " + url);
            diseaseService.insertNativeHasDisease( documentId, version, url );
        }
        return diseaseId;
    }


    /**
     * @param diseaseName
     * @return
     */
    public boolean exist(String diseaseName){
        Disease disease = diseaseService.findByName( diseaseName );
        if( disease != null )
            return true;
        else
            return false;
    }



}
