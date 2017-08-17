package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.SemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.Symptom;
import edu.upm.midas.data.relational.service.SemanticTypeService;
import edu.upm.midas.utilsservice.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo on 20/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SemanticTypeHelper
 * @see
 */
@Service
public class SemanticTypeHelperNative {

    @Autowired
    private SemanticTypeService semanticTypeService;

    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(SemanticTypeHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;



    /**
     * @param semType
     * @param description
     * @return
     * @throws Exception
     */
    public void insertIfExist(String semType, String description) throws Exception{
        SemanticType semanticType = semanticTypeService.findById( semType );;
        if ( semanticType == null ){
            semanticTypeService.insertNative( semType, description );
        }else{
            //semanticTypeService.updateFindPartial( semanticType );
        }
    }

    public void insertHasSemanticType(String cui, String semanticType){
        semanticTypeService.insertNativeHasSemanticType(cui, semanticType);
    }


    /**
     * @param semType
     * @return
     */
    public boolean exist(String semType){
        SemanticType semanticType = semanticTypeService.findById( semType );
        if( semanticType != null )
            return true;
        else
            return false;
    }



}
