//package learnk8s.io.knotejava;
//
//import com.mongodb.ReadPreference;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@EnableWebMvc
//@Configuration
//public class KnoteConfig implements WebMvcConfigurer {
//
//    private final MongoTemplate mongoTemplate;
//
//    @Autowired
//    public KnoteConfig(MongoTemplate mongoTemplate) {
//        this.mongoTemplate = mongoTemplate;
//        mongoTemplate.setReadPreference(ReadPreference.secondary());
//    }
//
//
//}
