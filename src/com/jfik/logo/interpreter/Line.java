package com.jfik.logo.interpreter;

public class Line {

	private long x1;
	private long x2;
	private long y1;
	private long y2;
	private int color;
	
    public Line(long x1, long y1, long x2, long y2, int color) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.color = color;
    }
    
    public int getColor() {
    	return this.color;
    }
    
    public long getX1() {
    	return x1;
    }
    
    public long getX2() {
    	return x2;
    }
       
    public long getY1() {
    	return y1;
    }
       
    public long getY2() {
    	return y2;
    }
       
}
