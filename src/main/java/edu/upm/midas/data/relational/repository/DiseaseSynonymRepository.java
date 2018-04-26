package edu.upm.midas.data.relational.repository;


import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonym;
import edu.upm.midas.data.relational.entities.edsssdb.DiseaseSynonymPK;

import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionRepository
 * @see
 */
public interface DiseaseSynonymRepository {

    DiseaseSynonym findById(DiseaseSynonymPK diseaseSynonymPK);

    DiseaseSynonym findByIdNative(DiseaseSynonymPK diseaseSynonymPK);

    DiseaseSynonym findByIdNativeResultClass(DiseaseSynonymPK diseaseSynonymPK);

    List<DiseaseSynonym> findAllQuery();

    void persist(DiseaseSynonym diseaseSynonym);

    int insertNative(String diseaseId, int synonymId);

    boolean deleteById(DiseaseSynonymPK diseaseSynonymPK);

    void delete(DiseaseSynonym diseaseSynonym);

    DiseaseSynonym update(DiseaseSynonym diseaseSynonym);

    int updateByIdQuery(DiseaseSynonym diseaseSynonym);
    
}
