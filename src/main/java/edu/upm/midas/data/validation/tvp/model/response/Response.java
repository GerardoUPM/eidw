package edu.upm.midas.data.validation.tvp.model.response;

import java.util.List;

/**
 * Created by gerardo on 26/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project tvp_rest
 * @className Response
 * @see
 */
public class Response {

    private String token;
    private boolean authorized;
    private String authorizationMessage;
    List<MatchNLP> validatedConcepts;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthorized(boolean authorized) {
        this.authorized = authorized;
    }

    public String getAuthorizationMessage() {
        return authorizationMessage;
    }

    public void setAuthorizationMessage(String authorizationMessage) {
        this.authorizationMessage = authorizationMessage;
    }

    public List<MatchNLP> getValidatedConcepts() {
        return validatedConcepts;
    }

    public void setValidatedConcepts(List<MatchNLP> validatedConcepts) {
        this.validatedConcepts = validatedConcepts;
    }
}
