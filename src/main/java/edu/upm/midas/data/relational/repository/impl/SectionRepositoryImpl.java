package edu.upm.midas.data.relational.repository.impl;
import edu.upm.midas.data.relational.entities.edsssdb.Section;
import edu.upm.midas.data.relational.repository.AbstractDao;
import edu.upm.midas.data.relational.repository.SectionRepository;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SectionRepositoryImpl
 * @see
 */
@Repository("SectionRepositoryDao")
public class SectionRepositoryImpl extends AbstractDao<String, Section>
                                    implements SectionRepository {


    public Section findById(String sectionId) {
        Section section = getByKey(sectionId);
        return section;
    }

    @SuppressWarnings("unchecked")
    public Section findByIdQuery(String sectionId) {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findById")
                .setParameter("sectionId", sectionId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    public Section findByNameQuery(String sectionName) {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findByNameNativeResultClass")
                .setParameter("name", sectionName)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    public Section findByDescriptionQuery(String sectionDesc) {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findByDescription")
                .setParameter("description", sectionDesc)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    public Section findLastSectionQuery() {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findLastSectionNativeResultClass")
                .setMaxResults(1)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    public String findLastSectionIdQuery() {
        String sectionId = (String) getEntityManager()
                .createNamedQuery("Section.findLastIdNative")
                .setMaxResults(1)
                .getSingleResult();
        return sectionId;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Section findByIdNative(String sectionId) {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findByIdNative")
                .setParameter("sectionId", sectionId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Section findByIdNativeResultClass(String sectionId) {
        Section section = null;
        List<Section> sectionList = (List<Section>) getEntityManager()
                .createNamedQuery("Section.findByIdNativeResultClass")
                .setParameter("sectionId", sectionId)
                .getResultList();
        if (CollectionUtils.isNotEmpty(sectionList))
            section = sectionList.get(0);
        return section;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Section> findAllQuery() {
        return (List<Section>) getEntityManager()
                .createNamedQuery("Section.findAll")
                .setMaxResults(0)
                .getResultList();
    }

    public void persist(Section section) {
        super.persist(section);
    }

    @Override
    public int insertNative(String sectionId, String name, String description) {
        return getEntityManager()
                .createNamedQuery("Section.insertNative")
                .setParameter("sectionId", sectionId)
                .setParameter("name", name)
                .setParameter("description", description)
                .executeUpdate();
    }

    public boolean deleteById(String sectionId) {
        Section section = findById( sectionId );
        if(section ==null)
            return false;
        super.delete(section);
        return true;
    }

    public void delete(Section section) {
        super.delete(section);
    }

    public Section update(Section section) {
        return super.update(section);
    }

    @Override
    public int updateByIdQuery(Section section) {
        return 0;
    }
}
