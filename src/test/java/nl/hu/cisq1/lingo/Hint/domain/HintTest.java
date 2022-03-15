package nl.hu.cisq1.lingo.Hint.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

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

        assertEquals(expectedHint, hint.getHint());
    }
}