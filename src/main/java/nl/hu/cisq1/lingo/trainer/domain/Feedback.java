package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.Mark.Mark;

import java.util.List;

public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String atmt, List<Mark> mrs ){
        attempt = atmt;
        marks = mrs;
    }
}
