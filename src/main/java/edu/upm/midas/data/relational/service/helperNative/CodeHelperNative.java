package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.code.Code;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCode;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCodePK;
import edu.upm.midas.data.relational.service.CodeService;
import edu.upm.midas.data.relational.service.ResourceService;
import edu.upm.midas.data.relational.service.SynonymCodeService;
import edu.upm.midas.data.relational.service.SynonymService;
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
    private SynonymService synonymService;
    @Autowired
    private SynonymCodeService synonymCodeService;
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
        Object[] codeEntity;

        for (Code code: codeList) {
            codeEntity = getCodeByCodeResource( code );

            if ( codeEntity == null ){//validar el resourceId por si no existe
                int resourceId = resourceService.findIdByNameQuery( code.getResource().getName() );
                if (resourceId == 0){
                    //Insertar resource
                    resourceService.insertNative(code.getResource().getName());
                    resourceId = resourceService.findIdByNameQuery( code.getResource().getName() );
                }
                codeService.insertNative( code.getCode(), resourceId );
                codeService.insertNativeHasCode( documentId, version, code.getCode(), resourceId );
                if (code.getLink()!=null) {
                    String urlId = urlHelperNative.getUrl(code.getLink(), getId(code.getCode(), resourceId));
                    codeService.insertNativeUrl(code.getCode(), resourceId, urlId);
                }
            }else{
                //System.out.println("Document_id: " + documentId + " Version: " + version + " Code: " + codeEntity[0] + " ResourceName: " + codeEntity[1]);
                codeService.insertNativeHasCode( documentId, version, (String) codeEntity[0], (int) codeEntity[1] );
            }
        }
    }


    public void insertIfExistSynonymCode(List<Code> codeList, int synonymId) throws JsonProcessingException {
        Object[] codeEntity;

        for (Code code: codeList) {
            if (code.getResource()!=null) {
                codeEntity = getCodeByCodeResource(code);

                if (codeEntity == null) {//validar el resourceId por si no existe
                    int resourceId = resourceService.findIdByNameQuery(code.getResource().getName());
                    if (resourceId == 0) {
                        //Insertar resource
                        resourceService.insertNative(code.getResource().getName());
                        resourceId = resourceService.findIdByNameQuery(code.getResource().getName());
                    }
                    if (codeService.insertNative(code.getCode(), resourceId) > 0) {
                        SynonymCode existSynonymCode = synonymCodeService.findById(new SynonymCodePK(synonymId, code.getCode(), resourceId));
                        if (existSynonymCode == null) {
                            synonymCodeService.insertNative(synonymId, code.getCode(), resourceId);
                        }
                    }
                    if (code.getLink() != null) {
                        String urlId = urlHelperNative.getUrl(code.getLink(), getId(code.getCode(), resourceId));
                        codeService.insertNativeUrl(code.getCode(), resourceId, urlId);
                    }
                } else {
                    //System.out.println("Document_id: " + documentId + " Version: " + version + " Code: " + codeEntity[0] + " ResourceName: " + codeEntity[1]);
                    //buscar si existe la relacion entre sinonimo y codigo
                    SynonymCode existSynonymCode = synonymCodeService.findById(new SynonymCodePK(synonymId, (String) codeEntity[0], (int) codeEntity[1]));
                    if (existSynonymCode == null) {
                        synonymCodeService.insertNative(synonymId, (String) codeEntity[0], (int) codeEntity[1]);
                    }
                }
            }
        }
    }


    /**
     * @param cod
     * @return
     */
    public boolean exist(Code cod){
        Object[] code = getCodeByCodeResource( cod );
        if( code != null )
            return true;
        else
            return false;
    }


    /**
     * @param code
     * @return
     */
    public Object[] getCodeByCodeResource(Code code){
//        System.out.println("RESOURCE NAME A BUSCAR: " + code.getResource().getName());
        int resourceId = resourceService.findIdByNameQuery( code.getResource().getName() );
        return codeService.findByIdNative(code.getCode(), resourceId);
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
