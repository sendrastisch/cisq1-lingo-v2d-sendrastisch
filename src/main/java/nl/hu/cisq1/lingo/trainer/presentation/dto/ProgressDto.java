package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.presentation.dto.FeedbackDto;
import nl.hu.cisq1.lingo.trainer.domain.enums.GameState;
import nl.hu.cisq1.lingo.trainer.presentation.dto.HintDto;

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
}
