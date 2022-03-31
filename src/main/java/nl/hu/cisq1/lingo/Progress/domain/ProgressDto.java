package nl.hu.cisq1.lingo.Progress.domain;

import nl.hu.cisq1.lingo.FeedbackDto.FeedbackDto;
import nl.hu.cisq1.lingo.Game.domain.GameState.GameState;
import nl.hu.cisq1.lingo.HintDto.HintDto;

import java.util.ArrayList;
import java.util.List;

public class ProgressDto {
    public long gameId;
    public long roundNumber;
    public int score;
    public GameState gState;
    public int lengthWord;
    public List<FeedbackDto> feedbackHistory = new ArrayList<>();
    public HintDto currentHint;

    public ProgressDto(long gameId, long roundNumber, int score, GameState gState, int lengthWord, List<FeedbackDto> feedbackHistory, HintDto currentHint) {
        this.gameId = gameId;
        this.roundNumber = roundNumber;
        this.score = score;
        this.gState = gState;
        this.lengthWord = lengthWord;
        this.feedbackHistory = feedbackHistory;
        this.currentHint = currentHint;
    }

    @Override
    public String toString() {
        return "ProgressDto{" +
                "gameId=" + gameId +
                ", roundNumber=" + roundNumber +
                ", score=" + score +
                ", gState=" + gState +
                ", lengthWord=" + lengthWord +
                ", feedbackHistory=" + feedbackHistory +
                ", currentHint=" + currentHint +
                '}';
    }
}
