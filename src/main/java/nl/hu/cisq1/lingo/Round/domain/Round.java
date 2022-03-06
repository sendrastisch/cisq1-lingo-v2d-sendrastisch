package nl.hu.cisq1.lingo.Round.domain;

public class Round {
    private String wordToGuess;
    private String guess;

    public Round(String wrToGs, String gs){
        wordToGuess = wrToGs;
        guess = gs;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }

    public String getGuess() {
        return guess;
    }


}
