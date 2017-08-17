package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Resource;
import edu.upm.midas.data.relational.repository.ResourceRepository;
import edu.upm.midas.data.relational.service.ResourceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 09/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className ResourceServiceImpl
 * @see
 */
@Service("resourceService")
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private ResourceRepository daoResource;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Resource findById(Integer resourceId) {
        Resource resource = daoResource.findById(resourceId);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return resource;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Resource findByName(String sourceName) {
        Resource resource = daoResource.findByNameQuery(sourceName);
        return resource;
    }

    @Override
    public int findIdByNameQuery(String resourceName) {
        return daoResource.findIdByNameQuery( resourceName );
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Resource> findAll() {
        List<Resource> resourceEntityList = daoResource.findAllQuery();
        return resourceEntityList;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Resource resource) {
        daoResource.persist(resource);
    }

    @Override
    public int insertNative(int resourceId, String name) {
        return daoResource.insertNative( resourceId, name );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Resource resource) {
        Resource reso = daoResource.findById(resource.getResourceId());
        if(reso!=null){
            reso.setResourceId(resource.getResourceId());
            reso.setName(resource.getName());
            //sour.getDiseasesBySidsource().clear();
            //sour.getDiseasesBySidsource().addAll(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource())?source.getDiseasesBySidsource():new ArrayList<Disease>());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Resource resource) {
        Resource reso = daoResource.findById(resource.getResourceId());
        if(reso!=null){
            if(resource.getResourceId() > 0)
                reso.setResourceId(resource.getResourceId());
            if(StringUtils.isNotBlank(resource.getName()))
                reso.setName(resource.getName());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(Integer resourceId) {
        Resource resource = daoResource.findById(resourceId);
        if(resource !=null)
            daoResource.delete(resource);
        else
            return false;
        return true;
    }
}
