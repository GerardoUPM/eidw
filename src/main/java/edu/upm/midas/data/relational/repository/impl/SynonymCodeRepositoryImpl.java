package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCode;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCodePK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.SynonymCodeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className SynonymCodeRepositoryImpl
 * @see
 */
@Repository("SynonymCodeRepositoryDao")
public class SynonymCodeRepositoryImpl extends AbstractDao<SynonymCodePK, SynonymCode> implements SynonymCodeRepository {
    @Override
    public SynonymCode findById(SynonymCodePK synonymCodePK) {
        SynonymCode hasSection = getByKey(synonymCodePK);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SynonymCode findByIdNative(SynonymCodePK synonymCodePK) {
        SynonymCode synonymCode = null;
        List<SynonymCode> synonymCodeList = (List<SynonymCode>) getEntityManager()
                .createNamedQuery("SynonymCode.findByIdNativeResultClass")
                .setParameter("synonymId", synonymCodePK.getSynonymId())
                .setParameter("code", synonymCodePK.getCode())
                .setParameter("resourceId", synonymCodePK.getResourceId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(synonymCodeList))
            synonymCode = synonymCodeList.get(0);
        return synonymCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SynonymCode findByIdNativeResultClass(SynonymCodePK synonymCodePK) {
        SynonymCode synonymCode = null;
        List<SynonymCode> synonymCodeList = (List<SynonymCode>) getEntityManager()
                .createNamedQuery("SynonymCode.findByIdNativeResultClass")
                .setParameter("synonymId", synonymCodePK.getSynonymId())
                .setParameter("code", synonymCodePK.getCode())
                .setParameter("resourceId", synonymCodePK.getResourceId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(synonymCodeList))
            synonymCode = synonymCodeList.get(0);
        return synonymCode;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SynonymCode> findAllQuery() {
        return (List<SynonymCode>) getEntityManager()
                .createNamedQuery("SynonymCode.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(SynonymCode synonymCode) {
        super.persist(synonymCode);
    }

    @Override
    public int insertNative(String synonymId, String code, String resourceId) {
        return getEntityManager()
                .createNamedQuery("SynonymCode.insertNative")
                .setParameter("synonymId", synonymId)
                .setParameter("code", code)
                .setParameter("resourceId", resourceId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(SynonymCodePK synonymCodePK) {
        SynonymCode synonymCode = findById( synonymCodePK );
        if(synonymCode ==null)
            return false;
        super.delete(synonymCode);
        return true;
    }

    @Override
    public void delete(SynonymCode synonymCode) {
        super.delete(synonymCode);
    }

    @Override
    public SynonymCode update(SynonymCode synonymCode) {
        return super.update(synonymCode);
    }

    @Override
    public int updateByIdQuery(SynonymCode synonymCode) {
        return 0;
    }
}
