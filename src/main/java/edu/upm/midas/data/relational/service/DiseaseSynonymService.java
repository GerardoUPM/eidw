package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonym;
import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonymPK;

import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionService
 * @see
 */
public interface DiseaseSynonymService {

    DiseaseSynonym findById(DiseaseSynonymPK diseaseSynonymPK);

    DiseaseSynonym findByIdNative(DiseaseSynonymPK diseaseSynonymPK);

    List<DiseaseSynonym> findAll();

    void save(DiseaseSynonym diseaseSynonym);

    int insertNative(String diseaseId, int synonymId);

    boolean updateFindFull(DiseaseSynonym hasSection, DiseaseSynonymPK diseaseSynonymPK);

    boolean updateFindPartial(DiseaseSynonym hasSection, DiseaseSynonymPK diseaseSynonymPK);

    boolean deleteById(DiseaseSynonymPK diseaseSynonymPK);
    
}
