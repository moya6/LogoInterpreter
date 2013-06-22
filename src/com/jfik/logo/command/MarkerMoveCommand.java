package com.jfik.logo.command;

import com.jfik.logo.interpreter.LogoNumber;

public class MarkerMoveCommand extends MarkerCommand {
	private long distance;
	
	public MarkerMoveCommand(LogoNumber numberRecord)
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
