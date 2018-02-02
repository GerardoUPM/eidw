package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.HasSymptom;
import edu.upm.midas.data.relational.entities.edsssdb.HasSymptomPK;

import java.util.List;

/**
 * Created by gerardo on 19/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSymptomService
 * @see
 */
public interface HasSymptomRepository {

    HasSymptom findById(HasSymptomPK hasSymptomPK);

    HasSymptom findByIdQuery(HasSymptomPK hasSymptomPK);

    HasSymptom findByIdNative(HasSymptomPK hasSymptomPK);

    HasSymptom findByIdNativeResultClass(HasSymptomPK hasSymptomPK);

    List<HasSymptom> findAllQuery();

    void persist(HasSymptom hasSymptom);

    int insertNative(String textId, String cui, boolean validated, String matchedWords, String positionalInfo);

    boolean deleteById(HasSymptomPK hasSymptomPK);

    void delete(HasSymptom hasSymptom);

    HasSymptom update(HasSymptom hasSymptom);

    int updateByIdQuery(HasSymptom hasSymptom);

    int updateValidatedNative(String version, String sourceId, String cui, boolean validated);

}
