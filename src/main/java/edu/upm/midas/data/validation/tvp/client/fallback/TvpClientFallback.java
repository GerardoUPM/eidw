package edu.upm.midas.data.validation.tvp.client.fallback;
import edu.upm.midas.data.validation.tvp.client.TvpClient;
import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Response;
import org.springframework.stereotype.Component;

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


    public Response getValidateSymptoms(Request request)  {
        System.out.println("SUCEDE ALGO?");
        return new Response();
    }

}

