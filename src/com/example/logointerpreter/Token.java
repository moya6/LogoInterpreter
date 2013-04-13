package com.example.logointerpreter;

public enum Token {
    REPEAT("REPEAT"),
    FORWARD("FORWARD"),
    BACK("BACK"),
    LEFT("LEFT"),
    RIGHT("RIGHT"),
    NUMBER("NUMBER"),
    LBRACKET("["),
    RBRACKET("]"),
    EOF("EOF");
   
    private Token(final String text) {
        this.text = text;
    }

    private final String text;

    @Override
    public String toString() {
        return text;
    }
    
    public static boolean contains(String test) {

        for (Token c : Token.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }

        return false;
    }
}