package edu.upm.midas.data.relational.service;

import edu.upm.midas.data.relational.entities.edsssdb.SynonymCode;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCodePK;

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
public interface SynonymCodeService {

    SynonymCode findById(SynonymCodePK synonymCodePK);

    SynonymCode findByIdNative(SynonymCodePK synonymCodePK);

    List<SynonymCode> findAll();

    void save(SynonymCode synonymCode);

    int insertNative(String synonymId, String code, String resourceId);

    boolean updateFindFull(SynonymCode synonymCode, SynonymCodePK synonymCodePK);

    boolean updateFindPartial(SynonymCode synonymCode, SynonymCodePK synonymCodePK);

    boolean deleteById(SynonymCodePK synonymCodePK);
    
}
