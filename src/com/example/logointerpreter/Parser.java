package com.example.logointerpreter;

import java.util.LinkedList;
import com.example.logointerpreter.Token;

import android.os.AsyncTask;
import android.text.Editable;
import com.example.exceptions.*;

class Parser extends AsyncTask<Void, Integer, LinkedList<String>> {
	
    private Scanner scanner;
    
    public Parser()
    {
        
    }
    
    @Override
	protected LinkedList<String> doInBackground(Void... params) {
		String token;
		while (true) {
			if (MainActivity.taskQueue.isEmpty()==false) {
				scanner = new Scanner(MainActivity.taskQueue.getFirst().toString());
				ParseLogoProgram();
			}
			
		}
	}
    
    @Override
	protected void onProgressUpdate(Integer... values) { //
		super.onProgressUpdate(values);
		// Not used in this case
	}
    
    
    public void ParseLogoProgram()
    {
        try {
			ParseLogoSentence();
		
	        while (true)
	        {
	            switch(scanner.NextToken())
	            {
	                case FORWARD:
	                case BACK:
	                case LEFT:
	                case RIGHT:
	                case REPEAT:
	                    ParseLogoSentence();
	                    break;
	 
	                default:
	                    Match(Token.EOF);
	                    return;
	            }
	        }
        } catch (SyntaxErrorException e) {
			// TODO Auto-generated catch block
			return;
		} catch (LogoScannerException e) {
			// TODO Auto-generated catch block
			return;
		}
    }
 
    
    private void ParseLogoSentence() throws SyntaxErrorException, LogoScannerException
    {
        Token nextToken = scanner.NextToken();
        switch(nextToken)
        {
            case FORWARD:
            case BACK:
            case LEFT:
            case RIGHT:
                Match(nextToken);
                Match(Token.NUMBER);
                break;
 
            case REPEAT:
                Match(nextToken);
                Match(Token.NUMBER);
                Match(Token.LBRACKET);
                ParseLogoSentence();
                Match(Token.RBRACKET);
                break;
 
            default:
                SyntaxError();
        }
    }
 
    private void Match(Token token) throws SyntaxErrorException, LogoScannerException
    {
        Token nextToken = scanner.Scan();
        if (nextToken != token)
        {
            SyntaxError();
        }
    }
    
    private void SyntaxError() throws SyntaxErrorException {
    	throw new SyntaxErrorException("Syntax Error");
    }
 
}
	
