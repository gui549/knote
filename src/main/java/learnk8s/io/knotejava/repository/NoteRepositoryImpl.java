package learnk8s.io.knotejava.repository;

import com.mongodb.client.result.UpdateResult;
import learnk8s.io.knotejava.domain.Comment;
import learnk8s.io.knotejava.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.List;

public class NoteRepositoryImpl implements NoteRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Autowired
    public NoteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void pushCommentById(String id, Comment comment) {
        Query query = new Query(Criteria.where("_id").is(id));
        Update update = new Update().push("comments").value(comment);

        mongoTemplate.updateFirst(query, update, Note.class);
    }

    public List<Note> findPageWithSkip(int offset, int pageSize) {
        Query query = new Query().with(Sort.by(Sort.Direction.DESC, "_id")).skip(offset).limit(pageSize);
        List<Note> notes = mongoTemplate.find(query, Note.class);

        return notes;
    }

}