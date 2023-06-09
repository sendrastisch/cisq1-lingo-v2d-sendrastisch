package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotPlaying;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDto;
import nl.hu.cisq1.lingo.Words.application.WordService;
import nl.hu.cisq1.lingo.Words.data.WordRepository;
import nl.hu.cisq1.lingo.Words.domain.Word;
import nl.hu.cisq1.lingo.trainer.application.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
@AutoConfigureMockMvc
class GameServiceIntegrationTest {

    @Autowired
    private GameService gameService;

    @Autowired
    private WordRepository repository;

    private static final String RANDOM_WORD_5 = "groep";
    private static final String RANDOM_WORD_6 = "school";
    private static final String RANDOM_WORD_7 = "student";

    @Test
    @DisplayName("Guessing correctly correctly makes wordlength go to 6")
    void testCorrectGuess(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        ProgressDto progressDto = gameService.startNewGame();

        ProgressDto progressDto1 = gameService.takeGuess(progressDto.gameId, "groep");

        assertEquals(6, progressDto1.lengthWord);
    }

    @Test
    @DisplayName("Guessing after 5 times throws exception")
    void testTooManyGuesses(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        ProgressDto progressDto = gameService.startNewGame();

        gameService.takeGuess(progressDto.gameId, "groen");
        gameService.takeGuess(progressDto.gameId, "groet");
        gameService.takeGuess(progressDto.gameId, "groot");
        gameService.takeGuess(progressDto.gameId, "gruis");
        gameService.takeGuess(progressDto.gameId, "greet");

        assertThrows(RoundIsNotPlaying.class, () -> {
            gameService.takeGuess(progressDto.gameId, "groen");
        });
    }

    @BeforeEach
    void loadTestData() {
        // Load test fixtures into test database before each test case
        repository.deleteAll();
        repository.save(new Word(RANDOM_WORD_5));
        repository.save(new Word(RANDOM_WORD_6));
        repository.save(new Word(RANDOM_WORD_7));
    }

    @AfterEach
    void clearTestData() {
        // Remove test fixtures from test database after each test case
        repository.deleteAll();
    }
}