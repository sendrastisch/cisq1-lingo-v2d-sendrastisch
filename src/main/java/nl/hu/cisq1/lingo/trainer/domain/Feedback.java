package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.Mark.Mark;
import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private List<Mark> marks;

    public Feedback(String atmt, List<Mark> mrs ){
        attempt = atmt;
        marks = mrs;
    }

    public boolean isWordGuessed(){
        boolean guessed = false;

        for (Mark m: marks){
            if(m == Mark.ABSENT | m == Mark.INVALID | m == Mark.PRESENT){
                guessed = false;
                break;
            }
            else{
                guessed = true;
            }
        }
        return guessed;
    }

    public boolean isWordInvalid(){
        boolean invalid = false;

        for (Mark m: marks){
            if(m == Mark.INVALID){
                invalid = true;
                break;
            }
        }
        return invalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public int hashCode() {
        return Objects.hash(attempt, marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }
}
