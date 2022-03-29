package nl.hu.cisq1.lingo.Game.application;

import nl.hu.cisq1.lingo.Game.application.exceptions.NoGamesFoundException;
import nl.hu.cisq1.lingo.Game.data.GameRepository;
import nl.hu.cisq1.lingo.Game.domain.Game;
import nl.hu.cisq1.lingo.Game.domain.GameState.GameState;
import nl.hu.cisq1.lingo.Progress.domain.ProgressDto;
import nl.hu.cisq1.lingo.Words.application.WordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Test
    @DisplayName("Starting a new game gives a correct hint & correct playing state")
    void startNewGameTest() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);

        ProgressDto progressDto = gameService.startNewGame();

        assertEquals("g....", progressDto.currentHint.hint);
        assertEquals(GameState.PLAYING, progressDto.gState);
    }

    @Test
    @DisplayName("Taking a guess gives a correct hint back")
    void takeGuess() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);
        Game game = new Game();

        game.startNewRound(wordService.provideRandomWord(5));
        when(repository.findById(any())).thenReturn(Optional.of(game));

        ProgressDto progressDto1 = gameService.takeGuess(0L, "groen");

        assertEquals("groe.", progressDto1.currentHint.hint);
    }

    @Test
    @DisplayName("Starting a new round after taking a guess should give a word with the correct word length")
    void startNewRound() {
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");
        when(wordService.provideRandomWord(6)).thenReturn("groens");
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);
        Game game = new Game();

        game.startNewRound(wordService.provideRandomWord(5));
        when(repository.findById(any())).thenReturn(Optional.of(game));

        game.takeGuess("groep");

        ProgressDto progressDto1 = gameService.startNewRound(0L);

        assertEquals(6, progressDto1.lengthWord);
    }

    @Test
    @DisplayName("The find all games method returns a list of DTO's")
    void findAllGamesTest(){
        WordService wordService = mock(WordService.class);
        when(wordService.provideRandomWord(5)).thenReturn("groep");
        GameRepository repository = mock(GameRepository.class);
        GameService gameService = new GameService(wordService, repository);
        Game game = new Game();
        Game game2 = new Game();
        List<Game> games = new ArrayList<>();

        game.startNewRound(wordService.provideRandomWord(5));
        game2.startNewRound(wordService.provideRandomWord(5));
        games.add(game);
        games.add(game2);

        when(repository.findAll()).thenReturn(games);

        List<ProgressDto> progressDto = gameService.findAllGames();

        assertEquals(2, progressDto.size());
    }

    @Test
    @DisplayName("The find all games method will throw an exception if no games are found.")
    void testFindAllGamesException(){
        WordService wordService = mock(WordService.class);
        GameRepository repository = mock(GameRepository.class);

        GameService gameService = new GameService(wordService, repository);

        assertThrows(NoGamesFoundException.class, gameService::findAllGames);
    }

}