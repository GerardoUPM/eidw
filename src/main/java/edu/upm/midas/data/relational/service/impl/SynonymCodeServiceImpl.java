package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCode;
import edu.upm.midas.data.relational.entities.edsssdb.SynonymCodePK;
import edu.upm.midas.data.relational.repository.SynonymCodeRepository;
import edu.upm.midas.data.relational.service.SynonymCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 11/04/2018.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className SynonymCodeServiceImpl
 * @see
 */
@Service("synonymCodeService")
public class SynonymCodeServiceImpl implements SynonymCodeService {

    @Autowired
    private SynonymCodeRepository daoSynonymCode;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public SynonymCode findById(SynonymCodePK synonymCodePK) {
        SynonymCode synonymCode = daoSynonymCode.findById(synonymCodePK);
        return synonymCode;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public SynonymCode findByIdNative(SynonymCodePK synonymCodePK) {
        return daoSynonymCode.findByIdNative(synonymCodePK);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<SynonymCode> findAll() {
        List<SynonymCode> synonymCodes = daoSynonymCode.findAllQuery();
        return synonymCodes;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(SynonymCode synonymCode) {
daoSynonymCode.persist(synonymCode);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(int synonymId, String code, int resourceId) {
        return daoSynonymCode.insertNative(synonymId, code, resourceId);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(SynonymCode synonymCode, SynonymCodePK synonymCodePK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(SynonymCode synonymCode, SynonymCodePK synonymCodePK) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(SynonymCodePK synonymCodePK) {
        return false;
    }
}
