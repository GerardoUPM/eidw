package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.SemanticType;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SemanticTypeService
 * @see
 */
public interface SemanticTypeService {

    SemanticType findById(String semanticType);

    SemanticType findByDescriptionQuery(String semanticTypeName);

    List<SemanticType> findAll();

    void save(SemanticType semanticType);

    int insertNative(String semanticType, String description);

    int insertNativeHasSemanticType(String cui, String semanticType);

    boolean updateFindFull(SemanticType semanticType);

    boolean updateFindPartial(SemanticType semanticType);

    boolean deleteById(String semanticType);

}
