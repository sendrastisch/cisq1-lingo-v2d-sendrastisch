package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.presentation.dto.FeedbackDto;
import nl.hu.cisq1.lingo.trainer.presentation.dto.HintDto;
import nl.hu.cisq1.lingo.trainer.domain.enums.RoundState;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundLostException;
import nl.hu.cisq1.lingo.trainer.domain.exception.RoundWonException;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Round {
    @Id
    @GeneratedValue
    private long id;

    private String wordToGuess;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private final List<Feedback> feedbackList = new ArrayList<>();

    @OneToOne
    @Cascade(CascadeType.ALL)
    private Hint hint;

    public Round() {

    }

    public Round(String wrToGs) {
        wordToGuess = wrToGs;
        hint = new Hint(wordToGuess);
    }

    public HintDto getHint() {
        return hint.getHint();
    }

    public RoundState getState() {
        if (feedbackList.size() == 5) return RoundState.LOST;
        if (feedbackList.size() != 0 && feedbackList.get(feedbackList.size() - 1).isWordGuessed())
            return RoundState.WON;
        return RoundState.PLAYING;
    }

    public void takeGuess(String guess) {
        if (getState() == RoundState.LOST) {
            throw new RoundLostException("You lost the round!");
        } else if (getState() == RoundState.WON) {
            throw new RoundWonException("You already won the round!");
        } else {
            Feedback feedback = new Feedback(guess);
            feedback.createListMarks(this.wordToGuess);
            feedbackList.add(feedback);
            hint = feedback.generateHint(hint, guess);
        }
    }

    public List<FeedbackDto> getFeedbackList() {
        List<FeedbackDto> feedbackDtos = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            feedbackDtos.add(feedback.getFeedbackDto());
        }
        return feedbackDtos;
    }
}
