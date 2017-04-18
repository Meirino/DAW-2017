package es.urjc.code.daw.storage;

import java.io.File;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
	File currentDir = new File("");
	//System.out.println(currentDir.getAbsoluteFile()+"photos");
    private String location = currentDir.getAbsoluteFile()+"/target/classes/static/imgs/";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}
