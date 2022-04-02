package nl.hu.cisq1.lingo.trainer.presentation.dto;

import nl.hu.cisq1.lingo.trainer.domain.enums.Mark;

import java.util.List;

public class FeedbackDto {
    public long feedbackId;
    public String attempt;
    public List<Mark> marks;

    public FeedbackDto(long feedbackId, String attempt, List<Mark> marks) {
        this.feedbackId = feedbackId;
        this.attempt = attempt;
        this.marks = marks;
    }
}
