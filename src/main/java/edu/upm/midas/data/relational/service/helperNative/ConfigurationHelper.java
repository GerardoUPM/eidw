package edu.upm.midas.data.relational.service.helperNative;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.relational.service.ConfigurationService;
import edu.upm.midas.data.relational.service.SourceService;
import edu.upm.midas.utilsservice.UniqueId;
import edu.upm.midas.utilsservice.UtilDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by gerardo on 03/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className ConfigurationHelper
 * @see
 */
@Component
public class ConfigurationHelper {

    @Autowired
    private ConfigurationService confService;
    @Autowired
    private SourceService sourceService;

    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Constants constants;
    @Autowired
    private UtilDate utilDate;


    public void insert(String source, Date version, String tool, String json){
        String configurationId = uniqueId.generateConfiguration(source, utilDate.dateFormatyyyMMdd(version));
        //System.out.println(source +" | " + version +" | "+ tool +" | "+ json);
//        String sourceId = (source.equals(Constants.SOURCE_WIKIPEDIA)?Constants.SOURCE_WIKIPEDIA_CODE:Constants.SOURCE_PUBMED);
        String sourceId = sourceService.findByNameNative(source);
        confService.insertNative(configurationId, sourceId, version, tool, json);
        System.out.println("Insert configuration ready!...");
    }


    public void insert(String source, String sourceId, Date version, String tool, String json){
        String configurationId = uniqueId.generateConfiguration(source, utilDate.dateFormatyyyMMdd(version));
        //System.out.println(source +" | " + sourceId +" | " + version +" | "+ tool +" | "+ json);
        confService.insertNative(configurationId, sourceId, version, tool, json);
        System.out.println("Insert configuration ready!...");
    }
}
