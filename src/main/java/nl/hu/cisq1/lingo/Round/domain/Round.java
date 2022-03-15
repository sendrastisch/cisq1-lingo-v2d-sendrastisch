package nl.hu.cisq1.lingo.Round.domain;

import nl.hu.cisq1.lingo.Feedback.domain.Feedback;
import nl.hu.cisq1.lingo.Round.exception.RoundWonException;
import nl.hu.cisq1.lingo.Hint.domain.Hint;
import nl.hu.cisq1.lingo.Round.exception.RoundLostException;
import nl.hu.cisq1.lingo.RoundState.RoundState;

import java.util.ArrayList;
import java.util.List;

public class Round {
    private String wordToGuess;
    private List<Feedback> feedbackList = new ArrayList<>();
    private Hint hint;

    public Round(String wrToGs){
        wordToGuess = wrToGs;
        hint = new Hint(wordToGuess);
        System.out.println(hint);
    }

    public RoundState getState() {
        if(feedbackList.size() == 5) return RoundState.LOST;
        if(feedbackList.size() != 0 && feedbackList.get(feedbackList.size() -1).isWordGuessed()) return RoundState.WON;
        return RoundState.PLAYING;
    }

    public void takeGuess(String guess){
        if(getState() == RoundState.LOST){
            throw new RoundLostException("You lost the round!");
        } else if(getState() == RoundState.WON){
            throw new RoundWonException("You already won the round!");
        } else{
            Feedback feedback = new Feedback(guess);
            feedback.createListMarks(this.wordToGuess);
            feedbackList.add(feedback);
            hint = feedback.generateHint(hint, guess);
            System.out.println(hint);
        }
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public Hint getHint() {
        return hint;
    }
}
