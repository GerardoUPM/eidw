package edu.upm.midas.data.extraction.model;
import java.util.List;

/**
 * Created by gerardo on 03/11/2017.
 *
 * @author Gerardo Lagunes G. ${EMAIL}
 * @version ${<VERSION>}
 * @project eidw
 * @className ConfigurationDiseaseAlbum
 * @see
 */
public class ConfigurationDiseaseAlbum {

    private String albumId;
    private String version;
    private Integer numberDiseases;
    private String source;
    private List<String> requests;
    private String serviceCode;


    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Integer getNumberDiseases() {
        return numberDiseases;
    }

    public void setNumberDiseases(Integer numberDiseases) {
        this.numberDiseases = numberDiseases;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<String> getRequests() {
        return requests;
    }

    public void setRequests(List<String> requests) {
        this.requests = requests;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }
}
