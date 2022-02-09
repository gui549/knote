package learnk8s.io.knotejava;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "knote")
public class KnoteProperties {
    @Value("${uploadDir:/opt/uploads/}")
    private String uploadDir;
    public String getUploadDir() {
        return uploadDir;
    }
}
