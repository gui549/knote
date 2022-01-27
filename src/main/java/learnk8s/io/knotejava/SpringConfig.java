package learnk8s.io.knotejava;

import learnk8s.io.knotejava.repository.NoteRepository;
import learnk8s.io.knotejava.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Autowired
    private NoteRepository noteRepository;

    @Bean
    public KnoteProperties properties() {
        System.out.println("knoteProperties bean is created!!");
        return new KnoteProperties();
    }

    @Bean
    public NoteService noteService() {
        System.out.println("noteRepository bean is created!!");
        return new NoteService(noteRepository);
    }
}
