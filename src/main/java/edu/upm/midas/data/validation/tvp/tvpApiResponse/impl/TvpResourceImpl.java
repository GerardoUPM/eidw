package edu.upm.midas.data.validation.tvp.tvpApiResponse.impl;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import edu.upm.midas.data.validation.tvp.tvpApiResponse.TvpResource;
import feign.Feign;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by gerardo on 08/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpResourceImpl
 * @see
 */
@Component
public class TvpResourceImpl {

    @Value("${URI.TVP.API}")
    private String URI_TVP_API;


    public List<MatchNLP> getValidateSymptoms(List<Concept> concepts) {
        TvpResource tvpResource = Feign.builder().encoder(new JacksonEncoder()).decoder(new JacksonDecoder()).target(TvpResource.class, URI_TVP_API);
        return tvpResource.getValidateSymptoms( concepts );
    }
}
