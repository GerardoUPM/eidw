package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasText;
import edu.upm.midas.data.relational.entities.edsssdb.HasTextPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.HasTextRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 14/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasTextRepositoryImpl
 * @see
 */
@Repository("HasTextRepositoryDao")
public class HasTextRepositoryImpl extends AbstractDao<HasTextPK, HasText>
                                    implements HasTextRepository{

    public HasText findById(HasTextPK hasTextPK) {
        HasText hasText = getByKey(hasTextPK);
        return hasText;
    }

    @SuppressWarnings("unchecked")
    public HasText findByIdQuery(HasTextPK hasTextPK) {
        HasText hasText = null;
        List<HasText> hasTextList = (List<HasText>) getEntityManager()
                .createNamedQuery("HasText.findById")
                .setParameter("documentId", hasTextPK.getDocumentId())
                .setParameter("date", hasTextPK.getDate())
                .setParameter("sectionId", hasTextPK.getSectionId())
                .setParameter("textId", hasTextPK.getTextId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasTextList))
            hasText = hasTextList.get(0);
        return hasText;
    }

    @SuppressWarnings("unchecked")
    public HasText findByTextOrderQuery(int textOrder) {
        HasText hasText = null;
        List<HasText> hasTextList = (List<HasText>) getEntityManager()
                .createNamedQuery("HasText.findByTextOrder")
                .setParameter("textOrder", textOrder)
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasTextList))
            hasText = hasTextList.get(0);
        return hasText;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasText findByIdNative(HasTextPK hasTextPK) {
        HasText hasText = null;
        List<HasText> hasTextList = (List<HasText>) getEntityManager()
                .createNamedQuery("HasText.findByIdNative")
                .setParameter("documentId", hasTextPK.getDocumentId())
                .setParameter("date", hasTextPK.getDate())
                .setParameter("sectionId", hasTextPK.getSectionId())
                .setParameter("textId", hasTextPK.getTextId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasTextList))
            hasText = hasTextList.get(0);
        return hasText;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasText findByIdNativeResultClass(HasTextPK hasTextPK) {
        HasText hasText = null;
        List<HasText> hasTextList = (List<HasText>) getEntityManager()
                .createNamedQuery("HasText.findByIdNativeResultClass")
                .setParameter("documentId", hasTextPK.getDocumentId())
                .setParameter("date", hasTextPK.getDate())
                .setParameter("sectionId", hasTextPK.getSectionId())
                .setParameter("textId", hasTextPK.getTextId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasTextList))
            hasText = hasTextList.get(0);
        return hasText;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HasText> findAllQuery() {
        return (List<HasText>) getEntityManager()
                .createNamedQuery("HasText.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(HasText hasText) {
        super.persist(hasText);
    }

    @Override
    public int insertNative(String documentId, Date date, String sectionId, String textId, int textOrder) {
        return getEntityManager()
                .createNamedQuery("HasText.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("sectionId", sectionId)
                .setParameter("textId", textId)
                .setParameter("textOrder", textOrder)
                .executeUpdate();
    }

    public boolean deleteById(HasTextPK hasTextPK) {
        HasText hasText = findById( hasTextPK );
        if(hasText ==null)
            return false;
        super.delete(hasText);
        return true;
    }

    public void delete(HasText hasText) {
        super.delete(hasText);
    }

    public HasText update(HasText hasText) {
        return super.update(hasText);
    }

    @Override
    public int updateByIdQuery(HasText hasText) {
        return getEntityManager()
                .createNamedQuery("HasText.updateById")
                .setParameter("documentId", hasText.getDocumentId())
                .setParameter("date", hasText.getDate())
                .setParameter("sectionId", hasText.getSectionId())
                .setParameter("textId", hasText.getTextId())
                .executeUpdate();
    }
}
