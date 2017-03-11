package es.urjc.code.daw.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\Users\\Javi\\Documents\\GitHub\\DAW-2017\\CuantoMeme2.0\\bbdd_ejem6_mio\\target\\classes\\static\\imgVinetas";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
