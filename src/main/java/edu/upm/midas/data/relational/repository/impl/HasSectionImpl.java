package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.HasSection;
import edu.upm.midas.data.relational.entities.edsssdb.HasSectionPK;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.HasSectionRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionImpl
 * @see
 */
@Repository("HasSectionRepositoryDao")
public class HasSectionImpl extends AbstractDao<HasSectionPK, HasSection> implements HasSectionRepository {



    public HasSection findById(HasSectionPK hasSectionPK) {
        HasSection hasSection = getByKey(hasSectionPK);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    public HasSection findByIdQuery(HasSectionPK hasSectionPK) {
        HasSection hasSection = null;
        List<HasSection> hasSectionList = (List<HasSection>) getEntityManager()
                .createNamedQuery("HasSection.findByIdNativeResultClass")
                .setParameter("documentId", hasSectionPK.getDocumentId())
                .setParameter("date", hasSectionPK.getDate())
                .setParameter("sectionId", hasSectionPK.getSectionId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSectionList))
            hasSection = hasSectionList.get(0);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    public HasSection findByIdNative(HasSectionPK hasSectionPK) {
        HasSection hasSection = null;
        List<HasSection> hasSectionList = (List<HasSection>) getEntityManager()
                .createNamedQuery("HasSection.findByIdNativeResultClass")
                .setParameter("documentId", hasSectionPK.getDocumentId())
                .setParameter("date", hasSectionPK.getDate())
                .setParameter("sectionId", hasSectionPK.getSectionId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSectionList))
            hasSection = hasSectionList.get(0);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public HasSection findByIdNativeResultClass(HasSectionPK hasSectionPK) {
        HasSection hasSection = null;
        List<HasSection> hasSectionList = (List<HasSection>) getEntityManager()
                .createNamedQuery("HasSection.findByIdNativeResultClass")
                .setParameter("documentId", hasSectionPK.getDocumentId())
                .setParameter("date", hasSectionPK.getDate())
                .setParameter("sectionId", hasSectionPK.getSectionId())
                .getResultList();
        if (CollectionUtils.isNotEmpty(hasSectionList))
            hasSection = hasSectionList.get(0);
        return hasSection;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<HasSection> findAllQuery() {
        return (List<HasSection>) getEntityManager()
                .createNamedQuery("HasSection.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(HasSection hasSection) {
        super.persist(hasSection);
    }

    @Override
    public int insertNative(String documentId, Date date, String sectionId) {
        return getEntityManager()
                .createNamedQuery("HasSection.insertNative")
                .setParameter("documentId", documentId)
                .setParameter("date", date)
                .setParameter("sectionId", sectionId)
                .executeUpdate();
    }

    public boolean deleteById(HasSectionPK hasSectionPK) {
        HasSection hasSection = findById( hasSectionPK );
        if(hasSection ==null)
            return false;
        super.delete(hasSection);
        return true;
    }

    public void delete(HasSection hasSection) {
        super.delete(hasSection);
    }

    public HasSection update(HasSection hasSection) {
        return super.update(hasSection);
    }

    @Override
    public int updateByIdQuery(HasSection hasSection) {
        return 0;
    }
}
