package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Synonym;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.SynonymRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className SynonymRepositoryImpl
 * @see
 */
@Repository("SynonymRepositoryDao")
public class SynonymRepositoryImpl extends AbstractDao<Integer, Synonym> implements SynonymRepository {
    @Override
    public Synonym findById(Integer synonymId) {
        Synonym synonym = getByKey(synonymId);
        return synonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Synonym findByIdQuery(Integer synonymId) {
        Synonym synonym = null;
        List<Synonym> termList = (List<Synonym>) getEntityManager()
                .createNamedQuery("Synonym.findById")
                .setParameter("synonymId", synonymId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(termList))
            synonym = termList.get(0);
        return synonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Synonym findByNameQuery(String synonymName) {
        Synonym synonym = null;
        List<Synonym> resourceList = (List<Synonym>) getEntityManager()
                .createNamedQuery("Synonym.findByNameNative")//BUSCA APLICANDO SENSIBILIDAD ENTRE MAYÚSCULAS Y MINÚSCULAS
                .setParameter("name", synonymName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(resourceList))
            synonym = resourceList.get(0);
        return synonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int findIdByNameQuery(String synonymName) {
        int synonymId = 0;
        List<Integer> resultList = (List<Integer>) getEntityManager()
                .createNamedQuery("Synonym.findIdByNameNative")
                .setParameter("name", synonymName)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(resultList)) {
            //System.out.println("no nulo" + resultList.toString());
            synonymId = resultList.get(0);
        }
        return synonymId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Synonym findByIdNative(Integer synonymId) {
        Synonym synonym = null;
        List<Synonym> synonymList = (List<Synonym>) getEntityManager()
                .createNamedQuery("Synonym.findByIdNative")
                .setParameter("synonymId", synonymId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(synonymList))
            synonym = synonymList.get(0);
        return synonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Synonym findByIdNativeResultClass(Integer synonymId) {
        Synonym synonym = null;
        List<Synonym> synonymList = (List<Synonym>) getEntityManager()
                .createNamedQuery("Synonym.findByIdNativeResultClass")
                .setParameter("synonymId", synonymId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(synonymList))
            synonym = synonymList.get(0);
        return synonym;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Synonym> findAllQuery() {
        List<Synonym> synonyms = getEntityManager()
                .createNamedQuery("Synonym.findAll")
                .setMaxResults(0)
                .getResultList();
        return synonyms;
    }

    @Override
    public void persist(Synonym synonym) {
        super.persist(synonym);
    }

    @Override
    public int insertNative(String name) {
        return getEntityManager()
                .createNamedQuery("Synonym.insertNative")
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(Integer synonymId) {
        Synonym synonym = findById( synonymId );
        if(synonym ==null)
            return false;
        super.delete(synonym);
        return true;
    }

    @Override
    public void delete(Synonym synonym) {
        super.delete(synonym);
    }

    @Override
    public Synonym update(Synonym synonym) {
        return super.update(synonym);
    }

    @Override
    public Integer updateByIdQuery(Synonym synonym) {
        return null;
    }
}
