package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticTypePK;
import edu.upm.midas.data.relational.service.HasSemanticTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by gerardo on 29/06/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className HasSemanticTypeServiceImpl
 * @see
 */
@Service("hasSemanticTypeService")
public class HasSemanticTypeServiceImpl implements HasSemanticTypeService {
    @Override
    public HasSemanticType findById(HasSemanticTypePK hasSemanticTypePK) {
        return null;
    }

    @Override
    public HasSemanticType findByIdQuery(HasSemanticTypePK hasSemanticTypePK) {
        return null;
    }

    @Override
    public HasSemanticType findByIdNative(HasSemanticTypePK hasSemanticTypePK) {
        return null;
    }

    @Override
    public List<HasSemanticType> findAllQuery() {
        return null;
    }

    @Override
    public void save(HasSemanticType hasSemanticType) {

    }

    @Override
    public boolean deleteById(HasSemanticTypePK hasSemanticTypePK) {
        return false;
    }

    @Override
    public void delete(HasSemanticType hasSemanticType) {

    }

    @Override
    public HasSemanticType update(HasSemanticType hasSemanticType) {
        return null;
    }

    @Override
    public int updateByIdQuery(HasSemanticType hasSemanticType) {
        return 0;
    }
}
