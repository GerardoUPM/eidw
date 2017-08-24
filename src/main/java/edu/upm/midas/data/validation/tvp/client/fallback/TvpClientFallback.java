package edu.upm.midas.data.validation.tvp.client.fallback;
import edu.upm.midas.data.validation.tvp.client.TvpClient;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import edu.upm.midas.data.validation.tvp.model.response.MatchNLP;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className TvpClientFallback
 * @see
 */
@Component
public class TvpClientFallback implements TvpClient {


    @Override
    public List<MatchNLP> getValidateSymptoms(List<Concept> concepts) {
        return new ArrayList<MatchNLP>();
    }
}

