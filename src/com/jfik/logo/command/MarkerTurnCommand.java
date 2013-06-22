package com.jfik.logo.command;

import com.jfik.logo.interpreter.LogoNumber;

public class MarkerTurnCommand extends MarkerCommand {
private long angle;
	
	public MarkerTurnCommand(LogoNumber numberRecord)
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
