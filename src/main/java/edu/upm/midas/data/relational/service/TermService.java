package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.Term;

import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className TermService
 * @see
 */
public interface TermService {

    Term findById(Integer termId);

    Term findByIdNative(Integer termId);

    Term findByNameQuery(String name);

    int findIdByNameQuery(String name);
    
    List<Term> findAll();

    void save(Term term);

    int insertNative(Integer resourceId, String name);

    boolean updateFindFull(Term term);

    boolean updateFindPartial(Term term);

    boolean deleteById(Integer termId);

}
