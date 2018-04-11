package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.Synonym;

import java.util.List;

/**
 * Created by gerardo on 09/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edsssdb
 * @className SynonymRepository
 * @see
 */
public interface SynonymRepository {

    Synonym findById(Integer synonymId);

    Synonym findByIdQuery(Integer synonymId);

    Synonym findByNameQuery(String synonymName);

    int findIdByNameQuery(String synonymName);

    Synonym findByIdNative(Integer synonymId);

    Synonym findByIdNativeResultClass(Integer synonymId);

    List<Synonym> findAllQuery();

    void persist(Synonym synonym);

    int insertNative(String name);

    boolean deleteById(Integer synonymId);

    void delete(Synonym synonym);

    Synonym update(Synonym synonym);

    Integer updateByIdQuery(Synonym synonym);
    
}
