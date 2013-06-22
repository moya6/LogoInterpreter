package com.jfik.logo.interpreter;

import java.util.LinkedList;

import android.text.Editable;

import com.jfik.logo.exceptions.ScannerException;

public class Scanner {
	Token[] reserved = { Token.FORWARD, Token.BACK, Token.RIGHT, Token.LEFT, Token.REPEAT };
	 
    private String code;
    public String buffer;
    private int idx;
    private char ch;
    private LinkedList<String> tokens = new LinkedList<String>();
    
    public Scanner(String contents)
    {
        code = contents;
    }
    
    
    
    public Token lookUpNextToken() throws ScannerException
    {
        int oldIdx = idx;
        Token result = Scan();
        idx = oldIdx;
        return result;
    }
    
 
    public Token Scan() throws ScannerException {
    	
        while (idx < code.length()) {
            ch = code.charAt(idx);
            if (ch == '$') {
            	return Token.EOF;
            }
            if (ch == '[') {
                idx++;
                return Token.LBRACKET;
            }
            else if (ch == ']') {
                idx++;
                return Token.RBRACKET;
            }
            else if (Character.isDigit(ch)) {
                buffer = new Character(ch).toString();
                idx++;
                while (idx < code.length()) {
                    ch = code.charAt(idx);
                    if (Character.isDigit(ch)) {
                        buffer += ch;
                        idx++;
                    }
                    else break;
                }
                return Token.NUMBER;
            }
            else if (Character.isLetter(ch)) {
                buffer = new Character(ch).toString();
                idx++;
                while (idx < code.length()) {
                    ch = code.charAt(idx);
                    if (Character.isLetter(ch)) {
                        buffer += ch;
                        idx++;
                    }
                    else break;
                }
                
                if (Token.contains(buffer)) {
                    return Token.valueOf(buffer);
                }
                LexicalError();
            }
            else if (Character.isWhitespace(ch)) {
                idx++;
            }
            else {
                LexicalError();
            }
        }
        return Token.EOF;
    }
 
    private void LexicalError() throws ScannerException
    {
        throw new ScannerException(String.format("Lexical error at '{0}' ('{1}')", ch, buffer));
    }
}


