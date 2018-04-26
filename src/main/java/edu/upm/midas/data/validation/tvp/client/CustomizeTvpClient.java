package edu.upm.midas.data.validation.tvp.client;

import edu.upm.midas.configuration.FeignConfiguration;
import edu.upm.midas.data.validation.tvp.client.fallback.TvpClientFallback;
import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Response;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

//import edu.upm.midas.data.validation.tvp.client.fallback.TvpClientFallback;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className CustomizeTvpClient
 * @seelocalhost:11062 http://disnet.ctb.upm.es http://localhost:8088 http://localhost:11062
 */
@FeignClient(name = "tvp-client",
        url = "http://localhost:8080/api",//url = "138.4.130.6:11062/api",
        fallback = TvpClientFallback.class,
        configuration = FeignConfiguration.class)
public interface CustomizeTvpClient {

    @RequestMapping(value = "/tvp/validate", method = RequestMethod.POST)
    //@Headers("token: eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJncmFyZG9sYWdhckBob3RtYWlsLmNvbSIsImF1ZCI6IndlYiIsIm5hbWUiOiJHZXJhcmRvIExhZ3VuZXMiLCJ1c2VyIjp0cnVlLCJpYXQiOjE1MDY0MzIwNjQsInNlY3JldF9jbGFpbSI6IlBlcnRlciBQYXJrZXIifQ.mC-hTx9a6vRX8-2QlP1W4vjnBiVk2D9ySnhasz7az12gZ_wx7u4gw20V1VC41zYydGBbX_A7MVJ0uGmzWwIRWQ")
    Response getValidateSymptoms(@RequestBody Request request);

}
