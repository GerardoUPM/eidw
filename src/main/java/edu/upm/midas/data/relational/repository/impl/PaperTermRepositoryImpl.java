package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperTerm;
import edu.upm.midas.data.relational.entities.edsssdb.PaperTermPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.PaperTermRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperTermRepositoryImpl
 * @see
 */
@Repository("PaperTermRepositoryDao")
public class PaperTermRepositoryImpl extends AbstractDao<PaperTermPK, PaperTerm> implements PaperTermRepository {
    @Override
    public PaperTerm findById(PaperTermPK paperTermPK) {
        PaperTerm paperTerm = getByKey(paperTermPK);
        return paperTerm;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaperTerm findByIdNative(PaperTermPK paperTermPK) {
        PaperTerm paperTerm = null;
        List<PaperTerm> paperTermList = (List<PaperTerm>) getEntityManager()
                .createNamedQuery("PaperTerm.findByIdNativeResultClass")
                .setParameter("paperId", paperTermPK.getPaperId())
                .setParameter("termId", paperTermPK.getTermId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperTermList))
            paperTerm = paperTermList.get(0);
        return paperTerm;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaperTerm findByIdNativeResultClass(PaperTermPK paperTermPK) {
        PaperTerm paperTerm = null;
        List<PaperTerm> paperTermList = (List<PaperTerm>) getEntityManager()
                .createNamedQuery("PaperTerm.findByIdNativeResultClass")
                .setParameter("paperId", paperTermPK.getPaperId())
                .setParameter("termId", paperTermPK.getTermId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperTermList))
            paperTerm = paperTermList.get(0);
        return paperTerm;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaperTerm> findAllQuery() {
        return (List<PaperTerm>) getEntityManager()
                .createNamedQuery("PaperTerm.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(PaperTerm paperTerm) {
        super.persist(paperTerm);
    }

    @Override
    public int insertNative(String paperId, String termId) {
        return getEntityManager()
                .createNamedQuery("PaperTerm.insertNative")
                .setParameter("paperId", paperId)
                .setParameter("termId", termId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(PaperTermPK paperTermPK) {
        PaperTerm paperTerm = findById( paperTermPK );
        if(paperTerm ==null)
            return false;
        super.delete(paperTerm);
        return true;
    }

    @Override
    public void delete(PaperTerm paperTerm) {
        super.delete(paperTerm);
    }

    @Override
    public PaperTerm update(PaperTerm paperTerm) {
        return super.update(paperTerm);
    }

    @Override
    public int updateByIdQuery(PaperTerm paperTerm) {
        return 0;
    }
}
