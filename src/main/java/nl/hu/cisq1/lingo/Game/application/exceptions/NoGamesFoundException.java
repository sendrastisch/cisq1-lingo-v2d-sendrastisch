package nl.hu.cisq1.lingo.Game.application.exceptions;

public class NoGamesFoundException extends RuntimeException {
    public NoGamesFoundException(String message) {
        super(message);
    }
}
