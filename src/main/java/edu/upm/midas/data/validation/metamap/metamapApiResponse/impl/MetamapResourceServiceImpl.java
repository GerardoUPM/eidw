package edu.upm.midas.data.validation.metamap.metamapApiResponse.impl;
//import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.upm.midas.data.validation.metamap.client.MetamapClient;
import edu.upm.midas.data.validation.metamap.metamapApiResponse.MetamapResourceService;
import edu.upm.midas.data.validation.metamap.model.request.Request;
import edu.upm.midas.data.validation.metamap.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 31/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className MetamapResourceServiceImpl
 * @see
 */
@Service
public class MetamapResourceServiceImpl implements MetamapResourceService {

    private MetamapClient metamapClient;

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    public MetamapResourceServiceImpl(MetamapClient metamapClient) {
        this.metamapClient = metamapClient;
    }

    @Override
    //@HystrixCommand(fallbackMethod = "retrieveFallback")
    public Response filterDiseaseName(Request request) {
        return metamapClient.filterTexts( request );
    }

    @Override
    //@HystrixCommand(fallbackMethod = "retrieveFallback")
    public Response filterTexts(Request request) {
        return metamapClient.filterTexts( request );
    }



    public Response retrieveFallback(Request request, Throwable ex){
        assert "filterTexts command failed".equals(ex.getMessage());
        throw new RuntimeException("retrieveFallback failedddddd" + ex.getMessage());
    }


}
