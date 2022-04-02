package nl.hu.cisq1.lingo.trainer.application;

import nl.hu.cisq1.lingo.trainer.application.exceptions.NoGamesFoundException;
import nl.hu.cisq1.lingo.trainer.data.GameRepository;
import nl.hu.cisq1.lingo.trainer.domain.Game;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDto;
import nl.hu.cisq1.lingo.Words.application.WordService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class GameService {
    private final WordService wordService;
    private final GameRepository gameRepository;

    public GameService(WordService wordService, GameRepository gameRepository) {
        this.wordService = wordService;
        this.gameRepository = gameRepository;
    }

    public ProgressDto startNewGame() {
        String wordToGuess = this.wordService.provideRandomWord(5);

        Game game = new Game();
        game.startNewRound(wordToGuess);
        this.gameRepository.save(game);

        return game.getProgress();
    }

    public ProgressDto takeGuess(long gameId, String guess) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NoGamesFoundException("Wrong id."));

        game.takeGuess(guess);
        gameRepository.save(game);

        return game.getProgress();
    }

    public ProgressDto startNewRound(long gameId) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new NoGamesFoundException("No game found with this ID. "));
        String nextWordToGuess = this.wordService.provideRandomWord(game.getLengthWord());

        game.startNewRound(nextWordToGuess);
        gameRepository.save(game);

        return game.getProgress();
    }

    public List<ProgressDto> findAllGames() {
        List<Game> games = gameRepository.findAll();
        List<ProgressDto> dto = new ArrayList<>();

        if (games.isEmpty()) {
            throw new NoGamesFoundException("No games are found.");
        } else {
            for (Game g : games) {
                dto.add(g.getProgress());
            }
        }
        return dto;
    }

    public ProgressDto findGameById(long id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new NoGamesFoundException("No game is found."));

        return game.getProgress();
    }
}
