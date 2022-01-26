package learnk8s.io.knotejava.service;

import learnk8s.io.knotejava.KnoteProperties;
import learnk8s.io.knotejava.domain.Note;
import learnk8s.io.knotejava.repository.NoteRepository;
import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class NoteService {
    private NoteRepository noteRepository;

    @Autowired
    private KnoteProperties properties;

    private Parser parser = Parser.builder().build();
    private HtmlRenderer renderer = HtmlRenderer.builder().build();

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void getAllNotes(Model model) {
        List<Note> notes = noteRepository.findAll();
        Collections.reverse(notes);
        model.addAttribute("notes", notes);
    }

    public void saveNote(String description, Model model) {
        if (description != null && !description.trim().isEmpty()){
            Node document = parser.parse(description.trim());
            String html = renderer.render(document);
            noteRepository.save(new Note(null, html));
            model.addAttribute("description", "");
        }
    }

    public void uploadImage(MultipartFile file, String description, Model model) throws IOException {
        File uploadDir = new File(properties.getUploadDir());
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        String fileId = UUID.randomUUID().toString() + "." + file.getOriginalFilename().split("\\.")[1];
        System.out.println("Path: " + properties.getUploadDir() + fileId);
        file.transferTo(new File(properties.getUploadDir() + fileId));
        model.addAttribute("description", description + " ![](/opt/uploads/" + fileId + ")");
    }

}
