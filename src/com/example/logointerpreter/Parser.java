package com.example.logointerpreter;

import java.util.LinkedList;

import com.example.logocommand.LogoCommand;
import com.example.logocommand.LogoMoveCommand;
import com.example.logocommand.LogoRepeatCommand;
import com.example.logocommand.LogoTurnCommand;
import com.example.logointerpreter.Token;

import android.os.AsyncTask;
import android.text.Editable;
import com.example.exceptions.*;

class Parser extends AsyncTask<Void, Integer, Void> {
	
    private Scanner scanner;
    //private LinkedList<LogoCommand> MainActivity.MainActivity.commands;
    
    
    
    
    public Parser()
    {
    	
    }
    
    @Override
	protected Void doInBackground(Void... params) {
		String token;
		
			if (MainActivity.taskQueue.isEmpty()==false) {
				scanner = new Scanner(MainActivity.taskQueue.poll().toString());
				ParseLogoProgram();
			}
			System.out.println(MainActivity.commands.size());
			//tutaj powinieneœ wywo³aæ metodê, która narysuje wynik parsowania
			return null;
			
		
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
                NumberRecord numberRecord = new NumberRecord(nextToken, scanner.scanBuffer);
                if (nextToken == Token.FORWARD || nextToken == Token.BACK)
                {
                  MainActivity.commands.add(new LogoMoveCommand(numberRecord));
                  System.out.println("doda³em ruch " + numberRecord.getNumber());
                }
                else
                {
                  MainActivity.commands.add(new LogoTurnCommand(numberRecord));
                  System.out.println("doda³em kierunek " + numberRecord.getNumber());
                }
                break;
 
            case REPEAT:
                Match(nextToken);
                Match(Token.NUMBER);
                NumberRecord numberRecord2 = new NumberRecord(nextToken, scanner.scanBuffer);
                Match(Token.LBRACKET);
                ParseLogoRepeatSentences(numberRecord2.getNumber());
                Match(Token.RBRACKET);
                break;
 
            default:
                SyntaxError();
        }
    }
    

    
    /*private LinkedList<LogoCommand> ParseLogoSentences() throws LogoScannerException
	{
    	LinkedList<LogoCommand> logoMainActivity.MainActivity.commands = new LinkedList<LogoCommand>();
		logoMainActivity.MainActivity.commands.add(ParseLogoSentence());
		for (Token token = scanner.NextToken(); IsSentencePrefix(token); token = scanner.NextToken())
		{
			result.Add(ParseLogoSentence());
		}
	}*/
    
    private void ParseLogoRepeatSentences(long times) throws SyntaxErrorException, LogoScannerException {
    	LinkedList<LogoCommand> tmp = new LinkedList<LogoCommand>();
    	
    	while (true) {
    		Token nextToken = scanner.NextToken();
	        switch(nextToken)
	        {
	        	case RBRACKET:
	        		MainActivity.commands.add(new LogoRepeatCommand(tmp, times));
	        		System.out.println("doda³em do repeat");
	        		return;
	            case FORWARD:
	            case BACK:
	            case LEFT:
	            case RIGHT:
	                Match(nextToken);
	                Match(Token.NUMBER);
	                NumberRecord numberRecord = new NumberRecord(nextToken, scanner.scanBuffer);
	                if (nextToken == Token.FORWARD || nextToken == Token.BACK)
	                {
	                  tmp.add(new LogoMoveCommand(numberRecord));
	                  
	                }
	                else
	                {
	                	tmp.add(new LogoTurnCommand(numberRecord));
	                	
	                }
	                break;
	 
	            case REPEAT:
	                Match(nextToken);
	                Match(Token.NUMBER);
	                NumberRecord numberRecord2 = new NumberRecord(nextToken, scanner.scanBuffer);
	                Match(Token.LBRACKET);
	                ParseLogoRepeatSentences(numberRecord2.getNumber());
	                Match(Token.RBRACKET);
	                break;
	 
	            default:
	                SyntaxError();
	        }
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
	
