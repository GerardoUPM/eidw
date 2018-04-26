package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Document;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentPK;
import edu.upm.midas.data.relational.repository.DocumentRepository;
import edu.upm.midas.data.relational.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DocumentServiceImpl
 * @see
 */
@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository daoDocument;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Document findById(DocumentPK documentPk) {
        Document document = daoDocument.findById( documentPk );
        /*if(document!=null)
            Hibernate.initialize(document.getCodeList());*/
        return document;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Date findLastVersionNative() {
        return daoDocument.findLastVersionNative();
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Document> findAll() {
        List<Document> listDocumentEntities = daoDocument.findAllQuery();
        return listDocumentEntities;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Document document) {
        daoDocument.persist(document);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String documentId, Date date) {
        return daoDocument.insertNative( documentId, date );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNativeUrl(String documentId, Date date, String urlId) {
        return daoDocument.insertNativeUrl( documentId, date, urlId );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNativeHasSource(String documentId, Date date, String sourceId) {
        return daoDocument.insertNativeHasSource( documentId, date, sourceId );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Document document, DocumentPK documentPk) {
        Document doc = daoDocument.findById( documentPk );
        if(doc!=null){
            //==>doc.setDocumentPK(document.getDocumentPK());
            //sour.getDiseasesBySidsource().clear();
            //sour.getDiseasesBySidsource().addAll(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource())?source.getDiseasesBySidsource():new ArrayList<Disease>());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Document document, DocumentPK documentPk) {
        Document doc = daoDocument.findById( documentPk );
        if(doc!=null){
            //==>if(StringUtils.isNotBlank( doc.getDocumentPK().toString() ) )
            //==>    doc.setDocumentPK(document.getDocumentPK());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(DocumentPK documentPk) {
        Document document = daoDocument.findById(documentPk);
        if(document !=null)
            daoDocument.delete(document);
        else
            return false;
        return true;
    }
}
