package nl.hu.cisq1.lingo.trainer.domain;

import nl.hu.cisq1.lingo.trainer.domain.exception.InvalidFeedbackException;
import nl.hu.cisq1.lingo.trainer.presentation.dto.FeedbackDto;
import nl.hu.cisq1.lingo.trainer.domain.enums.Mark;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
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

    public Feedback() {

    }

    public Feedback(String atmt, List<Mark> mrs) {
        attempt = atmt;
        marks = mrs;

        if (atmt.length() != mrs.size()) {
            throw new InvalidFeedbackException();
        }
    }

    public Feedback(String atmt) {
        attempt = atmt;
    }

    public FeedbackDto getFeedbackDto() {
        return new FeedbackDto(this.id, this.attempt, this.getMarks());
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public boolean isWordGuessed() {
        boolean guessed = true;

        for (Mark m : marks) {
            if (m != Mark.CORRECT) {
                guessed = false;
            }
        }
        return guessed;
    }

    public boolean isWordInvalid() {
        boolean invalid = false;

        for (Mark m : marks) {
            if (m == Mark.INVALID) {
                invalid = true;
                break;
            }
        }
        return invalid;
    }

    public void createListMarks(String wordToGuess) {
        List<Mark> list = new ArrayList<>();

        if (wordToGuess.equals(this.attempt)) {
            list = Stream.generate(() -> Mark.CORRECT).limit(this.attempt.length()).collect(Collectors.toList());
        } else if (wordToGuess.length() != this.attempt.length()) {
            list = Stream.generate(() -> Mark.INVALID).limit(wordToGuess.length()).collect(Collectors.toList());
        } else {
            //This is the word to guess split into an array of chars
            char[] splitwordToGuess = wordToGuess.toCharArray();
            List<Character> listWordToGuess = wordToGuess.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

            //This is the users guess split into an array of chars
            char[] splitAttempt = this.attempt.toCharArray();
            List<Character> listAttempt = this.attempt.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
            List<Character> listMaybePresent = new ArrayList<>(listWordToGuess);

            //if the character is correct and at the right index it gets the mark correct
            for (int i = 0; i < splitwordToGuess.length; i++) {
                if (splitwordToGuess[i] == splitAttempt[i]) {
                    list.add(Mark.CORRECT);
                    listMaybePresent.remove((Character) splitAttempt[i]);
                } else {
                    list.add(Mark.ABSENT);
                }
            }

            //loop through the remainder of the characters that werent correct and see which mark they should get
            for (int i = 0; i < listAttempt.size(); i++) {
                if (list.get(i) == Mark.ABSENT) {
                    if (listMaybePresent.contains(listAttempt.get(i))) {
                        list.set(i, Mark.PRESENT);
                        listMaybePresent.remove(listAttempt.get(i));
                    }
                }
            }
        }
        this.setMarks(list);
    }

    public Hint generateHint(Hint previousHint, String attempt) {
        return new Hint(previousHint, marks, attempt);
    }

}
