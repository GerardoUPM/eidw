package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentSet;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentSetPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.DocumentSetRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DocumentSetRepositoryImpl
 * @see
 */
@Repository("DocumentSetRepositoryDao")
public class DocumentSetRepositoryImpl extends AbstractDao<DocumentSetPK, DocumentSet> implements DocumentSetRepository {
    @Override
    public DocumentSet findById(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = getByKey(documentSetPK);
        return documentSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentSet findByIdNative(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = null;
        List<DocumentSet> documentSetList = (List<DocumentSet>) getEntityManager()
                .createNamedQuery("DocumentSet.findByIdNativeResultClass")
                .setParameter("documentId", documentSetPK.getDocumentId())
                .setParameter("version", documentSetPK.getDate())
                .setParameter("paperId", documentSetPK.getPaperId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(documentSetList))
            documentSet = documentSetList.get(0);
        return documentSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DocumentSet findByIdNativeResultClass(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = null;
        List<DocumentSet> documentSetList = (List<DocumentSet>) getEntityManager()
                .createNamedQuery("DocumentSet.findByIdNativeResultClass")
                .setParameter("documentId", documentSetPK.getDocumentId())
                .setParameter("version", documentSetPK.getDate())
                .setParameter("paperId", documentSetPK.getPaperId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(documentSetList))
            documentSet = documentSetList.get(0);
        return documentSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DocumentSet> findAllQuery() {
        return (List<DocumentSet>) getEntityManager()
                .createNamedQuery("HasSection.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(DocumentSet documentSet) {
        super.persist(documentSet);
    }

    @Override
    public int insertNative(String documentId, Date version, String paperId) {
        return getEntityManager()
                .createNamedQuery("DocumentSet.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("version", version)
                .setParameter("paperId", paperId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(DocumentSetPK documentSetPK) {
        DocumentSet documentSet = findById( documentSetPK );
        if(documentSet ==null)
            return false;
        super.delete(documentSet);
        return true;
    }

    @Override
    public void delete(DocumentSet documentSet) {
        super.delete(documentSet);
    }

    @Override
    public DocumentSet update(DocumentSet documentSet) {
        return super.update(documentSet);
    }

    @Override
    public int updateByIdQuery(DocumentSet documentSet) {
        return 0;
    }
}
