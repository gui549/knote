package learnk8s.io.knotejava.service;

import learnk8s.io.knotejava.domain.Comment;
import learnk8s.io.knotejava.domain.Note;
import learnk8s.io.knotejava.repository.NoteRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class NoteServiceTest {

    @Autowired NoteService noteService;
    @Autowired NoteRepository noteRepository;

    @AfterEach
    public void afterEach() {
        noteRepository.deleteAll();
    }

    @Test
    void getNotePages(){
        final int PAGE = 1, SIZE = 3, TOTAL_NOTES = 5;

        Note[] savedNotes = new Note[TOTAL_NOTES];
        for (int i = 0; i < TOTAL_NOTES; i++) {
            savedNotes[i] = noteService.saveNote("testTitle", "testAuthor", "testDescription");
        }

        Pageable paging = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.DESC, "_id"));
        Page<Note> notePages = noteService.getNotePages(paging);

        System.out.println("notePages.getNumber() = " + notePages.getNumber());
        System.out.println("notePages.getContent() = " + notePages.getContent());
        System.out.println("notePages.getTotalPages() = " + notePages.getTotalPages());
    }

    @Test
    void getNoteInPage() {
        final int PAGE = 3, SIZE = 3, TOTAL_NOTES = 10;
        final int TOTAL_PAGES = (int) Math.ceil((double) TOTAL_NOTES / SIZE);

        Note[] savedNotes = new Note[TOTAL_NOTES];
        for (int i = 0; i < TOTAL_NOTES; i++) {
            savedNotes[i] = noteService.saveNote("testTitle", "testAuthor", "testDescription");
        }

        Pageable paging = PageRequest.of(PAGE, SIZE, Sort.by(Sort.Direction.DESC, "_id"));
        Page<Note> notePages = noteService.getNotePages(paging);
        List<Note> notes = noteService.getNoteInPage(notePages);

        assertThat(notePages.getTotalPages()).isEqualTo(TOTAL_PAGES);
        for (int i = 0; i < notes.size(); i++) {
            assertThat(notes.get(i).getId()).isEqualTo(savedNotes[TOTAL_NOTES - (SIZE * PAGE) - i - 1].getId());
        }
    }

    @Test
    void getPagination() {
    }

    @Test
    void getEntireNote() {
        String testTitle = "Good Morning";
        String testAuthor = "Kong";
        String testDescription = "Hello!";
        Note savedNote = noteService.saveNote(testTitle, testAuthor, testDescription);

        Note retrievedNote = noteService.getEntireNote(savedNote.getId()).get();
        assertThat(savedNote.getTitle()).isEqualTo(retrievedNote.getTitle());
        assertThat(savedNote.getAuthor()).isEqualTo(retrievedNote.getAuthor());
        assertThat(savedNote.getDescription()).isEqualTo(retrievedNote.getDescription());
    }

    @Test
    void saveNote() {
        String testTitle = "Good Morning";
        String testAuthor = "Kong";
        String testDescription = "Hello!";
        Note savedNote = noteService.saveNote(testTitle, testAuthor, testDescription);

        assertThat(savedNote.getTitle()).isEqualTo(testTitle);
        assertThat(savedNote.getAuthor()).isEqualTo(testAuthor);
        assertThat(savedNote.getDescription()).isEqualTo(testDescription);
    }

    @Test
    void saveComment() {
        Comment comment1 = new Comment("Kong", "21-01-21", "21:09", "Hello");
        Comment comment2 = new Comment("Han", "21-01-21", "23:12", "Hi");
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment1);
        comments.add(comment2);
    }
}