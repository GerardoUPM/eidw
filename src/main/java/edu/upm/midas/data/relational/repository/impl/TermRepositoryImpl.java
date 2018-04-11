package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Term;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.TermRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TermRepositoryImpl
 * @see
 */
@Repository("TermRepositoryDao")
public class TermRepositoryImpl extends AbstractDao<Integer, Term> implements TermRepository {

    @Override
    public Term findById(Integer termId) {
        Term term = getByKey(termId);
        return term;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Term findByIdQuery(Integer termId) {
        Term term = null;
        List<Term> termList = (List<Term>) getEntityManager()
                .createNamedQuery("Term.findById")
                .setParameter("termId", termId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(termList))
            term = termList.get(0);
        return term;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Term findByIdNative(Integer termId) {
        Term term = null;
        List<Term> termList = (List<Term>) getEntityManager()
                .createNamedQuery("Term.findByIdNative")
                .setParameter("termId", termId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(termList))
            term = termList.get(0);
        return term;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Term findByIdNativeResultClass(Integer termId) {
        Term term = null;
        List<Term> termList = (List<Term>) getEntityManager()
                .createNamedQuery("Term.findByIdNativeResultClass")
                .setParameter("termId", termId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(termList))
            term = termList.get(0);
        return term;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Term findByNameQuery(String termName) {
        Term term = null;
        List<Term> resourceList = (List<Term>) getEntityManager()
                .createNamedQuery("Term.findByNameNative")//BUSCA APLICANDO SENSIBILIDAD ENTRE MAYÚSCULAS Y MINÚSCULAS
                .setParameter("name", termName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(resourceList))
            term = resourceList.get(0);
        return term;
    }

    @SuppressWarnings("unchecked")
    @Override
    public int findIdByNameQuery(String termName) {
        int termId = 0;
        List<Integer> resultList = (List<Integer>) getEntityManager()
                .createNamedQuery("Term.findIdByNameNative")
                .setParameter("name", termName)
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(resultList)) {
            //System.out.println("no nulo" + resultList.toString());
            termId = resultList.get(0);
        }
        return termId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Term> findAllQuery() {
        List<Term> terms = getEntityManager()
                .createNamedQuery("Term.findAll")
                .setMaxResults(0)
                .getResultList();
        return terms;
    }

    @Override
    public void persist(Term term) {
        super.persist(term);
    }

    @Override
    public int insertNative(Integer resourceId, String name) {
        return getEntityManager()
                .createNamedQuery("Term.insertNative")
                .setParameter("resourceId", resourceId)
                .setParameter("name", name)
                .executeUpdate();
    }

    @Override
    public boolean deleteById(Integer termId) {
        Term term = findById( termId );
        if(term ==null)
            return false;
        super.delete(term);
        return true;
    }

    @Override
    public void delete(Term term) {
        super.delete(term);
    }

    @Override
    public Term update(Term term) {
        return super.update(term);
    }

    @Override
    public int updateByIdQuery(Term term) {
        return 0;
    }
}
