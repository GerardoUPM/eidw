package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.HasDisease;
import edu.upm.midas.data.relational.entities.edsssdb.HasDiseasePK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasDiseaseService
 * @see
 */
public interface HasDiseaseService {

    HasDisease findById(HasDiseasePK hasDiseasePK);

    List<HasDisease> findAll();

    void save(HasDisease hasDisease);

    int insertNative(String documentId, Date date, String diseaseId);

    boolean updateFindFull(HasDisease hasDisease, HasDiseasePK hasDiseasePK);

    boolean updateFindPartial(HasDisease hasDisease, HasDiseasePK hasDiseasePK);

    boolean deleteById(HasDiseasePK hasDiseasePK);
    
}
