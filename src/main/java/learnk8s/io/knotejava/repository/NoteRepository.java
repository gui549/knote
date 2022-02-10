package learnk8s.io.knotejava.repository;

import learnk8s.io.knotejava.domain.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String>, NoteRepositoryCustom{
}