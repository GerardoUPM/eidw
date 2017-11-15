package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.SemanticType;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.SemanticTypeRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SemanticTypeRepositoryImpl
 * @see
 */
@Repository("SemanticTypeRepositoryDao")
public class SemanticTypeRepositoryImpl extends AbstractDao<String, SemanticType>
                                        implements SemanticTypeRepository {

    public SemanticType findById(String semanticType) {
        SemanticType semanticType1 = getByKey(semanticType);
        return semanticType1;
    }

    @SuppressWarnings("unchecked")
    public SemanticType findByIdQuery(String semanticType) {
        SemanticType semanticType1 = null;
        List<SemanticType> semanticTypeList = (List<SemanticType>) getEntityManager()
                .createNamedQuery("SemanticType.findByIdNativeResultClass")
                .setParameter("semanticType", semanticType)
                .getResultList();
        if (CollectionUtils.isNotEmpty(semanticTypeList))
            semanticType1 = semanticTypeList.get(0);
        return semanticType1;
    }

    @SuppressWarnings("unchecked")
    public SemanticType findByDescriptionQuery(String description) {
        SemanticType semanticType1 = null;
        List<SemanticType> semanticTypeList = (List<SemanticType>) getEntityManager()
                .createNamedQuery("SemanticType.findByDescription")
                .setParameter("description", description)
                .getResultList();
        if (CollectionUtils.isNotEmpty(semanticTypeList))
            semanticType1 = semanticTypeList.get(0);
        return semanticType1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SemanticType findByIdNative(String semanticType) {
        SemanticType semanticType1 = null;
        List<SemanticType> semanticTypeList = (List<SemanticType>) getEntityManager()
                .createNamedQuery("SemanticType.findByIdNativeResultClass")
                .setParameter("semanticType", semanticType)
                .getResultList();
        if (CollectionUtils.isNotEmpty(semanticTypeList))
            semanticType1 = semanticTypeList.get(0);
        return semanticType1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public SemanticType findByIdNativeResultClass(String semanticType) {
        SemanticType semanticType1 = null;
        List<SemanticType> semanticTypeList = (List<SemanticType>) getEntityManager()
                .createNamedQuery("SemanticType.findByIdNativeResultClass")
                .setParameter("semanticType", semanticType)
                .getResultList();
        if (CollectionUtils.isNotEmpty(semanticTypeList))
            semanticType1 = semanticTypeList.get(0);
        return semanticType1;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<SemanticType> findAllQuery() {
        return (List<SemanticType>) getEntityManager()
                .createNamedQuery("SemanticType.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(SemanticType semanticType) {
        super.persist(semanticType);
    }

    @Override
    public int insertNative(String semanticType, String description) {
        return getEntityManager()
                .createNamedQuery("SemanticType.insertNative")
                .setParameter("semanticType", semanticType)
                .setParameter("description", description)
                .executeUpdate();
    }

    @Override
    public int insertNativeHasSemanticType(String cui, String semanticType) {
        return getEntityManager()
                .createNamedQuery("HasSemanticType.insertNative")
                .setParameter("cui", cui)
                .setParameter("semanticType", semanticType)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(String semanticType) {
        SemanticType semanticType1 = findById( semanticType );
        if(semanticType1 ==null)
            return false;
        super.delete(semanticType1);
        return true;
    }

    @Override
    public void delete(SemanticType semanticType) {
        super.delete(semanticType);
    }

    @Override
    public SemanticType update(SemanticType semanticType) {
        return super.update(semanticType);
    }

    @Override
    public int updateByIdQuery(SemanticType semanticType) {
        return 0;
    }
}
