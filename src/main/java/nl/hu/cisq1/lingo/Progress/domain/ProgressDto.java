package nl.hu.cisq1.lingo.Progress.domain;

import nl.hu.cisq1.lingo.Feedback.domain.Feedback;
import nl.hu.cisq1.lingo.Game.domain.GameState.GameState;
import nl.hu.cisq1.lingo.Hint.domain.Hint;

import java.util.ArrayList;
import java.util.List;

public class ProgressDto {
    private long gameId;
    private long roundNumber;
    private int score;
    private GameState gState;
    private int lengthWord;
    private List<Feedback> feedbackHistory = new ArrayList<>();
    private Hint currentHint;

    public ProgressDto(long gameId, long roundNumber, int score, GameState gState, int lengthWord, List<Feedback> feedbackHistory, Hint currentHint) {
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.score = score;
        this.gState = gState;
        this.lengthWord = lengthWord;
        this.feedbackHistory = feedbackHistory;
        this.currentHint = currentHint;
    }

    public Hint getCurrentHint() {
        return currentHint;
    }

    public GameState getgState() {
        return gState;
    }

    public int getLengthWord() {
        return lengthWord;
    }
}
