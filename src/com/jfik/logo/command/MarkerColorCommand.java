package com.jfik.logo.command;

import com.jfik.logo.interpreter.LogoNumber;

public class MarkerColorCommand extends MarkerCommand {
	private int color;
	
	public MarkerColorCommand(LogoNumber numberRecord)
	{
		color = numberRecord.getNumber();
	}
	
	public int getColor() {
		return color;
	}
}
