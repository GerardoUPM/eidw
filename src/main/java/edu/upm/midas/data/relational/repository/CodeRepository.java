package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.Code;
import edu.upm.midas.data.relational.entities.edsssdb.CodePK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 13/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className CodeRepository
 * @see
 */
public interface CodeRepository {

    Code findById(CodePK codePK);

    Code findByIdQuery(CodePK codePK);

    Code findByCodeQuery(String code);

    Code findByResourceIdQuery(int resourceId);

    Code findByIdNative(CodePK codePK);

    Code findByIdNativeResultClass(CodePK codePK);

    List<Code> findAllQuery();

    void persist(Code code);

    int insertNative(String code, int resourceId);

    int insertNativeUrl(String code, int resourceId, String urlId);

    int insertNativeHasCode(String documentId, Date date, String code, int resourceId);

    boolean deleteById(CodePK codePK);

    void delete(Code code);

    Code update(Code code);

    int updateByIdQuery(Code code);
    
}