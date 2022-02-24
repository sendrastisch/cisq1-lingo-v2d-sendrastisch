package nl.hu.cisq1.lingo.Feedback.domain;

import nl.hu.cisq1.lingo.Hint.domain.Hint;
import nl.hu.cisq1.lingo.Mark.Mark;
import nl.hu.cisq1.lingo.Feedback.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FeedbackTest {

    @DisplayName("Word is guessed if all letters are correct")
    @Test
    void wordIsGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        assertTrue(feedback.isWordGuessed());
    }

    @DisplayName("Word is not guessed if one of the letters is incorrect")
    @Test
    void wordIsNotGuessed(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        assertFalse(feedback.isWordGuessed());
    }

    @DisplayName("Word is invalid if there are more letters than specified")
    @Test
    void wordIsInvalid(){
        Feedback feedback = new Feedback("woord", List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID));
        assertTrue(feedback.isWordInvalid());
    }

    @DisplayName("Word is not invalid if there are as much letters as specified")
    @Test
    void wordIsNotInvalid(){
        Feedback feedback = new Feedback("woord", List.of(Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT));
        assertFalse(feedback.isWordInvalid());
    }

    @DisplayName("Will throw an exception when the length of the marks list is not equal to the length of the attempt")
    @Test
    void invalidFeedback(){
        assertThrows(
                InvalidFeedbackException.class,
                () -> new Feedback("woord", List.of(Mark.CORRECT, Mark.ABSENT))
        );
    }

    @DisplayName("Will give correct hint depending on previous hint, wordToGuess and marks")
    @Test
    void feedbackIsCorrect(){

        Hint previousHint = new Hint("....y");
        Feedback feedback = new Feedback("petty", List.of(Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT));

        assertEquals("p..ty", feedback.giveHint(previousHint).toString());

    }
}