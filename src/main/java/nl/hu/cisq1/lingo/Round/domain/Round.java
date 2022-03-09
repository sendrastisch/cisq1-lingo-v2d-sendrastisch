package nl.hu.cisq1.lingo.Round.domain;

import nl.hu.cisq1.lingo.Feedback.domain.Feedback;
import nl.hu.cisq1.lingo.Mark.Mark;
import nl.hu.cisq1.lingo.RoundState.RoundState;
import org.springframework.beans.factory.support.ManagedArray;

public class Round {
    private String wordToGuess;
    private String guess;
    private RoundState state;
    private Feedback feedback;

    public Round(String wrToGs, String gs){
        wordToGuess = wrToGs;
        guess = gs;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public String getGuess() {
        return guess;
    }

    public void playRound(){
        Feedback feedback = new Feedback(this.guess);
        feedback.createListMarks(this.wordToGuess);

        System.out.println(feedback.getMarks());
    }


}
