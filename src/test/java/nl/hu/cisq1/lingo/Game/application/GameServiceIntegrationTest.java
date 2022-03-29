package nl.hu.cisq1.lingo.Game.application;

import nl.hu.cisq1.lingo.Game.data.GameRepository;
import nl.hu.cisq1.lingo.Game.domain.Game;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceIntegrationTest {
    @Autowired
    private GameRepository gameRepository;

//    @Test
//    @DisplayName("Adding something to database works")
//    void addToDatabase(){
//        Game game = new Game();
//        game.startNewRound("dyllan");
//        game.takeGuess("dyllan");
//
//        gameRepository.deleteById(18L);
//
//        fail();
//    }

}