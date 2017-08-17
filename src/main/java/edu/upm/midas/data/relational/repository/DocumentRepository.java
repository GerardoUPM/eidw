package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.Document;
import edu.upm.midas.data.relational.entities.edsssdb.DocumentPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DocumentRepository
 * @see
 */
public interface DocumentRepository {

    Document findById(DocumentPK documentPK);

    Document findByIdQuery(DocumentPK documentPK);
    
    Document findByIdNative(DocumentPK documentPK);

    Document findByIdNativeResultClass(DocumentPK documentPK);

    List<Document> findAllQuery();

    void persist(Document document);

    int insertNative(String documentId, Date date);

    int insertNativeUrl(String documentId, Date date, String urlId);

    int insertNativeHasSource(String documentId, Date date, String sourceId);

    boolean deleteById(DocumentPK documentPK);

    void delete(Document document);

    Document update(Document document);

    int updateByIdQuery(Document document);
    
}
