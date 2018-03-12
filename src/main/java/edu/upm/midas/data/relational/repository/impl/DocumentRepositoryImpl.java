package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Document;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.DocumentRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DocumentRepositoryImpl
 * @see
 */
@Repository("DocumentRepositoryDao")
public class DocumentRepositoryImpl extends AbstractDao<DocumentPK, Document>
                                    implements DocumentRepository {


    public Document findById(DocumentPK documentPK) {
        Document document = getByKey(documentPK);
        return document;
    }

    @SuppressWarnings("unchecked")
    public Document findByIdQuery(DocumentPK documentPK) {
        Document document = null;
        List<Document> documentList = (List<Document>) getEntityManager()
                .createNamedQuery("Document.findById")
                .setParameter("documentId", documentPK.getDocumentId())
                .setParameter("date", documentPK.getDate())
                .getResultList();
        if (CollectionUtils.isNotEmpty(documentList))
            document = documentList.get(0);
        return document;
    }

    @SuppressWarnings("unchecked")
    public Document findByIdNative(DocumentPK documentPK) {
        Document document = null;
        List<Document> documentList = (List<Document>) getEntityManager()
                .createNamedQuery("Document.findByIdNative")
                .setParameter("documentId", documentPK.getDocumentId())
                .setParameter("date", documentPK.getDate())
                .getResultList();
        if (CollectionUtils.isNotEmpty(documentList))
            document = documentList.get(0);
        return document;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Document findByIdNativeResultClass(DocumentPK documentPK) {
        Document document = null;
        List<Document> documentList = (List<Document>) getEntityManager()
                .createNamedQuery("Document.findByIdNativeResultClass")
                .setParameter("documentId", documentPK.getDocumentId())
                .setParameter("date", documentPK.getDate())
                .getResultList();
        if (CollectionUtils.isNotEmpty(documentList))
            document = documentList.get(0);
        return document;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Document> findAllQuery() {
        return (List<Document>) getEntityManager()
                .createNamedQuery("Document.findAll")
//                .setMaxResults(10)
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(Document document) {
        super.persist(document);
    }

    @Override
    public int insertNative(String documentId, Date date) {
        return getEntityManager()
                .createNamedQuery("Document.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .executeUpdate();
    }

    @Override
    public int insertNativeUrl(String documentId, Date date, String urlId) {
        return getEntityManager()
                .createNamedQuery("DocumentUrl.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("urlId", urlId)
                .executeUpdate();
    }

    @Override
    public int insertNativeHasSource(String documentId, Date date, String sourceId) {
        return getEntityManager()
                .createNamedQuery("HasSource.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("sourceId", sourceId)
                .executeUpdate();
    }

    public boolean deleteById(DocumentPK documentPK) {
        Document document = findById( documentPK );
        if(document ==null)
            return false;
        super.delete(document);
        return true;
    }

    public void delete(Document document) {
        super.delete(document);
    }

    public Document update(Document document) {
        return super.update(document);
    }

    @Override
    public int updateByIdQuery(Document document) {
        return 0;
    }
}
