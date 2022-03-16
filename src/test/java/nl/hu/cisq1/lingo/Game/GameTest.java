package nl.hu.cisq1.lingo.Game;

import nl.hu.cisq1.lingo.Game.exception.RoundCannotBeStartedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @DisplayName("Will pass if the new round cannot be started if previous round is not won")
    @Test
    void CannotBeStartedIfPreviousRoundIsNotWon(){
        assertThrows(RoundCannotBeStartedException.class, () -> {
           Game game = new Game();

           game.startNewRound("romeo");
           game.takeGuess("juliet");
           game.startNewRound("story");
        });
    }

    @DisplayName("Will pass if the score is calculated correctly after winning rounds")
    @Test
    void testScoreCalculation(){
        Game game = new Game();

        //make one round and win after one try
        game.startNewRound("romeo");
        game.takeGuess("romeo");

        //make another round and win after second try
        game.startNewRound("stormy");
        game.takeGuess("starve");
        game.takeGuess("stormy");

        assertEquals(45, game.getScore());
    }

    @DisplayName("Will pass if the score is calculated correctly after winning rounds and losing the last one")
    @Test
    void testScoreCalculationWithLosing(){
        Game game = new Game();

        //make one round and win after one try
        game.startNewRound("romeo");
        game.takeGuess("romeo");

        //make another round and win after second try
        game.startNewRound("stormy");
        game.takeGuess("starve");
        game.takeGuess("stormy");

        //make another round and lose
        game.startNewRound("shake");
        game.takeGuess("spray");
        game.takeGuess("stain");
        game.takeGuess("story");
        game.takeGuess("sworn");
        game.takeGuess("storm");

        assertEquals(45, game.getScore());
    }


}