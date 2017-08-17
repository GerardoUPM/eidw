package edu.upm.midas.data.extraction.service;

import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.extraction.xml.model.XmlSource;
import edu.upm.midas.data.extraction.xml.service.ReadXml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;


/**
 * Created by gerardo on 28/3/17.
 * @project ExtractionInformationWikipedia
 * @version ${<VERSION>}
 * @author Gerardo Lagunes G.
 * @className LoadSource
 * @see
 */
/*HACER QUE COJA LA RUTA DESDE EL PROPERTIES*/
@Component
public class LoadSource {

    @Autowired
    private Environment env;
    @Autowired
    private ReadXml oReadXml;

    public List<XmlSource> loadSources() throws Exception {

        File xmlFile = new File( Constants.XML_SOURCE_FILE );
        oReadXml.file( xmlFile );

        return oReadXml.read();

    }

}
