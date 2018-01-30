package edu.upm.midas.data.relational.repository.impl;

import edu.upm.midas.data.relational.entities.edsssdb.Disease;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.DiseaseRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DiseaseRepositoryImpl
 * @see
 */
@Repository("DiseaseRepositoryDao")
public class DiseaseRepositoryImpl extends AbstractDao<String, Disease>
                                    implements DiseaseRepository {


    public Disease findById(String diseaseId) {
        Disease disease = getByKey(diseaseId);
        return disease;
    }

    @SuppressWarnings("unchecked")
    public Disease findByIdQuery(String diseaseId) {
        Disease disease = null;
        List<Disease> diseaseList = (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findById")
                .setParameter("diseaseId", diseaseId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    public Object[] findByNameNative(String diseaseName) {
        Object[] disease = null;
        List<Object[]> diseaseList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Disease.findByNameNative")
                .setParameter("name", diseaseName)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    public Disease findByCuiQuery(String cui) {
        Disease disease = null;
        List<Disease> diseaseList = (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findByCui")
                .setParameter("cui", cui)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Disease findLastDiseaseQuery() {
        Disease disease = null;
        List<Disease> diseaseList = (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findLastDisease")
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findLastIdNative() {
        String id = "";
        Object[] idObject = null;
        List<Object[]> res = (List<Object[]>) getEntityManager()
                .createNamedQuery("Disease.findLastIdNative")
                .setMaxResults(1)
                .getResultList();

        if (CollectionUtils.isNotEmpty(res)) {
            //System.out.println("RESS: "+res.get(0));
            idObject = res.get(0);
            //id = idObject[0].toString();
        }
        return idObject;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Disease findByIdNativeMapping(String diseaseId) {
        Disease source = null;
        List<Disease> listSource = (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findByIdNativeMapping")
                .setParameter("diseaseId", diseaseId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(listSource))
            source = listSource.get(0);
        return source;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Disease findByIdNativeResultClass(String diseaseId) {
        Disease disease = null;
        List<Disease> diseaseList = (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findByIdNativeResultClass")
                .setParameter("diseaseId", diseaseId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Disease> findAllQuery() {
        return (List<Disease>) getEntityManager()
                .createNamedQuery("Disease.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findAllBySourceAndVersionNative(String sourceName, Date version) {
        return (List<Object[]>) getEntityManager()
                .createNamedQuery("Disease.findAllBySourceAndVersionNative")
                .setParameter("sourceName", sourceName)
                .setParameter("version", version)
                //.setMaxResults(10)
                .getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findByIdAndSourceAndVersionNative(String diseaseId, String sourceName, Date version) {
        Object[] disease = null;
        List<Object[]> diseaseList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Disease.findByIdAndSourceAndVersionNative")
                .setParameter("diseaseId", diseaseId)
                .setParameter("sourceName", sourceName)
                .setParameter("version", version)
                //.setMaxResults(100)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object[] findByCuiAndSourceAndVersionNative(String cui, String sourceName, Date version) {
        Object[] disease = null;
        List<Object[]> diseaseList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Disease.findByCuiAndSourceAndVersionNative")
                .setParameter("cui", cui)
                .setParameter("sourceName", sourceName)
                .setParameter("version", version)
                //.setMaxResults(100)
                .getResultList();
        if (CollectionUtils.isNotEmpty(diseaseList))
            disease = diseaseList.get(0);
        return disease;
    }

    public void persist(Disease disease) {
        super.persist(disease);
    }

    @Override
    public int insertNative(String diseaseId, String name, String cui) {
        return getEntityManager()
                .createNamedQuery("Disease.insertNative")
                .setParameter("diseaseId", diseaseId)
                .setParameter("name", name)
                .setParameter("cui", cui)
                .executeUpdate();
    }

    @Override
    public int insertNativeHasDisease(String documentId, Date date, String diseaseId) {
        return getEntityManager()
                .createNamedQuery("HasDisease.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("diseaseId", diseaseId)
                .executeUpdate();
    }

    public boolean deleteById(String diseaseId) {
        Disease disease = findById( diseaseId );
        if(disease ==null)
            return false;
        super.delete(disease);
        return true;
    }

    public void delete(Disease disease) {
        super.delete(disease);
    }

    public Disease update(Disease disease) {
        return super.update(disease);
    }

    @Override
    public int updateByIdQuery(Disease disease) {
        return getEntityManager()
                .createNamedQuery("Disease.updateById")
                .setParameter("diseaseId", disease.getDiseaseId())
                .setParameter("name", disease.getName())
                .setParameter("cui", disease.getCui())
                .executeUpdate();
    }

    @Override
    public int updateCuiByIdAndSourceAndVersionNative(String diseaseId, String cui, String sourceName, Date version) {
        return getEntityManager()
                .createNamedQuery("Disease.updateCuiByIdAndSourceAndVersionNative")
                .setParameter("diseaseId", diseaseId)
                .setParameter("cui", cui)
                .setParameter("sourceName", sourceName)
                .setParameter("version", version)
                .executeUpdate();
    }
}
