package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Section;
import edu.upm.midas.data.relational.repository.SectionRepository;
import edu.upm.midas.data.relational.service.SectionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SectionServiceImpl
 * @see
 */
@Service("sectionService")
public class SectionServiceImpl implements SectionService {

    @Autowired
    private SectionRepository daoSection;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Section findById(String sectionId) {
        Section section = daoSection.findById(sectionId);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return section;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Section findByName(String sectionName) {
        Section section = daoSection.findByNameQuery(sectionName);
/*
        if(source!=null)
            Hibernate.initialize(source.getVersionList());
*/
        return section;
    }

    @Override
    public Section findByDescriptionQuery(String sectionDesc) {
        Section section = daoSection.findByDescriptionQuery(sectionDesc);
        return section;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public Section findLastSectionQuery() {
        return daoSection.findLastSectionQuery();
    }

    @Override
    public String findLastSectionIdQuery() {
        return daoSection.findLastSectionIdQuery();
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<Section> findAll() {
        List<Section> listSectionEntities = daoSection.findAllQuery();
        return listSectionEntities;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public void save(Section section) {
        daoSection.persist(section);
    }

    @Override
    public int insertNative(String sectionId, String name, String description) {
        return daoSection.insertNative( sectionId, name, description );
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public boolean updateFindFull(Section section) {
        Section sec = daoSection.findById(section.getSectionId());
        if(sec!=null){
            if(StringUtils.isNotBlank(section.getSectionId()))
                sec.setSectionId(section.getSectionId());
            if(StringUtils.isNotBlank(section.getName()))
                sec.setName(section.getName());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public boolean updateFindPartial(Section section) {
        Section sec = daoSection.findByDescriptionQuery(section.getDescription());
        if(sec!=null){
            if(StringUtils.isNotBlank(section.getSectionId()))
                sec.setSectionId(section.getSectionId());
            if(StringUtils.isNotBlank(section.getName()))
                sec.setName(section.getName());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public boolean deleteById(String sectionId) {
        Section section = daoSection.findById(sectionId);
        if(section !=null)
            daoSection.delete(section);
        else
            return false;
        return true;
    }
}
