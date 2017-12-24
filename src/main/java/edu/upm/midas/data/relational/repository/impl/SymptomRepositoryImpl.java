package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.Symptom;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.SymptomRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SymptomRepositoryImpl
 * @see
 */
@Repository("SymptomRepositoryDao")
public class SymptomRepositoryImpl extends AbstractDao<String, Symptom>
                                    implements SymptomRepository {

    public Symptom findById(String cui) {
        Symptom symptom = getByKey(cui);
        return symptom;
    }

    @SuppressWarnings("unchecked")
    public Symptom findByIdQuery(String cui) {
        Symptom symptom = null;
        List<Symptom> symptomList = (List<Symptom>) getEntityManager()
                .createNamedQuery("Symptom.findByIdNativeResultClass")
                .setParameter("cui", cui)
                .getResultList();
        if (CollectionUtils.isNotEmpty(symptomList))
            symptom = symptomList.get(0);
        return symptom;
    }

    @SuppressWarnings("unchecked")
    public Symptom findByNameQuery(String symptomName) {
        Symptom symptom = null;
        List<Symptom> symptomList = (List<Symptom>) getEntityManager()
                .createNamedQuery("Symptom.findByName")
                .setParameter("name", symptomName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(symptomList))
            symptom = symptomList.get(0);
        return symptom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Symptom findByIdNative(String cui) {
        Symptom symptom = null;
        List<Symptom> symptomList = (List<Symptom>) getEntityManager()
                .createNamedQuery("Symptom.findByIdNativeResultClass")
                .setParameter("cui", cui)
                .getResultList();
        if (CollectionUtils.isNotEmpty(symptomList))
            symptom = symptomList.get(0);
        return symptom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Symptom findByIdNativeResultClass(String cui) {
        Symptom symptom = null;
        List<Symptom> symptomList = (List<Symptom>) getEntityManager()
                .createNamedQuery("Symptom.findByIdNativeResultClass")
                .setParameter("cui", cui)
                .getResultList();
        if (CollectionUtils.isNotEmpty(symptomList))
            symptom = symptomList.get(0);
        return symptom;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean findHasSemanticTypeIdNative(String cui, String semanticType) {
        List<HasSemanticType> hasSemanticTypeList = (List<HasSemanticType>) getEntityManager()
                .createNamedQuery("HasSemanticType.findByIdNative")
                .setParameter("cui", cui)
                .setParameter("semanticType", semanticType)
                .getResultList();
        return hasSemanticTypeList.size() > 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Object[]> findBySourceAndVersionNative(Date version, String source) {
        List<Object[]> symptoms = null;
        List<Object[]> symptomList = (List<Object[]>) getEntityManager()
                .createNamedQuery("Symptom.findBySourceAndVersionNative")
                .setParameter("version", version)
                .setParameter("source", source)
                //.setMaxResults(1000)
                .getResultList();
        if (CollectionUtils.isNotEmpty(symptomList))
            symptoms = symptomList;
        return symptoms;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Symptom> findAllQuery() {
        return (List<Symptom>) getEntityManager()
                .createNamedQuery("Symptom.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(Symptom symptom) {
        super.persist(symptom);
    }

    @Override
    public int insertNative(String cui, String name) {
        return getEntityManager()
                .createNamedQuery("Symptom.insertNative")
                .setParameter("cui", cui)
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(String cui) {
        Symptom symptom = findById( cui );
        if(symptom ==null)
            return false;
        super.delete(symptom);
        return true;
    }

    @Override
    public void delete(Symptom symptom) {
        super.delete(symptom);
    }

    @Override
    public Symptom update(Symptom symptom) {
        return super.update(symptom);
    }

    @Override
    public int updateByIdQuery(Symptom symptom) {
        return 0;
    }
}
