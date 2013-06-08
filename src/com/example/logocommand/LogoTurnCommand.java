package com.example.logocommand;

import com.example.logointerpreter.NumberRecord;

public class LogoTurnCommand extends LogoCommand {
private long angle;
	
	public LogoTurnCommand(NumberRecord numberRecord)
	{
		angle = numberRecord.getNumber();
	}
	
	public long getAngle() {
		return this.angle;
	}
	
	public String toString() {
		return Long.toString(angle);
	}
}
