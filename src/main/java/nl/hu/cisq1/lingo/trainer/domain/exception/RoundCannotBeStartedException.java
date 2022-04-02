package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundCannotBeStartedException extends RuntimeException {
    public RoundCannotBeStartedException(String message) {
        super(message);
    }
}
