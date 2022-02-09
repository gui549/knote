package learnk8s.io.knotejava.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="notes")
@Getter
//@NoArgsConstructor
@AllArgsConstructor
public class Note {

    @Id
    private String id;
    private String title;
    private String author;
    private String createdDate;
    private String createdTime;
    private String description;

    private List<Comment> comments;

    @Override
    public String toString() {
        return description;
    }
}
