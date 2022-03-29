package nl.hu.cisq1.lingo.FeedbackDto;

import nl.hu.cisq1.lingo.Mark.Mark;

import java.util.List;

public class FeedbackDto {
    public long feedbackId;
    public String attempt;
    public List<Mark> marks;

    public FeedbackDto(long feedbackId, String attempt, List<Mark> marks){
        this.feedbackId = feedbackId;
        this.attempt = attempt;
        this.marks = marks;
    }
}
