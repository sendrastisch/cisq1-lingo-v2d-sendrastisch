package nl.hu.cisq1.lingo.Round.domain;

import nl.hu.cisq1.lingo.Round.RoundState.RoundState;
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


}