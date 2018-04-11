package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentSet;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentSetPK;
import edu.upm.midas.data.relational.repository.DocumentSetRepository;
import edu.upm.midas.data.relational.service.DocumentSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DocumentSetServiceImpl
 * @see
 */
@Service("documentSetService")
public class DocumentSetServiceImpl implements DocumentSetService {

    @Autowired
    private DocumentSetRepository daoDocumentSet;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public DocumentSet findById(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = daoDocumentSet.findById(documentSetPK);
        return documentSet;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public DocumentSet findByIdNative(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = daoDocumentSet.findByIdNative(documentSetPK);
        return documentSet;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<DocumentSet> findAll() {
        List<DocumentSet> documentSets = daoDocumentSet.findAllQuery();
        return documentSets;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(DocumentSet documentSet) {
        daoDocumentSet.persist(documentSet);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String documentId, Date version, String paperId) {
        return daoDocumentSet.insertNative(documentId, version, paperId);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(DocumentSet documentSet, DocumentSetPK diseaseSynonymPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(DocumentSet documentSet, DocumentSetPK diseaseSynonymPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(DocumentSetPK diseaseSynonymPK) {
        return false;
    }
}
