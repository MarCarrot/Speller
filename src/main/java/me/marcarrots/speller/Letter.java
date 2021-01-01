package me.marcarrots.speller;

public class Letter {

    private char letter;

    public String getPattern() {
        return pattern;
    }

    private String pattern;

    public Letter(char letter, String pattern) {
        this.letter = letter;
        this.pattern = pattern;
    }
}
