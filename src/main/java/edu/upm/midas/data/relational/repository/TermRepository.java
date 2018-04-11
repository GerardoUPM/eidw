package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.Term;

import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className TermRepository
 * @see
 */
public interface TermRepository {

    Term findById(Integer termId);

    Term findByIdQuery(Integer termId);
    
    Term findByIdNative(Integer termId);

    Term findByIdNativeResultClass(Integer termId);

    Term findByNameQuery(String termName);

    int findIdByNameQuery(String termName);
    
    List<Term> findAllQuery();

    void persist(Term term);

    int insertNative(Integer resourceId, String name);

    boolean deleteById(Integer termId);

    void delete(Term term);

    Term update(Term term);

    int updateByIdQuery(Term term);
    
}
