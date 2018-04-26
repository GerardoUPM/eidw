package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasDisease;
import edu.upm.midas.data.relational.entities.edsssdb.HasDiseasePK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.HasDiseaseRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 18/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className HasDiseaseRepositoryImpl
 * @see
 */
@Repository("HasDiseaseRepositoryDao")
public class HasDiseaseRepositoryImpl extends AbstractDao<HasDiseasePK, HasDisease> implements HasDiseaseRepository {


    public HasDisease findById(HasDiseasePK hasDiseasePK) {
        HasDisease hasDisease = getByKey(hasDiseasePK);
        return hasDisease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasDisease findByIdQuery(HasDiseasePK hasDiseasePK) {
        HasDisease hasDisease = null;
        List<HasDisease> hasDiseases = (List<HasDisease>) getEntityManager()
                .createNamedQuery("HasDisease.findByIdNativeResultClass")
                .setParameter("documentId", hasDiseasePK.getDocumentId())
                .setParameter("date", hasDiseasePK.getDate())
                .setParameter("diseaseId", hasDiseasePK.getDiseaseId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasDiseases))
            hasDisease = hasDiseases.get(0);
        return hasDisease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasDisease findByIdNative(HasDiseasePK hasDiseasePK) {
        HasDisease hasDisease = null;
        List<HasDisease> hasDiseases = (List<HasDisease>) getEntityManager()
                .createNamedQuery("HasDisease.findByIdNativeResultClass")
                .setParameter("documentId", hasDiseasePK.getDocumentId())
                .setParameter("date", hasDiseasePK.getDate())
                .setParameter("diseaseId", hasDiseasePK.getDiseaseId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasDiseases))
            hasDisease = hasDiseases.get(0);
        return hasDisease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasDisease findByIdNativeResultClass(HasDiseasePK hasDiseasePK) {
        HasDisease hasDisease = null;
        List<HasDisease> hasDiseases = (List<HasDisease>) getEntityManager()
                .createNamedQuery("HasDisease.findByIdNativeResultClass")
                .setParameter("documentId", hasDiseasePK.getDocumentId())
                .setParameter("date", hasDiseasePK.getDate())
                .setParameter("diseaseId", hasDiseasePK.getDiseaseId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasDiseases))
            hasDisease = hasDiseases.get(0);
        return hasDisease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HasDisease> findAllQuery() {
        return (List<HasDisease>) getEntityManager()
                .createNamedQuery("HasDisease.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(HasDisease hasDisease) {
        super.persist(hasDisease);
    }

    @Override
    public int insertNative(String documentId, Date date, String diseaseId) {
        return getEntityManager()
                .createNamedQuery("HasDisease.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("diseaseId", diseaseId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(HasDiseasePK hasDiseasePK) {
        return false;
    }

    @Override
    public void delete(HasDisease hasDisease) {

    }

    @Override
    public HasDisease update(HasDisease hasDisease) {
        return null;
    }

    @Override
    public int updateByIdQuery(HasDisease hasDisease) {
        return 0;
    }
}
