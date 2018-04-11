package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperUrl;
import edu.upm.midas.data.relational.entities.edsssdb.PaperUrlPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.PaperUrlRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className PaperUrlRepositoryImpl
 * @see
 */
@Repository("PaperUrlRepositoryDao")
public class PaperUrlRepositoryImpl extends AbstractDao<PaperUrlPK, PaperUrl> implements PaperUrlRepository {
    @Override
    public PaperUrl findById(PaperUrlPK paperUrlPK) {
        PaperUrl hasSection = getByKey(paperUrlPK);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaperUrl findByIdNative(PaperUrlPK paperUrlPK) {
        PaperUrl paperUrl = null;
        List<PaperUrl> paperUrlList = (List<PaperUrl>) getEntityManager()
                .createNamedQuery("PaperUrl.findByIdNativeResultClass")
                .setParameter("paperId", paperUrlPK.getPaperId())
                .setParameter("urlId", paperUrlPK.getUrlId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperUrlList))
            paperUrl = paperUrlList.get(0);
        return paperUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PaperUrl findByIdNativeResultClass(PaperUrlPK paperUrlPK) {
        PaperUrl paperUrl = null;
        List<PaperUrl> paperUrlList = (List<PaperUrl>) getEntityManager()
                .createNamedQuery("PaperUrl.findByIdNativeResultClass")
                .setParameter("paperId", paperUrlPK.getPaperId())
                .setParameter("urlId", paperUrlPK.getUrlId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(paperUrlList))
            paperUrl = paperUrlList.get(0);
        return paperUrl;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<PaperUrl> findAllQuery() {
        return (List<PaperUrl>) getEntityManager()
                .createNamedQuery("PaperUrl.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(PaperUrl paperUrl) {
        super.persist(paperUrl);
    }

    @Override
    public int insertNative(String paperId, String urlId) {
        return getEntityManager()
                .createNamedQuery("PaperUrl.insertNative")
                .setParameter("paperId", paperId)
                .setParameter("urlId", urlId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(PaperUrlPK paperUrlPK) {
        PaperUrl paperUrl = findById( paperUrlPK );
        if(paperUrl ==null)
            return false;
        super.delete(paperUrl);
        return true;
    }

    @Override
    public void delete(PaperUrl paperUrl) {
        super.delete(paperUrl);
    }

    @Override
    public PaperUrl update(PaperUrl paperUrl) {
        return super.update(paperUrl);
    }

    @Override
    public int updateByIdQuery(PaperUrl paperUrl) {
        return 0;
    }
}
