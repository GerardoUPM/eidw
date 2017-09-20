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

    private Configuration configuration;
    private String validationMesssage;
    private List<Text> textList;


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
