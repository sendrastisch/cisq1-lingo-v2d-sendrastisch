package nl.hu.cisq1.lingo.Game;

import nl.hu.cisq1.lingo.Game.GameState.GameState;
import nl.hu.cisq1.lingo.Game.exception.RoundCannotBeStartedException;
import nl.hu.cisq1.lingo.Game.exception.RoundIsNotPlaying;
import nl.hu.cisq1.lingo.Round.RoundState.RoundState;
import nl.hu.cisq1.lingo.Round.domain.Round;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameState gState = GameState.WAITING_FOR_ROUND;
    private int lengthWord = 5;
    private int score;
    private List<Round> rounds = new ArrayList<>();

    public Game() {

    }

    public int getScore() {
        return score;
    }

    public void startNewRound(String wordToGuess){

        if(rounds.size() != 0 && rounds.get(rounds.size()-1).getState() != RoundState.WON ){
            throw new RoundCannotBeStartedException("The round cannot be started.");
        }

        gState = GameState.PLAYING;
        Round round = new Round(wordToGuess);
        rounds.add(round);

    }

    public void takeGuess(String guess){
        Round round = rounds.get(rounds.size()-1);

        if(round.getState() != RoundState.PLAYING){
            throw new RoundIsNotPlaying("There is no round that has been started");
        } else{
            round.takeGuess(guess);
            if(round.getState() == RoundState.WON){
                score += 5 * (5 - round.getFeedbackList().size()) + 5;
                changeWordLength();
            } else if(round.getState() == RoundState.LOST){
                gState = GameState.LOST;
            }
        }
    }

    private void changeWordLength(){
        switch (lengthWord) {
            case 5 : lengthWord = 6;
            case 6 : lengthWord = 7;
            case 7 : lengthWord = 5;
            default : lengthWord = 5;
        }
    }
}
