package nl.hu.cisq1.lingo.Feedback.domain;

import nl.hu.cisq1.lingo.Hint.domain.Hint;
import nl.hu.cisq1.lingo.Mark.Mark;
import nl.hu.cisq1.lingo.Feedback.domain.exception.InvalidFeedbackException;

import java.util.List;
import java.util.Objects;

public class Feedback {
    private String attempt;
    private List<Mark> marks;
    private Hint hint;

    public Feedback(String atmt, List<Mark> mrs ){
        attempt = atmt;
        marks = mrs;

        if(atmt.length() != mrs.size()){
            throw new InvalidFeedbackException();
        }
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

    public Hint giveHint(Hint previousHint){

        int indexMark = -1;
        StringBuilder hintPuzzel = new StringBuilder();
        String vorigeHint = previousHint.getHint();
        char[] gesplitHint = vorigeHint.toCharArray();

        for(Mark m: marks){
            indexMark +=1;
            if(m == Mark.CORRECT){
                char a = attempt.charAt(indexMark);
                hintPuzzel.append(a);
            } else{
                hintPuzzel.append(".");
            }
        }

        for(int i = 0; i < gesplitHint.length; i++){
            if(gesplitHint[i] != '.'){
                String ding = String.valueOf(gesplitHint[i]);
                hintPuzzel.replace(i, i+1 , ding);
            }
        }

        return new Hint(String.valueOf(hintPuzzel));
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
