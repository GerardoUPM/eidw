package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Disease;
import edu.upm.midas.data.relational.repository.DiseaseRepository;
import edu.upm.midas.data.relational.service.DiseaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className DiseaseServiceImpl
 * @see
 */
@Service("diseaseService")
public class DiseaseServiceImpl implements DiseaseService {

    @Autowired
    private DiseaseRepository daoDisease;


    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Disease findById(String diseaseId) {
        Disease disease = daoDisease.findById(diseaseId);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return disease;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Disease findByName(String diseaseName) {
        Disease disease = daoDisease.findByNameQuery(diseaseName);
/*
        if(source!=null)
            Hibernate.initialize(source.getVersionList());
*/
        return disease;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Disease findByCui(String cui) {
        Disease disease = daoDisease.findByCuiQuery(cui);
/*
        if(source!=null)
            Hibernate.initialize(source.getVersionList());
*/
        return disease;
    }

    @Override
    public Disease findLastDiseaseQuery() {
        return daoDisease.findLastDiseaseQuery();
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Disease> findAll() {
        List<Disease> listDiseaseEntities = daoDisease.findAllQuery();
        return listDiseaseEntities;
    }

    @Override
    public List<Object[]> findAllBySourceAndVersionNative(String sourceName, Date version) {
        return daoDisease.findAllBySourceAndVersionNative(sourceName, version);
    }

    @Override
    public Object[] findByIdAndSourceAndVersionNative(String diseaseId, String sourceName, Date version) {
        return daoDisease.findByIdAndSourceAndVersionNative(diseaseId, sourceName, version);
    }

    @Override
    public Object[] findByCuiAndSourceAndVersionNative(String cui, String sourceName, Date version) {
        return daoDisease.findByCuiAndSourceAndVersionNative(cui, sourceName, version);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(Disease disease) {
        daoDisease.persist(disease);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNative(String diseaseId, String name, String cui) {
        return daoDisease.insertNative( diseaseId, name, cui);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public int insertNativeHasDisease(String documentId, Date date, String diseaseId) {
        return daoDisease.insertNativeHasDisease( documentId, date, diseaseId );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(Disease disease) {
        Disease dis = daoDisease.findById(disease.getDiseaseId());
        if(dis!=null){
            dis.setDiseaseId(disease.getDiseaseId());
            dis.setName(disease.getName());
            dis.setCui(disease.getCui());
            //sour.getDiseasesBySidsource().clear();
            //sour.getDiseasesBySidsource().addAll(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource())?source.getDiseasesBySidsource():new ArrayList<Disease>());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(Disease disease) {
        Disease dis = daoDisease.findByNameQuery(disease.getName());
        if(dis!=null){
/*
            if(StringUtils.isNotBlank(disease.getDocumentId()))
                dis.setDocumentId(disease.getDocumentId());
            if(StringUtils.isNotBlank(disease.getName()))
                dis.setName(disease.getName());
*/

            //dis.setDocumentList( disease.getDocumentList() );
/*
            if(StringUtils.isNotBlank(disease.getCui()))
                dis.setCui(disease.getCui());
*/
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(String diseaseId) {
        Disease disease = daoDisease.findById(diseaseId);
        if(disease !=null)
            daoDisease.delete(disease);
        else
            return false;
        return true;
    }

    @Override
    public int updateCuiByIdAndSourceAndVersionNative(String diseaseId, String cui, String sourceName, Date version) {
        return daoDisease.updateCuiByIdAndSourceAndVersionNative(diseaseId, cui, sourceName, version);
    }
}
