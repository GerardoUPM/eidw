package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Configuration;
import edu.upm.midas.data.relational.repository.ConfigurationRepository;
import edu.upm.midas.data.relational.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 04/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className ConfigurationServiceImpl
 * @see
 */
@Service("configurationService")
public class ConfigurationServiceImpl implements ConfigurationService {

    @Autowired
    private ConfigurationRepository daoConf;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Configuration findById(String configurationId) {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Configuration findByIdQuery(String configurationId) {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findBySourceNameNative(String sourceName) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findBySourceIdNative(String sourceId) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findByVersionNative(Date version) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findByToolNative(String toolName) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findByContigurationNative(String configuration) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public Object[] findByIdNative(String configuration, int resourceId) {
        return new Object[0];
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    @Override
    public List<Configuration> findAllNative() {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public void persist(Configuration configuration) {
        daoConf.persist(configuration);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public int insertNative(String configurationId, String sourceId, Date version, String tool, String configuration) {
        return daoConf.insertNative(configurationId, sourceId, version, tool, configuration);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public boolean deleteById(String configurationId) {
        return false;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public void delete(Configuration configuration) {

    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public Configuration update(Configuration configuration) {
        return null;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    @Override
    public int updateByIdQuery(Configuration configuration) {
        return 0;
    }
}
