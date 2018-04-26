package edu.upm.midas.data.validation.metamap.model.receiver;
import edu.upm.midas.constants.Constants;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by gerardo on 25/07/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project metamap_rest
 * @className Request
 * @see
 */
public class Request {

    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    @NotEmpty(message = Constants.ERR_EMPTY_PARAMETER)
    private String token;
    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    private Configuration configuration;
    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    @Size(min = 1, message = Constants.ERR_EMPTY_PARAMETER)
    private List<Text> textList;
    private String snapshot;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public List<Text> getTextList() {
        return textList;
    }

    public void setTextList(List<Text> textList) {
        this.textList = textList;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    @Override
    public String toString() {
        return "Request{" +
                "token='" + token + '\'' +
                ", configuration=" + configuration +
                ", textList=" + textList +
                ", snapshot='" + snapshot + '\'' +
                '}';
    }
}
