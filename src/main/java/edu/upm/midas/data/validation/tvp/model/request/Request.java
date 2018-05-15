package edu.upm.midas.data.validation.tvp.model.request;
import edu.upm.midas.constants.Constants;
import edu.upm.midas.data.validation.tvp.model.response.Concept;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by gerardo on 26/09/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project tvp_rest
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
    @NotEmpty(message = Constants.ERR_EMPTY_PARAMETER)
    private List<Concept> concepts;
    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    @NotEmpty(message = Constants.ERR_EMPTY_PARAMETER)
    private String source;
    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    @NotEmpty(message = Constants.ERR_EMPTY_PARAMETER)
    private String snapshot;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Concept> getConcepts() {
        return concepts;
    }

    public void setConcepts(List<Concept> concepts) {
        this.concepts = concepts;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
                ", source='" + source + '\'' +
                ", snapshot='" + snapshot + '\'' +
                '}';
    }
}
