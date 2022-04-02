package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.enums.Mark;
import nl.hu.cisq1.lingo.trainer.domain.Hint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class HintTest {

    private static Stream<Arguments> provideArgumentsForHintConstructorTest(){
        return Stream.of(
                Arguments.of("story", "s...."),
                Arguments.of("coney", "c...."),
                Arguments.of("blank", "b...."),
                Arguments.of("ready", "r...."),
                Arguments.of("shake", "s...."),
                Arguments.of("betty", "b....")
        );
    }

    @ParameterizedTest
    @MethodSource("provideArgumentsForHintConstructorTest")
    @DisplayName("Will pass if the constructor creates the first letter hint correctly (with the first letter and 4 dots)")
    void constructorGivesCorrectOutput(String guess, String expectedHint){
        Hint hint = new Hint(guess);

        assertEquals(expectedHint, hint.getHintAttribute());
    }

    @MethodSource("provideArgumentsForNewHintConstructorTest")
    @ParameterizedTest
    @DisplayName("Will pass if the constructor creates a new hint correctly based on previoushint, a list of marks and a new attempt")
    void constructorGivesCorrectNewHint(Hint hint, List<Mark> marks, String attempt, String expectedResult){
        Hint newHint = new Hint(hint, marks, attempt);

        assertEquals(expectedResult, newHint.getHintAttribute());

    }

    private static Stream<Arguments> provideArgumentsForNewHintConstructorTest(){
        return Stream.of(
                Arguments.of(new Hint("folks"), new ArrayList<>(List.of(new Mark[]{Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT})), "fawns", "f...s"),
                Arguments.of(new Hint("tears"), new ArrayList<>(List.of(new Mark[]{Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.CORRECT, Mark.ABSENT})), "thorn", "t..r."),
                Arguments.of(new Hint("seven"), new ArrayList<>(List.of(new Mark[]{Mark.CORRECT, Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT})), "serve", "se..."),
                Arguments.of(new Hint("peace"), new ArrayList<>(List.of(new Mark[]{Mark.CORRECT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT, Mark.ABSENT})), "props", "p....")
        );
    }
}