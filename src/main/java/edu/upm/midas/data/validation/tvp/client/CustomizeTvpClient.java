package edu.upm.midas.data.validation.tvp.client;

import edu.upm.midas.data.validation.tvp.client.configuration.FeignTvpConfiguration;
import edu.upm.midas.data.validation.tvp.client.fallback.TvpClientFallback;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className CustomizeTvpClient
 * @see
 */
@FeignClient(name = "tvp-client",
        url = "http://localhost:8083/tvp/api",
        fallback = TvpClientFallback.class,
        configuration = FeignTvpConfiguration.class)
public interface CustomizeTvpClient {

    @RequestMapping(value = "/concepts-validated", method = RequestMethod.POST)
    List<MatchNLP> getValidateSymptoms(@RequestBody List<Concept> concepts);

}
