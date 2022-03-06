package nl.hu.cisq1.lingo.Feedback.domain;

import nl.hu.cisq1.lingo.Hint.domain.Hint;
import nl.hu.cisq1.lingo.Mark.Mark;
import nl.hu.cisq1.lingo.Feedback.domain.exception.InvalidFeedbackException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @ParameterizedTest
    @MethodSource("provideInputForFeedbackIsCorrect")
    @DisplayName("Will give correct hint depending on previous hint and marks")
    void feedbackIsCorrect(Hint previousHint, Feedback feedback, String expectedHint){

        assertEquals(expectedHint, feedback.giveHint(previousHint).toString());

    }

    public static Stream<Arguments> provideInputForFeedbackIsCorrect(){
        return Stream.of(
           Arguments.of(new Hint("r...e"), new Feedback("ruche", List.of(Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT)), "ru..e"),
           Arguments.of(new Hint("wo..."), new Feedback("works", List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT)), "wor.s"),
           Arguments.of(new Hint(".ov.."), new Feedback("rover", List.of(Mark.ABSENT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT)), ".over"),
           Arguments.of(new Hint("p..e."), new Feedback("price", List.of(Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT)),"p..e.")
        );
    }

    @DisplayName("Will give list of correct marks if word is guessed")
    @Test
    void marksAreCorrectIfWordGuessed(){
        String wordToGuess = "chart";
        String attempt = "chart";
        Feedback feedback = new Feedback(attempt);
        feedback.createListMarks(wordToGuess);

        assertEquals(List.of(Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT, Mark.CORRECT), feedback.getMarks());
    }

    @DisplayName("Will give list of invalid marks if guessed word is not the same length as wordToGuess")
    @Test
    void marksAreInvalidIfLengthDifferent(){
        String wordToGuess = "chart";
        String attempt = "single";
        Feedback feedback = new Feedback(attempt);
        feedback.createListMarks(wordToGuess);

        assertEquals(List.of(Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID, Mark.INVALID), feedback.getMarks());
    }

    private static Stream<Arguments> provideArgumentsForFeedbackFromAttempt() {
        var lunch = "lunch";
        var willow = "willow";
        return Stream.of(
                Arguments.of(new Feedback("juicy"), lunch, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.CORRECT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT}))),
                Arguments.of(new Feedback("fully"), lunch, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.CORRECT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT}))),
                Arguments.of(new Feedback("blank"), lunch, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT}))),
                Arguments.of(new Feedback("white"), lunch, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT}))),
                Arguments.of(new Feedback("animal"), willow, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.ABSENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT}))),
                Arguments.of(new Feedback("window"), willow, new ArrayList<>(List.of(new Mark[]{Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.CORRECT}))),
                Arguments.of(new Feedback("global"), willow, new ArrayList<>(List.of(new Mark[]{Mark.ABSENT, Mark.PRESENT, Mark.PRESENT, Mark.ABSENT, Mark.ABSENT, Mark.PRESENT}))),
                Arguments.of(new Feedback("lunch"), lunch, Stream.generate(() -> Mark.CORRECT).limit(lunch.length()).collect(Collectors.toList())),
                Arguments.of(new Feedback("message"), lunch, Stream.generate(() -> Mark.INVALID).limit(lunch.length()).collect(Collectors.toList())),
                Arguments.of(new Feedback(lunch), willow, Stream.generate(() -> Mark.INVALID).limit(willow.length()).collect(Collectors.toList())),
                Arguments.of(new Feedback(willow), willow, Stream.generate(() -> Mark.CORRECT).limit(willow.length()).collect(Collectors.toList()))
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForFeedbackFromAttempt")
    @DisplayName("Test get feedback from attempt")
    void testGetFeedbackFromAttempt(Feedback feedback, String wordToGuess, List<Mark> expectedMarks){
           feedback.createListMarks(wordToGuess);
        assertEquals(expectedMarks, feedback.getMarks());
    }




}