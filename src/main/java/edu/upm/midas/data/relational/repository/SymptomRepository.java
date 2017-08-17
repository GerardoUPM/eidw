package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.HasSemanticType;
import edu.upm.midas.data.relational.entities.edsssdb.Symptom;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SymptomRepository
 * @see
 */
public interface SymptomRepository {

    Symptom findById(String cui);

    Symptom findByIdQuery(String cui);

    Symptom findByNameQuery(String symptomName);

    Symptom findByIdNative(String cui);

    Symptom findByIdNativeResultClass(String cui);

    boolean findHasSemanticTypeIdNative(String cui, String semanticType);

    List<Object[]> findBySourceAndVersionNative(Date version, String source);

    List<Symptom> findAllQuery();

    void persist(Symptom symptom);

    int insertNative(String cui, String name);

    boolean deleteById(String cui);

    void delete(Symptom symptom);

    Symptom update(Symptom symptom);

    int updateByIdQuery(Symptom symptom);
    
}

