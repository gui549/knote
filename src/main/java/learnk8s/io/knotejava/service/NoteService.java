package learnk8s.io.knotejava.service;

import learnk8s.io.knotejava.domain.Comment;
import learnk8s.io.knotejava.domain.Note;
import learnk8s.io.knotejava.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Page<Note> getNotePages(Pageable pageable) {
        Page<Note> notePages = noteRepository.findAll(pageable);
        return notePages;
    }

    public List<Note> getNoteInPage(Page notePages) {
        List<Note> notes = notePages.getContent();
        return notes;
    }

    public Map<String, Object> getPagination(Page notePages) {
        int currentPage = notePages.getNumber();
        int totalPages = notePages.getTotalPages();
        int startPage = ((currentPage - 1) / 10) * 10 + 1; // 1 ~ 10 => 1, 11 ~ 20 => 11, 21 ~ 30 => 21, ...

        Boolean showFirstPage = false;
        boolean showLastPage = false;
        if (startPage > 10) {
            showFirstPage = true;
        }

        List<Integer> pageIndices = new ArrayList<Integer>();
        while (startPage <= totalPages && pageIndices.size() < 10) {
            pageIndices.add(startPage);
            startPage++;
        }

        if (startPage <= totalPages) {
            showLastPage = true;
        }

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", Integer.valueOf(currentPage));
        pagination.put("totalPages", Integer.valueOf(totalPages));
        pagination.put("startPage", Integer.valueOf(startPage));
        pagination.put("showFirstPage", Boolean.valueOf(showFirstPage));
        pagination.put("showLastPage", Boolean.valueOf(showLastPage));
        pagination.put("pageIndices", pageIndices);

        return pagination;
    }

    public Optional<Note> getEntireNote(String id) {
        Optional<Note> note = noteRepository.findById(id);
        return note;
    }

    public Note saveNote(String title, String author, String description) throws IllegalArgumentException {
        // Invalid Title
        if (isInvalid(title)) {
            throw new IllegalArgumentException();
        }

        // Invalid Author
        else if (isInvalid(author)) {
            throw new IllegalArgumentException();
        }

        // Invalid description
        else if (isInvalid(description)) {
            throw new IllegalArgumentException();
        }

        Date currentTime = new Date();
        String createdDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        String createdTime = new SimpleDateFormat("HH:mm").format(currentTime);

        Note savedNote = noteRepository.save(new Note(null, title, author, createdDate, createdTime, description, new ArrayList<Comment>()));

        return savedNote;
    }

    public Boolean saveComment(String id, String author, String description) {
        // Invalid Author
        if (isInvalid(author)) {
            return false;
        }

        // Invalid description
        else if (isInvalid(description)) {
            return false;
        }

        Date currentTime = new Date();
        String createdDate = new SimpleDateFormat("yyyy-MM-dd").format(currentTime);
        String createdTime = new SimpleDateFormat("HH:mm").format(currentTime);

//      TODO: PUSH A NEW COMMENT
//        Note savedComment = noteRepository
        return true;
    }

    private Boolean isInvalid(String testString) {
        return testString == null || testString.trim().isEmpty();
    }
}