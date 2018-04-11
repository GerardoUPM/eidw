package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Term;
import edu.upm.midas.data.relational.repository.TermRepository;
import edu.upm.midas.data.relational.service.TermService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TermServiceImpl
 * @see
 */
@Service("termService")
public class TermServiceImpl implements TermService {

    @Autowired
    private TermRepository daoTerm;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Term findById(Integer termId) {
        Term term = daoTerm.findById(termId);
        return term;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Term findByIdNative(Integer termId) {
        Term term = daoTerm.findByIdNative(termId);
        return term;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Term findByNameQuery(String name) {
        Term term = daoTerm.findByNameQuery(name);
        return term;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public int findIdByNameQuery(String name) {
        return daoTerm.findIdByNameQuery( name );
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Term> findAll() {
        List<Term> daoTermAllQuery = daoTerm.findAllQuery();
        return daoTermAllQuery;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Term term) {
        daoTerm.persist(term);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(Integer resourceId, String name) {
        return daoTerm.insertNative( resourceId, name );
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Term term) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Term term) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(Integer termId) {
        return false;
    }
}
