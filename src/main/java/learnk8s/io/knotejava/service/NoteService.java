package learnk8s.io.knotejava.service;

import learnk8s.io.knotejava.domain.Comment;
import learnk8s.io.knotejava.domain.Note;
import learnk8s.io.knotejava.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Page<Note> getNotePages(Pageable pageable) {
        return noteRepository.findAll(pageable);
    }

    public List<Note> getNoteInPage(Page<Note> notePages) {
        return notePages.getContent();
    }

    public Map<String, Object> getPagination(Page<Note> notePages) {
        int currentPage = notePages.getNumber() + 1; // plus 1 because pageable start page 0
        int totalPages = notePages.getTotalPages();
        int startPage = ((currentPage - 1) / 10) * 10 + 1; // 1 ~ 10 => 1, 11 ~ 20 => 11, 21 ~ 30 => 21, ...
        int prevStartPage = startPage - 10; // if this value is minus, user can't see the button for previous start page
        int nextStartPage = startPage + 10; // similar to above

        boolean showFirstPage = false;
        boolean showLastPage = false;
        if (startPage > 10) {
            showFirstPage = true;
        }

        List<Integer> pageIndices = new ArrayList<>();
        while (startPage <= totalPages && pageIndices.size() < 10) {
            pageIndices.add(startPage);
            startPage++;
        }

        if (startPage <= totalPages) {
            showLastPage = true;

        }

        Map<String, Object> pagination = new HashMap<>();
        pagination.put("currentPage", currentPage);
        pagination.put("totalPages", totalPages);
        pagination.put("startPage", startPage);
        pagination.put("prevStartPage", prevStartPage);
        pagination.put("nextStartPage", nextStartPage);
        pagination.put("showFirstPage", showFirstPage);
        pagination.put("showLastPage", showLastPage);
        pagination.put("pageIndices", pageIndices);

        return pagination;
    }

    public Optional<Note> getEntireNote(String id) {
        return noteRepository.findById(id);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GTM+9"));
        timeFormat.setTimeZone(TimeZone.getTimeZone("GTM+9"));

        String createdDate = dateFormat.format(currentTime);
        String createdTime = timeFormat.format(currentTime);

        return noteRepository.save(new Note(null, title, author, createdDate, createdTime, description, new ArrayList<Comment>()));
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