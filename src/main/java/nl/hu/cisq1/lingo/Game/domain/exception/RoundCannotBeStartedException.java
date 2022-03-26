package nl.hu.cisq1.lingo.Game.domain.exception;

public class RoundCannotBeStartedException extends RuntimeException{
    public RoundCannotBeStartedException(String message) {
        super(message);
    }
}
