package nl.hu.cisq1.lingo.Hint.domain;

import nl.hu.cisq1.lingo.Mark.Mark;

import java.util.List;

public class Hint {
    private String hint;

    //Will be used to show first letter
    public Hint(String wordToGuess){
        this.hint = wordToGuess.charAt(0) + ".".repeat(wordToGuess.length()-1);
    }

    //will create new hint based on previous hint and the existing list of marks.
    public Hint(Hint previousHint, List<Mark> marks, String attempt){

        int indexMark = -1;
        //This is where I create the hint String
        StringBuilder hintPuzzle = new StringBuilder();

        //This is the string of the last hint
        String lastHintString = previousHint.getHint();

        //This is the list of chars of the previous hint.
        char[] splitHint = lastHintString.toCharArray();

        //Loop through list of marks and check whether its correct. If its not correct, it'll add a dot to the hint.
        for(Mark m: marks){
            indexMark +=1;
            if(m == Mark.CORRECT){
                char a = attempt.charAt(indexMark);
                hintPuzzle.append(a);
            } else{
                hintPuzzle.append(".");
            }
        }

        //This loop will combine the hints given from the guess with the previous hint.
        for(int i = 0; i < splitHint.length; i++){
            if(splitHint[i] != '.'){
                hintPuzzle.replace(i, i+1 , String.valueOf(splitHint[i]));
            }
        }
       hint = hintPuzzle.toString();
    }

    public String getHint() {
        return hint;
    }



    @Override
    public String toString() {
        return hint;
    }
}