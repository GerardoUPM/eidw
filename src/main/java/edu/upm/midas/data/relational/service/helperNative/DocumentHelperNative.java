package edu.upm.midas.data.relational.service.helperNative;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.relational.entities.edsssdb.Url;
import edu.upm.midas.data.relational.service.DocumentService;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DocumentHelper
 * @see
 */
@Service
public class DocumentHelperNative {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private UrlHelperNative urlHelperNative;
    @Autowired
    private DocumentHelperNative documentHelperNative;

    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(DocumentHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    public String insert(String sourceId, Doc document, Date version) throws JsonProcessingException {
        String documentId = uniqueId.generateDocument( sourceId, document.getId() );
        //Buscar si la enfermedad de la que habla este documento ya se encuentra insertada
        //Buscar por source, version y nombre de enfermedad. Si encuentra un documento, entonces no
        //insertar nuevo documento, ni nada relacionado con el (enfermedad, textos y códigos)
        //Cambio para la siguiente version 2018-01-15
        if ( documentService.insertNative( documentId, version ) > 0 ) {
            String docId = documentHelperNative.getDocumentId( documentId, version );
            Url url = urlHelperNative.findUrl(document.getUrl().getUrl());
            if (url!=null){
                documentService.insertNativeUrl( documentId, version, url.getUrlId() );
            }else {
                String urlId = urlHelperNative.getSimpleUrlId(document.getUrl(), document.getId());
                documentService.insertNativeUrl(documentId, version, urlId);
            }
            documentService.insertNativeHasSource( documentId, version, sourceId );
            return documentId;
        }else
            return "";
    }

    /**
     * @param documentId
     * @param version
     * @return
     */
    public String getDocumentId(String documentId, Date version){
        return documentId + ".V" + version;
    }

}
