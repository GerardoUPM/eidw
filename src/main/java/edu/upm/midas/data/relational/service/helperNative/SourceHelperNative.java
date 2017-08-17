package edu.upm.midas.data.relational.service.helperNative;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.upm.midas.data.extraction.model.Source;
import edu.upm.midas.data.relational.service.SourceService;
import edu.upm.midas.utilsservice.Common;
import edu.upm.midas.utilsservice.UniqueId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 12/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className SourceHelper
 * @see
 */
@Service
public class SourceHelperNative {

    @Autowired
    private SourceService sourceService;
    @Autowired
    private UrlHelperNative urlHelperNative;
    @Autowired
    private UniqueId uniqueId;
    @Autowired
    private Common common;

    private static final Logger logger = LoggerFactory.getLogger(SourceHelperNative.class);
    @Autowired
    ObjectMapper objectMapper;



    /**
     * @param source
     * @return
     * @throws Exception
     */
    public String insertIfExist(Source source) throws Exception{

        edu.upm.midas.data.relational.entities.edsssdb.Source sourceEntity = sourceService.findByName( source.getName() );

        if ( sourceEntity != null ){
            return sourceEntity.getSourceId();
        }else{
            String sourceId = getLastId();
            sourceService.insertNative( sourceId, source.getName() );
            String urlId = urlHelperNative.getUrl( source.getUrl(), sourceId );
            if ( !urlId.equals("") ) sourceService.insertNativeUrl( sourceId, urlId );

            return sourceId;
        }
    }


    /**
     * @param nameSource
     * @return
     */
    public boolean exist(String nameSource){
        edu.upm.midas.data.relational.entities.edsssdb.Source source = sourceService.findByName( nameSource );
        if( source != null )
            return true;
        else
            return false;
    }


    /**
     * @return
     */
    public String getLastId(){

        if (sourceService.findAll().size() > 0){
            String sourceId = sourceService.findLastSourceIdQuery();

            int last = Integer.parseInt( common.cutStringPerformance(2, 0, sourceId ) );

            return uniqueId.generateSource( last + 1 );

        }else{
            return uniqueId.generateSource( 1 );
        }

    }

}
