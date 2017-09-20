package edu.upm.midas.data.validation.metamap.client.fallback;
import edu.upm.midas.data.validation.metamap.client.MetamapClient;
import edu.upm.midas.data.validation.metamap.model.receiver.Request;
import edu.upm.midas.data.validation.metamap.model.response.Response;
import org.springframework.stereotype.Component;

/**
 * Created by gerardo on 17/08/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className MetamapClientFallback
 * @see
 */
@Component
public class MetamapClientFallback implements MetamapClient {

    @Override
    public Response filterTexts(Request request) {
        return new Response();
    }

}

