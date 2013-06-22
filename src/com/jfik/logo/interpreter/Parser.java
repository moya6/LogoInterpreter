package com.jfik.logo.interpreter;

import java.util.LinkedList;


import android.os.AsyncTask;
import android.text.Editable;

import com.jfik.logo.command.MarkerColorCommand;
import com.jfik.logo.command.MarkerCommand;
import com.jfik.logo.command.MarkerMoveCommand;
import com.jfik.logo.command.MarkerTurnCommand;
import com.jfik.logo.exceptions.*;
import com.jfik.logo.interpreter.Token;

class Parser {
	
    private Scanner scanner;
    LinkedList<MarkerCommand> commands;
    
    public Parser() {
    	
    }
    
    
	protected void execute() {
		String token;
		
		if (!MainActivity.taskQueue.equals("")) {
			scanner = new Scanner(MainActivity.taskQueue);
			ParseLogoProgram();
		}
		System.out.println(MainActivity.commands.size());
			
	}
    
    
    public void ParseLogoProgram()
    {
        try {
			ParseNext();
		
	        while (true)
	        {
	            switch(scanner.lookUpNextToken())
	            {
	                case FORWARD:
	                case BACK:
	                case LEFT:
	                case RIGHT:
	                case COLOR:
	                case REPEAT:
	                    ParseNext();
	                    break;
	                case CLEAR:
	                	MainActivity.clrscr = true;
	                default:
	                    checkNextToken(Token.EOF);
	                    
	                    return;
	            }
	        }
        } catch (SyntaxErrorException e) {
			// TODO Auto-generated catch block
			return;
		} catch (ScannerException e) {
			// TODO Auto-generated catch block
			return;
		}
    }
 
    
    private void ParseNext() throws SyntaxErrorException, ScannerException {
        Token lookUpNextToken = scanner.lookUpNextToken();
        
        switch(lookUpNextToken) {
            case FORWARD:
            case BACK:
            case LEFT:
            case RIGHT:
            case COLOR:
                checkNextToken(lookUpNextToken);
                checkNextToken(Token.NUMBER);
                LogoNumber numberRecord = new LogoNumber(lookUpNextToken, scanner.buffer);
                if (lookUpNextToken == Token.FORWARD || lookUpNextToken == Token.BACK) {
                  MainActivity.commands.add(new MarkerMoveCommand(numberRecord));
                  System.out.println("doda³em ruch " + numberRecord.getNumber());
                }
                else if (lookUpNextToken == Token.COLOR) {
                	MainActivity.commands.add(new MarkerColorCommand(numberRecord));	
                }
                else {
                  MainActivity.commands.add(new MarkerTurnCommand(numberRecord));
                  System.out.println("doda³em kierunek " + numberRecord.getNumber());
                }
                break;
 
            case REPEAT:
                checkNextToken(lookUpNextToken);
                checkNextToken(Token.NUMBER);
                LogoNumber numberRecord2 = new LogoNumber(lookUpNextToken, scanner.buffer);
                checkNextToken(Token.LBRACKET);
                ParseNextRepeat(numberRecord2.getNumber());
                checkNextToken(Token.RBRACKET);
                break;
            case CLEAR:
            	MainActivity.clrscr = true;
            default:
                SyntaxError();
        }
    }
   
    private void ParseNextRepeat(long times) throws SyntaxErrorException, ScannerException {
    	LinkedList<MarkerCommand> tmp = new LinkedList<MarkerCommand>();
    	
    	while (true) {
    		Token lookUpNextToken = scanner.lookUpNextToken();
	        switch(lookUpNextToken) {
	        	case RBRACKET:
	        		for (int i=0;i<times;i++) {
	        			for (MarkerCommand c : tmp) {
	        				MainActivity.commands.add(c);
	        			}
	        		}
	        		System.out.println("doda³em do repeat");
	        		return;
	            case FORWARD:
	            case BACK:
	            case LEFT:
	            case RIGHT:
	                checkNextToken(lookUpNextToken);
	                checkNextToken(Token.NUMBER);
	                LogoNumber numberRecord = new LogoNumber(lookUpNextToken, scanner.buffer);
	                if (lookUpNextToken == Token.FORWARD || lookUpNextToken == Token.BACK) {
	                  tmp.add(new MarkerMoveCommand(numberRecord));
	                  
	                }
	                else {
	                	tmp.add(new MarkerTurnCommand(numberRecord));	
	                }
	                break;
	 
	            case REPEAT:
	                checkNextToken(lookUpNextToken);
	                checkNextToken(Token.NUMBER);
	                LogoNumber numberRecord2 = new LogoNumber(lookUpNextToken, scanner.buffer);
	                checkNextToken(Token.LBRACKET);
	                ParseNextRepeat(numberRecord2.getNumber());
	                checkNextToken(Token.RBRACKET);
	                break;
	 
	            default:
	                SyntaxError();
	        }
    	}
    	
    }
 
    private void checkNextToken(Token token) throws SyntaxErrorException, ScannerException {
        Token lookUpNextToken = scanner.Scan();
        
        if (lookUpNextToken != token) {
            SyntaxError();
        }
    }
    
    private void SyntaxError() throws SyntaxErrorException {
    	throw new SyntaxErrorException("Syntax Error");
    }
 
}
	
