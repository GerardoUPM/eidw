package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSection;
import edu.upm.midas.data.relational.entities.edsssdb.HasSectionPK;
import edu.upm.midas.data.relational.repository.HasSectionRepository;
import edu.upm.midas.data.relational.service.HasSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionServiceImpl
 * @see
 */
@Service("hasSectionService")
public class HasSectionServiceImpl implements HasSectionService {

    @Autowired
    private HasSectionRepository daoHasSection;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public HasSection findById(HasSectionPK hasSectionPK) {
        HasSection hasSection = daoHasSection.findById(hasSectionPK);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return hasSection;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<HasSection> findAll() {
        List<HasSection> hasSectionList = daoHasSection.findAllQuery();
        return hasSectionList;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(HasSection hasSection) {
        daoHasSection.persist(hasSection);
    }

    @Override
    public int insertNative(String documentId, Date date, String sectionId) {
        return daoHasSection.insertNative( documentId, date, sectionId);
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(HasSection hasSection, HasSectionPK hasSectionPK) {
        HasSection hs = daoHasSection.findById( hasSectionPK );
        if(hs!=null){
                //==>hs.setHasSectionPK(hasSection.getHasSectionPK());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(HasSection hasSection, HasSectionPK hasSectionPK) {
        HasSection hs = daoHasSection.findById( hasSectionPK );
        if(hs!=null){
            //==>if(hasSection.getHasSectionPK()!=null)
            //==>    hs.setHasSectionPK(hasSection.getHasSectionPK());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(HasSectionPK hasSectionPK) {
        HasSection hasSection = daoHasSection.findById(hasSectionPK);
        if(hasSection !=null)
            daoHasSection.delete(hasSection);
        else
            return false;
        return true;
    }
}
