package edu.upm.midas.data.validation.tvp.tvpApiResponse.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import edu.upm.midas.data.validation.tvp.client.TvpClient;
import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Response;
import edu.upm.midas.data.validation.tvp.tvpApiResponse.TvpResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpResourceServiceImpl
 * @see
 */
@Service
public class TvpResourceServiceImpl implements TvpResourceService {

    @Autowired
    @Lazy
    private TvpClient tvpClient;

    //@HystrixCommand(groupKey = "tp-notification-service", fallbackMethod = "notificationsAreDown")
    //@HystrixCommand(fallbackMethod = "retrieveFallback")
    public Response getValidateSymptoms(Request request) {
        return tvpClient.getValidateSymptoms( request );
    }

    /*public Response notificationsAreDown(Request request) {
        return tvpClient.getValidateSymptoms( request );
    }*/

    /*public Response retrieveFallback(Request request, Throwable ex){
        assert "filterTexts command failed".equals(ex.getMessage());
        throw new RuntimeException("retrieveFallback failedddddd" + ex.getMessage());
    }*/
}
