package learnk8s.io.knotejava.repository;

import learnk8s.io.knotejava.domain.Note;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NoteRepository extends MongoRepository<Note, String>, NoteRepositoryCustom{
}