package nl.hu.cisq1.lingo.trainer.presentation;

import nl.hu.cisq1.lingo.trainer.application.GameService;
import nl.hu.cisq1.lingo.trainer.application.exceptions.NoGamesFoundException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundCannotBeStartedException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundIsNotPlaying;
import nl.hu.cisq1.lingo.trainer.presentation.dto.GuessDto;
import nl.hu.cisq1.lingo.trainer.presentation.dto.ProgressDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/{id}")
    public ProgressDto getGameById(@PathVariable long id) {
        try {
            return gameService.findGameById(id);
        } catch (NoGamesFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping()
    public ProgressDto startGame() {
        return gameService.startNewGame();
    }

    @PostMapping("/{gameId}")
    public ProgressDto startNewRound(@PathVariable long gameId) {
        try {
            return gameService.startNewRound(gameId);
        } catch (NoGamesFoundException | RoundCannotBeStartedException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    //dit wordt de take a guess controller method ding.
    @PatchMapping("/{gameId}")
    public ProgressDto takeAGuess(@PathVariable long gameId, @RequestBody GuessDto guess) {
        try{
            return gameService.takeGuess(gameId, guess.guess);
        } catch(RoundIsNotPlaying exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }

    }

}
