package nl.hu.cisq1.lingo.Hint.domain;

public class Hint {
    private String hint;

    public Hint(String hnt){
        hint = hnt;
    }

    public String getHint() {
        return hint;
    }

    @Override
    public String toString() {
        return hint;
    }
}
