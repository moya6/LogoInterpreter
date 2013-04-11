package com.example.logointerpreter;

import java.util.LinkedList;

import android.os.AsyncTask;
import android.text.Editable;
import com.example.exceptions.LogoScannerException;;

class Parser extends AsyncTask<Void, Integer, LinkedList<String>> {
	
	public Parser() {
		
	}
	Token[] reserved = { Token.FORWARD, Token.BACK, Token.RIGHT, Token.LEFT, Token.REPEAT };
	 
    private String rawContents;
    private String scanBuffer;
    private int idx;
    private char ch;
    private LinkedList<String> tokens = new LinkedList<String>();
    
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
    
	
	@Override
	protected LinkedList<String> doInBackground(Void... params) {
		String token;
		while (true) {
			if (MainActivity.taskQueue.isEmpty()==false) {
				rawContents = MainActivity.taskQueue.getFirst().toString();
				try {
					while ((token = Scan().toString())!="EOF") {
						tokens.add(token);
					}
				}
				catch(LogoScannerException e) {
					
				}
			}
			
		}
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
