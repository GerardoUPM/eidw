package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Configuration;
import edu.upm.midas.data.relational.repository.ConfigurationRepository;
import edu.upm.midas.data.relational.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public Configuration findById(String configurationId) {
        return null;
    }

    @Override
    public Configuration findByIdQuery(String configurationId) {
        return null;
    }

    @Override
    public Object[] findBySourceNameNative(String sourceName) {
        return new Object[0];
    }

    @Override
    public Object[] findBySourceIdNative(String sourceId) {
        return new Object[0];
    }

    @Override
    public Object[] findByVersionNative(Date version) {
        return new Object[0];
    }

    @Override
    public Object[] findByToolNative(String toolName) {
        return new Object[0];
    }

    @Override
    public Object[] findByContigurationNative(String configuration) {
        return new Object[0];
    }

    @Override
    public Object[] findByIdNative(String configuration, int resourceId) {
        return new Object[0];
    }

    @Override
    public List<Configuration> findAllNative() {
        return null;
    }

    @Override
    public void persist(Configuration configuration) {

    }

    @Override
    public int insertNative(String configurationId, String sourceId, Date version, String tool, String configuration) {
        return daoConf.insertNative(configurationId, sourceId, version, tool, configuration);
    }

    @Override
    public boolean deleteById(String configurationId) {
        return false;
    }

    @Override
    public void delete(Configuration configuration) {

    }

    @Override
    public Configuration update(Configuration configuration) {
        return null;
    }

    @Override
    public int updateByIdQuery(Configuration configuration) {
        return 0;
    }
}
