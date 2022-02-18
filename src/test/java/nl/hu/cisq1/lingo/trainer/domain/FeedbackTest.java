package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.Mark.Mark;
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
}