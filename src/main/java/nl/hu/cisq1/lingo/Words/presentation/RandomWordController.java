package nl.hu.cisq1.lingo.Words.presentation;

import nl.hu.cisq1.lingo.Words.application.WordService;
import nl.hu.cisq1.lingo.Words.domain.exception.WordLengthNotSupportedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/words")
public class RandomWordController {
    private final WordService service;

    public RandomWordController(WordService service) {
        this.service = service;
    }

    @GetMapping("/random")
    public WordResult getRandomWord(@RequestParam Integer length) {
        try {
            String word = this.service.provideRandomWord(length);
            return new WordResult(word);
        } catch (WordLengthNotSupportedException exception) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, exception.getMessage());
        }
    }
}
