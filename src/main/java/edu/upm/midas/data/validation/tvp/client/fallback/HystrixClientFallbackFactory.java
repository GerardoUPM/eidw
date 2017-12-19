package edu.upm.midas.data.validation.tvp.client.fallback;

import edu.upm.midas.data.validation.tvp.client.TvpClient;
import edu.upm.midas.data.validation.tvp.model.request.Request;
import edu.upm.midas.data.validation.tvp.model.response.Response;
import feign.hystrix.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/*@Component
public class HystrixClientFallbackFactory implements FallbackFactory<TvpClient> {
    @Override
    public TvpClient create(Throwable cause) {
        return new TvpClient(){
            @Override
            public Response getValidateSymptoms(Request request) {
                System.out.println("Fallback cause: " + cause.getMessage() + HttpStatus.OK);
                return new Response();
            }

            *//*@Override
            public ResponseEntity populateCloudant() {
                return new ResponseEntity("Fallback cause: " + cause.getMessage() + "\n", HttpStatus.OK);
            }*//*
        };
    }
}*/
