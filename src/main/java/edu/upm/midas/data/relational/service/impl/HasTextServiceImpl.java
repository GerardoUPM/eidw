package edu.upm.midas.data.relational.service.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasText;
import edu.upm.midas.data.relational.entities.edsssdb.HasTextPK;
import edu.upm.midas.data.relational.repository.HasTextRepository;
import edu.upm.midas.data.relational.service.HasTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasTextServiceImpl
 * @see
 */
@Service("hasTextService")
public class HasTextServiceImpl implements HasTextService {


    @Autowired
    private HasTextRepository daoHasText;

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public HasText findById(HasTextPK hasTextPK) {
        HasText ht = daoHasText.findById(hasTextPK);
        //if(source!=null)
        //Hibernate.initialize(source.getDiseasesBySidsource());
        return ht;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public HasText findByTextOrderQuery(int textOrder) {
        HasText ht = daoHasText.findByTextOrderQuery(textOrder);
/*
        if(source!=null)
            Hibernate.initialize(source.getVersionList());
*/
        return ht;
    }

    @Transactional(propagation= Propagation.REQUIRED,readOnly=true)
    public List<HasText> findAll() {
        List<HasText> listHasTextEntities = daoHasText.findAllQuery();
        return listHasTextEntities;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void save(HasText hasText) {
        daoHasText.persist(hasText);
    }

    @Override
    public int insertNative(String documentId, Date date, String sectionId, String textId, int textOrder) {
        return daoHasText.insertNative( documentId, date, sectionId, textId, textOrder );
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindFull(HasText hasText, HasTextPK hasTextPK) {
        HasText ht = daoHasText.findById( hasTextPK );
        if(ht!=null){
            //==>if(hasText.getHasTextPK()!=null)
            //==>    ht.setHasTextPK(hasText.getHasTextPK());
            //==>if(hasText.getTextOrder()>0)
            //==>    ht.setTextOrder(hasText.getTextOrder());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean updateFindPartial(HasText hasText, HasTextPK hasTextPK) {
        HasText ht = daoHasText.findById( hasTextPK );
        if(ht!=null){
            //==>if(hasText.getHasTextPK()!=null)
            //==>    ht.setHasTextPK(hasText.getHasTextPK());
            //==>if(hasText.getTextOrder()>0)
            //==>    ht.setTextOrder(hasText.getTextOrder());
            //if(CollectionUtils.isNotEmpty(source.getDiseasesBySidsource()))
            //    sour.setDiseasesBySidsource(source.getDiseasesBySidsource());
        }else
            return false;
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean deleteById(HasTextPK hasTextPK) {
        HasText hasText = daoHasText.findById(hasTextPK);
        if(hasText !=null)
            daoHasText.delete(hasText);
        else
            return false;
        return true;
    }
}
