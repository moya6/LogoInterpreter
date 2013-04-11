package com.example.logointerpreter;

import java.util.LinkedList;

import android.os.AsyncTask;
import android.text.Editable;
import com.example.exceptions.LogoScannerException;;

class Parser extends AsyncTask<Editable, Integer, LinkedList<String>> {
	
	Token[] reserved = { Token.FORWARD, Token.BACK, Token.RIGHT, Token.LEFT, Token.REPEAT };
	 
    private String rawContents;
    private String scanBuffer;
    private int idx;
    private char ch;
    
	public enum Token
	{
	    [TokenAsText("REPEAT")]
	    REPEAT,
	    [TokenAsText("FORWARD")]
	    FORWARD,
	    [TokenAsText("BACK")]
	    BACK,
	    [TokenAsText("LEFT")]
	    LEFT,
	    [TokenAsText("RIGHT")]
	    RIGHT,
	    [TokenAsText("NUMBER")]
	    NUMBER,
	    [TokenAsText("[")]
	    LBRACKET,
	    [TokenAsText("]")]
	    RBRACKET,
	    EOF
	}
	
	@Override
	protected LinkedList<String> doInBackground(Editable... code) {
		rawContents = code[0].toString();
		return null;
	}
	
	@Override
	protected void onProgressUpdate(Integer... values) { //
		super.onProgressUpdate(values);
		// Not used in this case
	}
	
	
	private LinkedList<String> parse(Editable code) {
		LinkedList<String> commandList = new LinkedList<String>();
		
		return commandList;
	}
 
    public Token Scan()
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
                Token lookup;
                if (LookupReserved(scanBuffer, lookup))
                {
                    return lookup;
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
 
    private void LexicalError()
    {
        throw new LogoScannerException(String.format("Lexical error at '{0}' ('{1}')", ch, scanBuffer));
    }
 
    private boolean LookupReserved(String s, Token lookup)
    {
        lookup = TokenHelper.LookupAsText(s);
        return reserved.Contains(lookup);
    }
    
	
}
