package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticTypePK;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSemanticTypeService
 * @see
 */
public interface HasSemanticTypeRepository {

    HasSemanticType findById(HasSemanticTypePK hasSemanticTypePK);

    HasSemanticType findByIdQuery(HasSemanticTypePK hasSemanticTypePK);

    HasSemanticType findByIdNative(HasSemanticTypePK hasSemanticTypePK);

//    HasSemanticType findByIdNativeResultClass(HasSemanticTypePK hasSemanticTypePK);

    List<HasSemanticType> findAllQuery();

    void persist(HasSemanticType hasSemanticType);

//    int insertNative(String textId, String cui, boolean validated, String matchedWords, String positionalInfo);

    boolean deleteById(HasSemanticTypePK hasSemanticTypePK);

    void delete(HasSemanticType hasSemanticType);

    HasSemanticType update(HasSemanticType hasSemanticType);

    int updateByIdQuery(HasSemanticType hasSemanticType);

//    int updateValidatedNative(String version, String sourceId, String cui, boolean validated);

//    int updateMatchedWordsAndPositionalInfoNative(String textId, String cui, String matchedWords, String positionalInfo);

}
