package edu.upm.midas.data.extraction.album.model.request;
import edu.upm.midas.constants.Constants;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by gerardo on 02/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project get_diseases_list_rest
 * @className RequestFather
 * @see
 */
public class RequestFather {

    @Valid
    @NotNull(message = Constants.ERR_NO_PARAMETER)
    @Size(min = 1, message = Constants.ERR_EMPTY_PARAMETER)
    private String token;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
