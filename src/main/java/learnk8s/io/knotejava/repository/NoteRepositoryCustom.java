package learnk8s.io.knotejava.repository;

import learnk8s.io.knotejava.domain.Comment;

public interface NoteRepositoryCustom {

    public void pushCommentById(String id, Comment comment);
}
