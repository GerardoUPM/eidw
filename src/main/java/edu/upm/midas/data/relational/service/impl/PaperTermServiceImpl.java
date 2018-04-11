package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperTerm;
import edu.upm.midas.data.relational.entities.edsssdb.PaperTermPK;
import edu.upm.midas.data.relational.repository.PaperTermRepository;
import edu.upm.midas.data.relational.service.PaperTermService;
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
 * @className PaperTermServiceImpl
 * @see
 */
@Service("paperTermService")
public class PaperTermServiceImpl implements PaperTermService {

    @Autowired
    private PaperTermRepository daoPaperTerm;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PaperTerm findById(PaperTermPK paperTermPK) {
        PaperTerm paperTerm = daoPaperTerm.findById(paperTermPK);
        return paperTerm;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public PaperTerm findByIdNative(PaperTermPK paperTermPK) {
        return daoPaperTerm.findByIdNative(paperTermPK);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<PaperTerm> findAll() {
        List<PaperTerm> daoPaperTermAllQuery = daoPaperTerm.findAllQuery();
        return daoPaperTermAllQuery;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(PaperTerm paperTerm) {
daoPaperTerm.persist(paperTerm);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String paperId, String termId) {
        return daoPaperTerm.insertNative(paperId, termId);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(PaperTerm documentSet, PaperTermPK paperTermPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(PaperTerm documentSet, PaperTermPK paperTermPK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(PaperTermPK paperTermPK) {
        return false;
    }
}
