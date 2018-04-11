package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Synonym;
import edu.upm.midas.data.relational.repository.SynonymRepository;
import edu.upm.midas.data.relational.service.SynonymService;
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
 * @className SynonymServiceImpl
 * @see
 */
@Service("synonymService")
public class SynonymServiceImpl implements SynonymService {

    @Autowired
    private SynonymRepository daoSynonym;

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Synonym findById(Integer synonymId) {
        Synonym synonym = daoSynonym.findById(synonymId);
        return synonym;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Synonym findByIdNative(Integer synonymId) {
        Synonym synonym = daoSynonym.findByIdNative(synonymId);
        return synonym;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Synonym findByNameQuery(String name) {
        Synonym synonym = daoSynonym.findByNameQuery(name);
        return synonym;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public int findIdByNameQuery(String name) {
        return daoSynonym.findIdByNameQuery( name );
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Synonym> findAll() {
        List<Synonym> daoSynonymAllQuery = daoSynonym.findAllQuery();
        return daoSynonymAllQuery;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Synonym synonym) {
        daoSynonym.persist(synonym);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String name) {
        return daoSynonym.insertNative(name);
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Synonym synonym) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Synonym synonym) {
        return false;
    }

    @Override
    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(Integer synonymId) {
        return false;
    }
}
