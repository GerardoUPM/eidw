package edu.upm.midas.data.relational.service.helperNative;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.Doc;
import edu.upm.midas.data.extraction.model.PubMedDoc;
import edu.upm.midas.data.extraction.model.Term;
import edu.upm.midas.data.relational.entities.edsssdb.*;
import edu.upm.midas.data.relational.service.*;
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
    private DocumentSetService documentSetService;
    @Autowired
    private PaperService paperService;
    @Autowired
    private PaperUrlService paperUrlService;
    @Autowired
    private PaperTermService paperTermService;
    @Autowired
    private TermService termService;

    @Autowired
    private UrlHelperNative urlHelperNative;
    @Autowired
    private DocumentHelperNative documentHelperNative;
    @Autowired
    private ResourceHelperNative resourceHelperNative;

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


    public String insertPubMedArticles(String sourceId, Doc document, Date version) throws JsonProcessingException {
        String documentId = uniqueId.generateDocument( sourceId, document.getId() );
        if ( documentService.insertNative( documentId, version ) > 0 ) {
            //Se genera un identificador del documento para todas las entidades relacionadas con los documentos
            String docId = documentHelperNative.getDocumentId( documentId, version );
            //Insertar papers "document_set"
            insertPapers(document, documentId, version);
            //SI tiene url la inserta
            if (document.getUrl()!=null) {
                Url url = urlHelperNative.findUrl(document.getUrl().getUrl());
                if (url != null) {
                    documentService.insertNativeUrl(documentId, version, url.getUrlId());
                } else {
                    String urlId = urlHelperNative.getSimpleUrlId(document.getUrl(), document.getId());
                    documentService.insertNativeUrl(documentId, version, urlId);
                }
            }
            //inserta la relación entre el documento y la fuente
            documentService.insertNativeHasSource( documentId, version, sourceId );
            return documentId;
        }else
            return "";
    }


    private void insertPapers(Doc document, String documentId, Date version) throws JsonProcessingException {
        //Recorrer lista de papers, si existen
        if (document.getPaperList()!=null){
            for (PubMedDoc paper: document.getPaperList()) {
                String paperId = paper.getPmID();
                Paper existPaper = paperService.findById(paperId);
                if (existPaper==null) {
                    String doi = (common.isEmpty(paper.getDoi()))?"":paper.getDoi();
                    String altId = (common.isEmpty(paper.getPmcID()))?"":paper.getPmcID();
                    String title = (common.isEmpty(paper.getTitleText()))?"":paper.getTitleText();
                    String authors = (common.isEmpty(paper.getAuthor()))?"":paper.getAuthor();
                    String keywords = (common.isEmpty(paper.getKeyWords()))?"":paper.getKeyWords();
                    //Inserta el paper
                    if (paperService.insertNative(paperId, doi, altId, title, authors, keywords, paper.isHasFreeText()) > 0) {
                        //Insertar url si existe
                        insertUrl(paper, paperId);
                        //Inserta los terminos asociados al paper y los relaciona
                        insertTerms(paper, paperId);
                        //Inserta la relación entre el paper y el documento DISNET "document_set"
                        insertDocumentSet(documentId, version, paperId);
                    }
                }
            }
        }
    }


    /**
     * @param documentId
     * @param version
     * @param paperId
     */
    private void insertDocumentSet(String documentId, Date version, String paperId){
        //Busca si ya se encuentra la relación insertada
        DocumentSet existDocumentSet = documentSetService.findById(new DocumentSetPK(documentId, (java.sql.Date) version, paperId));
        if (existDocumentSet==null){
            documentSetService.insertNative(documentId, version, paperId);
        }
    }


    /**
     * @param paper
     * @param paperId
     */
    private void insertTerms(PubMedDoc paper, String paperId){
        //Si existen los insertaremos
        if (paper.getTerms()!=null){
            //Recorrer los terminos si existen
            for (Term term: paper.getTerms()) {
                //Primero verificar si existe el "resource", si no existe, lo inserta y regresa el id (int)
                int resourceId = resourceHelperNative.insertIfExist(term.getResource().getName().trim());
                //Busca el termino
                edu.upm.midas.data.relational.entities.edsssdb.Term existTerm = termService.findByNameQuery(term.getName().trim());
                if (existTerm!=null){
                    //Buscar e insertar relacion paper con term
                    PaperTerm existPaperTerm = paperTermService.findById(new PaperTermPK(paperId, existTerm.getTermId()));
                    //Si no lo encuentra inserta la relación
                    if (existPaperTerm==null){
                        paperTermService.insertNative(paperId, existTerm.getTermId());
                    }
                }else {
                    //Insertar al no existir
                    if (termService.insertNative(resourceId, term.getName().trim()) > 0) {
                        //Después de insertado lo consulto por el nombre para obtener el id
                        edu.upm.midas.data.relational.entities.edsssdb.Term foundTerm = termService.findByNameQuery(term.getName().trim());
                        //Buscar e insertar relacion paper con term
                        PaperTerm existPaperTerm = paperTermService.findById(new PaperTermPK(paperId, foundTerm.getTermId()));
                        //Si no lo encuentra inserta la relación
                        if (existPaperTerm==null){
                            paperTermService.insertNative(paperId, foundTerm.getTermId());
                        }
                    }
                }
            }
        }
    }


    /**
     * @param paper
     * @param paperId
     * @throws JsonProcessingException
     */
    private void insertUrl(PubMedDoc paper, String paperId) throws JsonProcessingException {
        //Insertar url si existe
        if (paper.getLink() != null) {
            Url existUrl = urlHelperNative.findUrl(paper.getLink().getUrl());
            if (existUrl != null) {//Si existe la url
                //Busca que no exista la relacion entre url y paper
                PaperUrl existPaperUrl = paperUrlService.findById(new PaperUrlPK(paperId, existUrl.getUrlId()));
                //Inserta la relacion entre url y paper si no existe
                if (existPaperUrl==null) {
                    paperUrlService.insertNative(paperId, existUrl.getUrlId());
                }
            } else {//Si no existe la url, se crea
                //Busca e inserta la url y regresa id de url nueva  o no
                String urlId = urlHelperNative.getUrl(paper.getLink(), paperId);
                //Busca que no exista la relacion entre url y paper
                PaperUrl existPaperUrl = paperUrlService.findById(new PaperUrlPK(paperId, urlId));
                //Inserta la relacion entre url y paper si no existe
                if (existPaperUrl==null) {
                    paperUrlService.insertNative(paperId, urlId);
                }
            }
        }
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
