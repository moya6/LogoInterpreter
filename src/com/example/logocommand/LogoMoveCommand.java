package com.example.logocommand;

import com.example.logointerpreter.NumberRecord;

public class LogoMoveCommand implements LogoCommand {
	private long distance;
	
	public LogoMoveCommand(NumberRecord numberRecord)
	{
		distance = numberRecord.getNumber();
	}
	
	public long getDistance() {
		return this.distance;
	}
	
	public String toString() {
		return Long.toString(distance);
	}
	
}
