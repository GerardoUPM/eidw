package edu.upm.midas.data.relational.repository;


import edu.upm.midas.data.relational.entities.edsssdb.PaperUrl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperUrlPK;

import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className PaperUrlRepository
 * @see
 */
public interface PaperUrlRepository {

    PaperUrl findById(PaperUrlPK paperUrlPK);

    PaperUrl findByIdNative(PaperUrlPK paperUrlPK);

    PaperUrl findByIdNativeResultClass(PaperUrlPK paperUrlPK);

    List<PaperUrl> findAllQuery();

    void persist(PaperUrl paperUrl);

    int insertNative(String paperId, String urlId);

    boolean deleteById(PaperUrlPK paperUrlPK);

    void delete(PaperUrl paperUrl);

    PaperUrl update(PaperUrl paperUrl);

    int updateByIdQuery(PaperUrl paperUrl);
    
}
