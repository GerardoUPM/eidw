package edu.upm.midas.data.relational.repository;


import edu.upm.midas.data.relational.entities.edsssdb.SynonymCode;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCodePK;

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
public interface SynonymCodeRepository {

    SynonymCode findById(SynonymCodePK synonymCodePK);

    SynonymCode findByIdNative(SynonymCodePK synonymCodePK);

    SynonymCode findByIdNativeResultClass(SynonymCodePK synonymCodePK);

    List<SynonymCode> findAllQuery();

    void persist(SynonymCode synonymCode);

    int insertNative(int synonymId, String code, int resourceId);

    boolean deleteById(SynonymCodePK synonymCodePK);

    void delete(SynonymCode synonymCode);

    SynonymCode update(SynonymCode synonymCode);

    int updateByIdQuery(SynonymCode synonymCode);
    
}
