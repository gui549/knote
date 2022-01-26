package learnk8s.io.knotejava.controller;

import learnk8s.io.knotejava.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class KNoteController {
    private NoteService noteService;

    @Autowired
    public KNoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/")
    public String index(Model model) {
        noteService.getAllNotes(model);
        return "index";
    }

    @PostMapping("/note")
    public String saveNotes(@RequestParam("image") MultipartFile file,
                            @RequestParam String description,
                            @RequestParam(required = false) String publish,
                            @RequestParam(required = false) String upload,
                            Model model) throws IOException {

        if (publish != null && publish.equals("Publish")) {
            noteService.saveNote(description, model);
            noteService.getAllNotes(model);
            return "redirect:/";
        }
        if (upload != null && upload.equals("Upload")) {
            if (file != null && file.getOriginalFilename() != null
                && !file.getOriginalFilename().isEmpty()) {
                noteService.uploadImage(file, description, model);
            }
            noteService.getAllNotes(model);
            return "index";
        }
        return "index";
    }

}
