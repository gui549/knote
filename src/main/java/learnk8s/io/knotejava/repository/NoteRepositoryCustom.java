package learnk8s.io.knotejava.repository;

import learnk8s.io.knotejava.domain.Comment;
import learnk8s.io.knotejava.domain.Note;

import java.util.List;

public interface NoteRepositoryCustom {

    public void pushCommentById(String id, Comment comment);

    public List<Note> findPageWithSkip(int offset, int pageSize);
}
