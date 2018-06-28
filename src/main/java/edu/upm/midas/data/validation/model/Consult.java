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
    // snapshot: 1) fecha específica (ej. "2017-06-15"); 2) "last" (última versión)
    private String snapshot;
    private Date date;

    public Consult(String source, String snapshot) throws ParseException {
        this.source = source;
        this.snapshot = snapshot;
        if ( !this.snapshot.equals( Constants.CONSULT_LAST_SNAPSHOT) ){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            this.date = sdf.parse(snapshot);
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

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
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
                ", snapshot='" + snapshot + '\'' +
                ", date=" + date +
                '}';
    }
}
