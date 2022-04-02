package nl.hu.cisq1.lingo.Game.domain;

import nl.hu.cisq1.lingo.Game.domain.GameState.GameState;
import nl.hu.cisq1.lingo.Game.domain.exception.RoundCannotBeStartedException;
import nl.hu.cisq1.lingo.Game.domain.exception.RoundIsNotPlaying;
import nl.hu.cisq1.lingo.Round.domain.domain.ProgressDto;
import nl.hu.cisq1.lingo.Round.RoundState.RoundState;
import nl.hu.cisq1.lingo.Round.domain.Round;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue()
    private long id;

    @Enumerated(EnumType.STRING)
    private GameState gState = GameState.WAITING_FOR_ROUND;
    private int lengthWord = 5;
    private int score;

    @OneToMany
    @JoinColumn
    @Cascade(CascadeType.ALL)
    private List<Round> rounds = new ArrayList<>();

    public Game() {

    }

    public ProgressDto getProgress() {
        ProgressDto progressDto = new ProgressDto(this.id, rounds.size(), this.score, this.gState, this.lengthWord, rounds.get(rounds.size() - 1).getFeedbackList(), rounds.get(rounds.size() - 1).getHint());

        return progressDto;
    }

    public int getScore() {
        return score;
    }

    public int getLengthWord() {
        return lengthWord;
    }

    public void setLengthWord(int lengthWord) {
        this.lengthWord = lengthWord;
    }

    public void startNewRound(String wordToGuess) {

        if (rounds.size() != 0 && rounds.get(rounds.size() - 1).getState() != RoundState.WON) {
            throw new RoundCannotBeStartedException("The round cannot be started.");
        }

        gState = GameState.PLAYING;
        Round round = new Round(wordToGuess);
        rounds.add(round);
    }

    public void takeGuess(String guess) {

        if (rounds.size() == 0) {
            throw new RoundIsNotPlaying("There is no round that has been started");
        }

        Round round = rounds.get(rounds.size() - 1);

        if (round.getState() != RoundState.PLAYING) {
            throw new RoundIsNotPlaying("There is no round that has been started because the game is over.");
        } else {
            round.takeGuess(guess);
            if (round.getState() == RoundState.WON) {
                score += 5 * (5 - round.getFeedbackList().size()) + 5;
                changeWordLength();
            } else if (round.getState() == RoundState.LOST) {
                gState = GameState.LOST;
                this.setLengthWord(5);
            }
        }
    }

    public void changeWordLength() {
        switch (lengthWord) {
            case 5:
                setLengthWord(6);
                break;
            case 6:
                setLengthWord(7);
                break;
            case 7:
                setLengthWord(5);
                break;
            default:
                lengthWord = 5;
        }
    }
}
