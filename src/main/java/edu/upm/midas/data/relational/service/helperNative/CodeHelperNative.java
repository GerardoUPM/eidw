package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.code.Code;
import edu.upm.midas.data.relational.entities.edsssdb.CodePK;
import edu.upm.midas.data.relational.entities.edsssdb.Resource;
import edu.upm.midas.data.relational.service.CodeService;
import edu.upm.midas.data.relational.service.ResourceService;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className CodeHelper
 * @see
 */
@Service
public class CodeHelperNative {

    @Autowired
    private CodeService codeService;
    @Autowired
    private DocumentHelperNative documentHelperNative;
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private UrlHelperNative urlHelperNative;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(CodeHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    /**
     * @param codeList
     * @param documentId
     * @param version
     * @throws JsonProcessingException
     */
    public void insertIfExist(List<Code> codeList, String documentId, Date version) throws JsonProcessingException {
        edu.upm.midas.data.relational.entities.edsssdb.Code codeEntity;

        for (Code code: codeList) {
            codeEntity = getCodeByCodeResource( code );
            int resourceId = resourceService.findIdByNameQuery( code.getResource().getName() );
            
            if ( codeEntity == null ){
                codeService.insertNative( code.getCode(), resourceId );
                codeService.insertNativeHasCode( documentId, version, code.getCode(), resourceId );
                String urlId = urlHelperNative.getUrl( code.getLink(), getId( code.getCode(), resourceId ) );
                codeService.insertNativeUrl( code.getCode(), resourceId, urlId );
            }else{
                codeService.insertNativeHasCode( documentId, version, code.getCode(), resourceId );
            }
        }
    }


    /**
     * @param cod
     * @return
     */
    public boolean exist(Code cod){
        edu.upm.midas.data.relational.entities.edsssdb.Code code = getCodeByCodeResource( cod );
        if( code != null )
            return true;
        else
            return false;
    }


    /**
     * @param code
     * @return
     */
    public edu.upm.midas.data.relational.entities.edsssdb.Code getCodeByCodeResource(Code code){
//        System.out.println("RESOURCE NAME A BUSCAR: " + code.getResource().getName());
        Resource resource = resourceService.findByName( code.getResource().getName() );
//        System.out.println("RESOURCE NAME: " + code.getResource().getName() + " id: " + resource.getResourceId());
        CodePK codePK = new CodePK();
        codePK.setCode( code.getCode() );
        codePK.setResourceId( resource.getResourceId() );

        return codeService.findById( codePK );
    }


    /**
     * @param code       
     * @param resourceId
     * @return
     */
    public String getId(String code, int resourceId) {
        return uniqueId.generateCode( code, resourceId );
    }


}
