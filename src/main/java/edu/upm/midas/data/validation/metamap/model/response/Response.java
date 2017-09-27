package edu.upm.midas.data.validation.metamap.model.response;


import edu.upm.midas.data.validation.metamap.model.receiver.Configuration;

import java.util.List;

/**
 * Created by gerardo on 14/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project metamap_rest
 * @className Response
 * @see
 */
public class Response {

    private String token;
    private boolean authorization;
    private String authorizationMessage;

    private Configuration configuration;
    private String validationMesssage;
    private List<Text> textList;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isAuthorization() {
        return authorization;
    }

    public void setAuthorization(boolean authorization) {
        this.authorization = authorization;
    }

    public String getAuthorizationMessage() {
        return authorizationMessage;
    }

    public void setAuthorizationMessage(String authorizationMessage) {
        this.authorizationMessage = authorizationMessage;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getValidationMesssage() {
        return validationMesssage;
    }

    public void setValidationMesssage(String validationMesssage) {
        this.validationMesssage = validationMesssage;
    }

    public List<Text> getTextList() {
        return textList;
    }

    public void setTextList(List<Text> textList) {
        this.textList = textList;
    }
}
