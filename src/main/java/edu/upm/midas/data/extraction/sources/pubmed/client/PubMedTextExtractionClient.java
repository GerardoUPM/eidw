package edu.upm.midas.data.extraction.sources.pubmed.client;

import edu.upm.midas.configuration.FeignConfiguration;
import edu.upm.midas.data.extraction.sources.pubmed.client.fallback.PubMedTextExtractionClientFallback;
import edu.upm.midas.data.extraction.sources.pubmed.model.Request;
import edu.upm.midas.data.extraction.sources.pubmed.model.Response;
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
 * @className TvpClient
 * @see
 */

@FeignClient(name = "${my.service.client.pmte.name}",
        url = "${my.service.client.pmte.url}",
        fallback = PubMedTextExtractionClientFallback.class,
        configuration = FeignConfiguration.class)
public interface PubMedTextExtractionClient {

    @RequestMapping(value = "${my.service.client.pmte.texts.path}", method = RequestMethod.POST)
    Response getPubMedTexts(@RequestBody Request request);

    @RequestMapping(value = "${my.service.client.pmte.texts.json.path}", method = RequestMethod.POST)
    Response getPubMedTextsByJSON(@RequestBody Request request);

}
