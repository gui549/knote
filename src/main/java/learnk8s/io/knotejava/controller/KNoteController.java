package learnk8s.io.knotejava.controller;

import learnk8s.io.knotejava.domain.Note;
import learnk8s.io.knotejava.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class KNoteController {
    private final NoteService noteService;
    final static int size = 10;

    @Autowired
    public KNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String getNotePages(@RequestParam(defaultValue = "1") int page, Model model) {
        Pageable paging = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "_id"));
        Page<Note> notePages = noteService.getNotePages(paging);

        List<Note> notes = noteService.getNoteInPage(notePages);
        Map<String, Object> pagination = noteService.getPagination(notePages);

        model.addAttribute("notes", notes);
        model.addAttribute("pagination", pagination);

        return "main";
    }

    @GetMapping("/note/new")
    public String test(Model model) {
        return "write-test";
    }


    @GetMapping("/note")
    public String getEntireNote(@RequestParam String id, Model model) {
        Optional<Note> note = noteService.getEntireNote(id);
        model.addAttribute("note", note.get());
        return "note";
    }

    @PostMapping("/note/new")
    public String saveNote(@RequestParam String title,
                           @RequestParam String author,
                           @RequestParam String description,
                           Model model) {

        try {
            Note savedNote = noteService.saveNote(title, author, description);
        }
        catch (IllegalArgumentException exception) {
            return "redirect:/note/new";
        }
        return "redirect:/";
    }

    @PostMapping("/note")
    public String saveComment(@RequestParam String id,
                              @RequestParam String author,
                              @RequestParam String description,
                              Model model) {

        try {
            noteService.saveComment(id, author, description);
        }
        catch (IllegalArgumentException exception) {
            return "redirect:/note?id=" + id;
        }

        return "redirect:/note?id=" + id;
    }
}
