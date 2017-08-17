package edu.upm.midas.data.relational.repository;

import edu.upm.midas.data.relational.entities.edsssdb.HasSection;
import edu.upm.midas.data.relational.entities.edsssdb.HasSectionPK;

import java.util.Date;
import java.util.List;

/**
 * Created by gerardo on 23/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionRepository
 * @see
 */
public interface HasSectionRepository {

    HasSection findById(HasSectionPK hasSectionPK);

    HasSection findByIdQuery(HasSectionPK hasSectionPK);

    HasSection findByIdNative(HasSectionPK hasSectionPK);

    HasSection findByIdNativeResultClass(HasSectionPK hasSectionPK);

    List<HasSection> findAllQuery();

    void persist(HasSection hasSection);

    int insertNative(String documentId, Date date, String sectionId);

    boolean deleteById(HasSectionPK hasSectionPK);

    void delete(HasSection hasSection);

    HasSection update(HasSection hasSection);

    int updateByIdQuery(HasSection hasSection);
    
}
