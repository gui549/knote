package learnk8s.io.knotejava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

public class KnoteProperties {

    private String uploadDir = "/opt/uploads/";

    public String getUploadDir() {
        return uploadDir;
    }
}
