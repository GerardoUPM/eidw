package edu.upm.midas.data.relational.service.helperNative;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.Section;
import edu.upm.midas.data.relational.service.HasSectionService;
import edu.upm.midas.data.relational.service.SectionService;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by gerardo on 26/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className HasSectionHelper
 * @see
 */
@Service
public class HasSectionHelperNative {


    @Autowired
    private HasSectionService hasSectionService;
    @Autowired
    private SectionService sectionService;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(HasSectionHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;


    public String insert(String documentId, Date version, Section section){
        edu.upm.midas.data.relational.entities.edsssdb.Section sectionEntity = sectionService.findByName( section.getName() );
        hasSectionService.insertNative( documentId, version, sectionEntity.getSectionId() );
        return sectionEntity.getSectionId();
    }





}
