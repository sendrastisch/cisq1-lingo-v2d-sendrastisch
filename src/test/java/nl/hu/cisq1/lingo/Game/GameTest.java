package nl.hu.cisq1.lingo.Game;

import nl.hu.cisq1.lingo.Game.domain.Game;
import nl.hu.cisq1.lingo.Game.domain.exception.RoundCannotBeStartedException;
import nl.hu.cisq1.lingo.Game.domain.exception.RoundIsNotPlaying;
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

    @DisplayName("Will pass if the user cannot take a guess when a round is not started")
    @Test
    void CannotTakeGuessIfRoundNotStarted(){
        assertThrows(RoundIsNotPlaying.class, () -> {
            Game game = new Game();

            game.takeGuess("never");
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

    @DisplayName("Will pass if the word length is changed accordingly after one round")
    @Test
    void testWordLengthMethod(){
        Game game = new Game();

        game.startNewRound("sunny");
        game.takeGuess("sunny");

        assertEquals(6, game.getLengthWord());
    }

    @DisplayName("Will pass if the word length is changed accordingly after two rounds")
    @Test
    void testWordLengthMethodAfterTwoRounds(){
        Game game = new Game();

        game.startNewRound("sunny");
        game.takeGuess("sunny");

        game.startNewRound("story");
        game.takeGuess("story");

        assertEquals(7, game.getLengthWord());
    }

    @DisplayName("Will pass if the word length is changed accordingly after three rounds (should be 5 again after 7")
    @Test
    void testWordLengthMethodAfterThreeRounds(){
        Game game = new Game();

        game.startNewRound("sunny");
        game.takeGuess("sunny");

        game.startNewRound("story");
        game.takeGuess("story");

        game.startNewRound("swirl");
        game.takeGuess("swirl");

        assertEquals(5, game.getLengthWord());
    }

    @DisplayName("Will pass if the word length is changed back to 5 after losing a game")
    @Test
    void testWordLengthMethodAfterLosing(){
        Game game = new Game();

        game.startNewRound("sunny");
        game.takeGuess("sunny");

        game.startNewRound("story");
        game.takeGuess("swirl");
        game.takeGuess("stray");
        game.takeGuess("saint");
        game.takeGuess("swing");
        game.takeGuess("stunt");

        assertEquals(5, game.getLengthWord());
    }




}