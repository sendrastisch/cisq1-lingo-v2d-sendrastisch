package nl.hu.cisq1.lingo.trainer.domain.exception;

public class RoundIsNotPlaying extends RuntimeException {
    public RoundIsNotPlaying(String message) {
        super(message);
    }
}
