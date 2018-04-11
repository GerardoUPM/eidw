package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonym;
import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonymPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.DiseaseSynonymRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className DiseaseSynonymRepositoryImpl
 * @see
 */
@Repository("DiseaseSynonymRepositoryDao")
public class DiseaseSynonymRepositoryImpl extends AbstractDao<DiseaseSynonymPK, DiseaseSynonym> implements DiseaseSynonymRepository {
    @Override
    public DiseaseSynonym findById(DiseaseSynonymPK diseaseSynonymPK) {
        DiseaseSynonym diseaseSynonym = getByKey(diseaseSynonymPK);
        return diseaseSynonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DiseaseSynonym findByIdNative(DiseaseSynonymPK diseaseSynonymPK) {
        DiseaseSynonym diseaseSynonym = null;
        List<DiseaseSynonym> diseaseSynonymList = (List<DiseaseSynonym>) getEntityManager()
                .createNamedQuery("DiseaseSynonym.findByIdNativeResultClass")
                .setParameter("diseaseId", diseaseSynonymPK.getDiseaseId())
                .setParameter("synonymId", diseaseSynonymPK.getSynonymId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseSynonymList))
            diseaseSynonym = diseaseSynonymList.get(0);
        return diseaseSynonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public DiseaseSynonym findByIdNativeResultClass(DiseaseSynonymPK diseaseSynonymPK) {
        DiseaseSynonym diseaseSynonym = null;
        List<DiseaseSynonym> diseaseSynonymList = (List<DiseaseSynonym>) getEntityManager()
                .createNamedQuery("DiseaseSynonym.findByIdNativeResultClass")
                .setParameter("diseaseId", diseaseSynonymPK.getDiseaseId())
                .setParameter("synonymId", diseaseSynonymPK.getSynonymId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseSynonymList))
            diseaseSynonym = diseaseSynonymList.get(0);
        return diseaseSynonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<DiseaseSynonym> findAllQuery() {
        return (List<DiseaseSynonym>) getEntityManager()
                .createNamedQuery("DiseaseSynonym.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(DiseaseSynonym diseaseSynonym) {
        super.persist(diseaseSynonym);
    }

    @Override
    public int insertNative(String diseaseId, String synonymId) {
        return getEntityManager()
                .createNamedQuery("DiseaseSynonym.insertNative")
                .setParameter("diseaseId", diseaseId)
                .setParameter("synonymId", synonymId)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(DiseaseSynonymPK diseaseSynonymPK) {
        DiseaseSynonym diseaseSynonym = findById( diseaseSynonymPK );
        if(diseaseSynonym ==null)
            return false;
        super.delete(diseaseSynonym);
        return true;
    }

    @Override
    public void delete(DiseaseSynonym diseaseSynonym) {
        super.delete(diseaseSynonym);
    }

    @Override
    public DiseaseSynonym update(DiseaseSynonym diseaseSynonym) {
        return super.update(diseaseSynonym);
    }

    @Override
    public int updateByIdQuery(DiseaseSynonym diseaseSynonym) {
        return 0;
    }
}
