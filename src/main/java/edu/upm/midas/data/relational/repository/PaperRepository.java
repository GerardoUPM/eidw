package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.Paper;

import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className PaperRepository
 * @see
 */
public interface PaperRepository {

    Paper findById(String paperId);

    Paper findByIdQuery(String paperId);
    
    Paper findByIdNative(String paperId);

    Paper findByIdNativeResultClass(String paperId);
    
    List<Paper> findAllQuery();

    void persist(Paper paper);

    int insertNative(String paperId, String doi, String alternativeId, String title, String authors, String keywords, boolean isFreeText);

    int insertNativeUrl(String paperId, String urlId);

    boolean deleteById(String paperId);

    void delete(Paper paper);

    Paper update(Paper paper);

    int updateByIdQuery(Paper paper);
    
}
