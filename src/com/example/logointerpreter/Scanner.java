package com.example.logointerpreter;

import java.util.LinkedList;

import android.text.Editable;

import com.example.exceptions.LogoScannerException;

public class Scanner {
	Token[] reserved = { Token.FORWARD, Token.BACK, Token.RIGHT, Token.LEFT, Token.REPEAT };
	 
    private String rawContents;
    private String scanBuffer;
    private int idx;
    private char ch;
    private LinkedList<String> tokens = new LinkedList<String>();
    
    public Scanner(String contents)
    {
        rawContents = contents;
    }
    
    
    
    public Token NextToken() throws LogoScannerException
    {
        int oldIdx = idx;
        Token result = Scan();
        idx = oldIdx;
        return result;
    }
    
	private LinkedList<String> parse(Editable code) {
		LinkedList<String> commandList = new LinkedList<String>();
		
		return commandList;
	}
 
    public Token Scan() throws LogoScannerException
    {
        while (idx < rawContents.length())
        {
            ch = rawContents.charAt(idx);
            if (ch == '[')
            {
                idx++;
                return Token.LBRACKET;
            }
            else if (ch == ']')
            {
                idx++;
                return Token.RBRACKET;
            }
            else if (Character.isDigit(ch))
            {
                scanBuffer = new Character(ch).toString();
                idx++;
                while (idx < rawContents.length())
                {
                    ch = rawContents.charAt(idx);
                    if (Character.isDigit(ch))
                    {
                        scanBuffer += ch;
                        idx++;
                    }
                    else break;
                }
                return Token.NUMBER;
            }
            else if (Character.isLetter(ch))
            {
                scanBuffer = new Character(ch).toString();
                idx++;
                while (idx < rawContents.length())
                {
                    ch = rawContents.charAt(idx);
                    if (Character.isLetter(ch))
                    {
                        scanBuffer += ch;
                        idx++;
                    }
                    else break;
                }
                
                if (Token.contains(scanBuffer))
                {
                    return Token.valueOf(scanBuffer);
                }
                LexicalError();
            }
            else if (Character.isWhitespace(ch))
            {
                idx++;
            }
            else
            {
                LexicalError();
            }
        }
        return Token.EOF;
    }
 
    private void LexicalError() throws LogoScannerException
    {
        throw new LogoScannerException(String.format("Lexical error at '{0}' ('{1}')", ch, scanBuffer));
    }
}


