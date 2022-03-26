package nl.hu.cisq1.lingo.Game.application;

import nl.hu.cisq1.lingo.Game.data.GameRepository;
import nl.hu.cisq1.lingo.Game.domain.Game;
import nl.hu.cisq1.lingo.Game.domain.GameState.GameState;
import nl.hu.cisq1.lingo.Progress.domain.ProgressDto;
import nl.hu.cisq1.lingo.Words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    @DisplayName("Starting a new game gives a correct hint & correct playing state")
    void startNewGameTest(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");

        GameRepository repository = mock(GameRepository.class);

        GameService gameService = new GameService(wordService, repository);
        ProgressDto progressDto = gameService.startNewGame();

        assertEquals("g....", progressDto.getCurrentHint().getHint());
        assertEquals(GameState.PLAYING, progressDto.getgState());
    }
}