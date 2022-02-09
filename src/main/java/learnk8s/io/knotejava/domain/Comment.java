package learnk8s.io.knotejava.domain;

import lombok.*;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class Comment {

    private String author;
    private String createdDate;
    private String createdTime;
    private String description;

    @Override
    public String toString() {
        return description;
    }
}
