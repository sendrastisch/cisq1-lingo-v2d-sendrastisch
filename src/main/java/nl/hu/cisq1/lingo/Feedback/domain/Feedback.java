package nl.hu.cisq1.lingo.Feedback.domain;

import nl.hu.cisq1.lingo.Hint.domain.Hint;
import nl.hu.cisq1.lingo.Mark.Mark;
import nl.hu.cisq1.lingo.Feedback.domain.exception.InvalidFeedbackException;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Feedback {
    @Id
    @GeneratedValue
    private long id;

    private String attempt;

    @ElementCollection
    private List<Mark> marks;

    public Feedback(){

    }

    public Feedback(String atmt, List<Mark> mrs ){
        attempt = atmt;
        marks = mrs;

        if(atmt.length() != mrs.size()){
            throw new InvalidFeedbackException();
        }
    }

    public Feedback(String atmt){
        attempt = atmt;
    }

    public String getAttempt() {
        return attempt;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public boolean isWordGuessed(){
        boolean guessed = true;

        for (Mark m: marks){
            if(m != Mark.CORRECT){
                guessed = false;
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

    //This function creates a list of marks for a word
    public void createListMarks(String wordToGuess){
        List<Mark> list = new ArrayList<>();
        
        if(wordToGuess.equals(this.attempt)){
            list = Stream.generate(() -> Mark.CORRECT).limit(this.attempt.length()).collect(Collectors.toList());
        }
        else if(wordToGuess.length() != this.attempt.length()){
            list = Stream.generate(() -> Mark.INVALID).limit(wordToGuess.length()).collect(Collectors.toList());
        }
        else{
            //This is the word to guess split into an array of chars
            char[] splitwordToGuess = wordToGuess.toCharArray();
            List<Character> listWordToGuess = wordToGuess.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

            //This is the users guess split into an array of chars
            char[] splitAttempt = this.attempt.toCharArray();
            List<Character> listAttempt = this.attempt.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            List<Character> listMaybePresent = new ArrayList<>(listWordToGuess);

            //if the character is correct and at the right index it gets the mark correct
            for(int i = 0; i < splitwordToGuess.length; i++){
                if(splitwordToGuess[i] == splitAttempt[i]){
                    list.add(Mark.CORRECT);
                    listMaybePresent.remove((Character) splitAttempt[i]);
                }
                else{
                    list.add(Mark.ABSENT);
                }
            }

            //loop through the remainder of the characters that werent correct and see which mark they should get
            for(int i = 0; i < listAttempt.size(); i++){
                if(list.get(i) == Mark.ABSENT){
                    if(listMaybePresent.contains(listAttempt.get(i))){
                        list.set(i, Mark.PRESENT);
                        listMaybePresent.remove(listAttempt.get(i));
                    }
                }
            }
        }
        this.setMarks(list);
    }

    public Hint generateHint(Hint previousHint, String attempt){
        return new Hint(previousHint, marks, attempt);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(attempt, feedback.attempt) && Objects.equals(marks, feedback.marks);
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "attempt='" + attempt + '\'' +
                ", marks=" + marks +
                '}';
    }
}
