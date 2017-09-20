package edu.upm.midas.data.validation.metamap.client;

import edu.upm.midas.data.validation.metamap.client.configuration.FeignMetamapConfiguration;
import edu.upm.midas.data.validation.metamap.client.fallback.MetamapClientFallback;
import edu.upm.midas.data.validation.metamap.model.receiver.Request;
import edu.upm.midas.data.validation.metamap.model.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className MetamapClient
 * @see
 */

@FeignClient(name = "metamap-client",
        url = "http://localhost:8084/metamap/api",
        fallback = MetamapClientFallback.class,
        configuration = FeignMetamapConfiguration.class)
public interface MetamapClient {

    @RequestMapping(value = "/concepts-found", method = RequestMethod.POST)
    Response filterTexts(@RequestBody Request request);

}
