package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticTypePK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.HasSemanticTypeRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 29/06/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className HasSemanticTypeRepositoryImpl
 * @see
 */
@Repository("HasSemanticTypeRepositoryDao")
public class HasSemanticTypeRepositoryImpl extends AbstractDao<HasSemanticTypePK, HasSemanticType> implements HasSemanticTypeRepository {

    @SuppressWarnings("unchecked")
    @Override
    public HasSemanticType findById(HasSemanticTypePK hasSemanticTypePK) {
        HasSemanticType hasSemanticType = getByKey(hasSemanticTypePK);
        return hasSemanticType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasSemanticType findByIdQuery(HasSemanticTypePK hasSemanticTypePK) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasSemanticType findByIdNative(HasSemanticTypePK hasSemanticTypePK) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HasSemanticType> findAllQuery() {
        return (List<HasSemanticType>) getEntityManager()
                .createNamedQuery("HasSemanticType.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    @Override
    public void persist(HasSemanticType hasSemanticType) {
        super.persist(hasSemanticType);
    }

    @Override
    public boolean deleteById(HasSemanticTypePK hasSemanticTypePK) {
        return false;
    }

    @Override
    public void delete(HasSemanticType hasSemanticType) {
        super.delete(hasSemanticType);
    }

    @Override
    public HasSemanticType update(HasSemanticType hasSemanticType) {
        return super.update(hasSemanticType);
    }

    @Override
    public int updateByIdQuery(HasSemanticType hasSemanticType) {
        return 0;
    }
}
