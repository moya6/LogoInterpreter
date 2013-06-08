package com.example.logocommand;

import java.util.LinkedList;

public class LogoRepeatCommand extends LogoCommand {
	private LinkedList<LogoCommand> list;
	private long times;
	
	public LogoRepeatCommand(LinkedList<LogoCommand> list, long times) {
		this.list = list;
		this.times = times;
		System.out.println(this.toString());
	}
	
	public LinkedList<LogoCommand> getList() {
		return list;
	}
	
	public long getTimes() {
		return times;
	}
	
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (LogoCommand l : list) {
			result.append(l+" ");
		}
		result.append(times);
		return result.toString();
	}
}
