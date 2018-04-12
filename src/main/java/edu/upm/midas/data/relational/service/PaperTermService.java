package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.PaperTerm;
import edu.upm.midas.data.relational.entities.edsssdb.PaperTermPK;

import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionService
 * @see
 */
public interface PaperTermService {

    PaperTerm findById(PaperTermPK paperTermPK);

    PaperTerm findByIdNative(PaperTermPK paperTermPK);

    List<PaperTerm> findAll();

    void save(PaperTerm paperTerm);

    int insertNative(String paperId, Integer termId);

    boolean updateFindFull(PaperTerm documentSet, PaperTermPK paperTermPK);

    boolean updateFindPartial(PaperTerm documentSet, PaperTermPK paperTermPK);

    boolean deleteById(PaperTermPK paperTermPK);
    
}
