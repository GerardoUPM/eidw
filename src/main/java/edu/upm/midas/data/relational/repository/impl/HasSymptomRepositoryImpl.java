package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSymptom;
import edu.upm.midas.data.relational.entities.edsssdb.HasSymptomPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.HasSymptomRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSymptomServiceImpl
 * @see
 */
@Repository("HasSymptomRepositoryDao")
public class HasSymptomRepositoryImpl extends AbstractDao<HasSymptomPK, HasSymptom>
                                        implements HasSymptomRepository {

    public HasSymptom findById(HasSymptomPK hasSymptomPK) {
        HasSymptom hasSymptom = getByKey(hasSymptomPK);
        return hasSymptom;
    }

    @SuppressWarnings("unchecked")
    public HasSymptom findByIdQuery(HasSymptomPK hasSymptomPK) {
        HasSymptom hasSymptom = null;
        List<HasSymptom> hasSymptomList = (List<HasSymptom>) getEntityManager()
                .createNamedQuery("HasSymptom.findById")
                .setParameter("textId", hasSymptomPK.getTextId())
                .setParameter("cui", hasSymptomPK.getCui())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSymptomList))
            hasSymptom = hasSymptomList.get(0);
        return hasSymptom;
    }

    @SuppressWarnings("unchecked")
    public HasSymptom findByIdNative(HasSymptomPK hasSymptomPK) {
        HasSymptom hasSymptom = null;
        List<HasSymptom> hasSymptomList = (List<HasSymptom>) getEntityManager()
                .createNamedQuery("HasSymptom.findByIdNative")
                .setParameter("textId", hasSymptomPK.getTextId())
                .setParameter("cui", hasSymptomPK.getCui())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSymptomList))
            hasSymptom = hasSymptomList.get(0);
        return hasSymptom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasSymptom findByIdNativeResultClass(HasSymptomPK hasSymptomPK) {
        HasSymptom hasSymptom = null;
        List<HasSymptom> hasSymptomList = (List<HasSymptom>) getEntityManager()
                .createNamedQuery("HasSymptom.findByIdNativeResultClass")
                .setParameter("textId", hasSymptomPK.getTextId())
                .setParameter("cui", hasSymptomPK.getCui())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSymptomList))
            hasSymptom = hasSymptomList.get(0);
        return hasSymptom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HasSymptom> findAllQuery() {
        return (List<HasSymptom>) getEntityManager()
                .createNamedQuery("HasSymptom.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(HasSymptom hasSymptom) {
        super.persist(hasSymptom);
    }

    @Override
    public int insertNative(String textId, String cui, boolean validated) {
        return getEntityManager()
                .createNamedQuery("HasSymptom.insertNative")
                .setParameter("textId", textId)
                .setParameter("cui", cui)
                .setParameter("validated", validated)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(HasSymptomPK hasSymptomPK) {
        return false;
    }

    @Override
    public void delete(HasSymptom hasSymptom) {
        super.delete(hasSymptom);
    }

    @Override
    public HasSymptom update(HasSymptom hasSymptom) {
        return super.update(hasSymptom);
    }

    @Override
    public int updateByIdQuery(HasSymptom hasSymptom) {
        return 0;
    }

    @Override
    public int updateValidatedNative(String version, String sourceId, String cui, boolean validated) {
        return getEntityManager()
                .createNamedQuery("HasSymptom.updateValidatedNative")
                .setParameter("version", "%" + version + "%")
                .setParameter("sourceId", "%" + sourceId + "%")
                .setParameter("cui", cui)
                .setParameter("validated", validated)
                .executeUpdate();
    }
}
