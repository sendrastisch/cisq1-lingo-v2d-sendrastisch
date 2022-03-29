package nl.hu.cisq1.lingo.Game.presentation;

import nl.hu.cisq1.lingo.Game.application.GameService;
import nl.hu.cisq1.lingo.Game.application.exceptions.NoGamesFoundException;
import nl.hu.cisq1.lingo.Game.domain.Game;
import nl.hu.cisq1.lingo.Progress.domain.ProgressDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {
    private GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping()
    public List<ProgressDto> getAllGames() {
        try{
            return gameService.findAllGames();
        } catch (NoGamesFoundException exception) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ProgressDto getGameById(@PathVariable long id){
        try{
            return gameService.findGameById(id);
        } catch(NoGamesFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping()
    public ProgressDto startGame(){
        return gameService.startNewGame();
    }

    @PostMapping("/{gameId}")
    public ProgressDto startNewRound(@PathVariable long gameId){
        try{
            return gameService.startNewRound(gameId);
        } catch(NoGamesFoundException exception){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, exception.getMessage());
        }
    }

    //dit wordt de take a guess controller method ding.
    @PatchMapping("/{gameId}")
    public ProgressDto takeAGuess(@PathVariable long gameId, @RequestBody String guess){
        return gameService.takeGuess(gameId, guess);
    }
    
}
