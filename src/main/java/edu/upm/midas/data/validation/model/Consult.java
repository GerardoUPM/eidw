package edu.upm.midas.data.validation.model;
import edu.upm.midas.constants.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gerardo on 16/06/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project edu.upm.midas
 * @className Consult
 * @see
 */
public class Consult {

    // source: 1) nombre del source (ej. "wikipedia"); 2) "all" (todos los source)
    private String source;
    // version: 1) fecha específica (ej. "2017-06-15"); 2) "last" (última versión)
    private String version;
    private Date date;

    public Consult(String source, String version) throws ParseException {
        this.source = source;
        this.version = version;
        if ( !this.version.equals( Constants.CONSULT_VERSION_LAST ) ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.date = sdf.parse( version );
        }else {
            this.date = null;
        }
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Consult{" +
                "source='" + source + '\'' +
                ", version='" + version + '\'' +
                ", date=" + date +
                '}';
    }
}
