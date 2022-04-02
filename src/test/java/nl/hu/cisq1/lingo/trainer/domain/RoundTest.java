package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enums.RoundState;
import nl.hu.cisq1.lingo.trainer.domain.Round;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundLostException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundWonException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest {

    @DisplayName("Will pass if the state is won when the word is guessed correctly")
    @Test
    void playRoundAndWin() {
        Round round = new Round("swift");
        round.takeGuess("swift");

        assertEquals(RoundState.WON, round.getState());
    }

    @DisplayName("Will pass if the state is playing when the word is not guessed correctly yet and the player has tries left.")
    @Test
    void playRoundAndCheckPlayingState() {
        Round round = new Round("swift");
        round.takeGuess("swikt");

        assertEquals(RoundState.PLAYING, round.getState());
    }

    @DisplayName("Will pass if the state is lost after 5 guesses.")
    @Test
    void playRoundAndGuessTooManyTimes(){
        Round round = new Round("swift");
        round.takeGuess("lunch");
        round.takeGuess("juicy");
        round.takeGuess("start");
        round.takeGuess("blood");
        round.takeGuess("swikt");

        assertEquals(RoundState.LOST, round.getState());
    }

    @DisplayName("Will pass if the method throws a roundlostexception after trying to guess a sixth time")
    @Test
    void guessSixTimesAndGetException(){
        assertThrows(RoundLostException.class, () -> {
            Round round = new Round("swift");
            round.takeGuess("lunch");
            round.takeGuess("juicy");
            round.takeGuess("start");
            round.takeGuess("blood");
            round.takeGuess("lover");
            round.takeGuess("swikt");
        });
    }

    @DisplayName("Will pass if the method throws a roundwonexception after trying to guess another time after winning")
    @Test
    void winAndTryToGuessAgain(){
        assertThrows(RoundWonException.class, () -> {
            Round round = new Round("swift");
            round.takeGuess("lunch");
            round.takeGuess("juicy");
            round.takeGuess("swift");
            round.takeGuess("lives");
        });
    }

    @DisplayName("Will pass if the method gives back a correct feedbacklist after guessing.")
    @Test
    void TestGetFeedback(){
        Round round = new Round("swift");
        round.takeGuess("store");
        round.takeGuess("sting");

        assertEquals(2, round.getFeedbackList().size());
    }
}