package edu.upm.midas.data.relational.service;

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
 * @className HasSectionService
 * @see
 */
public interface HasSectionService {

    HasSection findById(HasSectionPK hasSectionPK);

    List<HasSection> findAll();

    void save(HasSection hasSection);

    int insertNative(String documentId, Date date, String sectionId);

    boolean updateFindFull(HasSection hasSection, HasSectionPK hasSectionPK);

    boolean updateFindPartial(HasSection hasSection, HasSectionPK hasSectionPK);

    boolean deleteById(HasSectionPK hasSectionPK);
    
}
