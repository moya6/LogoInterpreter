package com.jfik.logo.interpreter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Bundle;
import android.util.AttributeSet;

import com.example.logointerpreter.R;
import com.jfik.logo.command.*;
import com.jfik.logo.interpreter.Line;
import com.jfik.logo.marker.Marker;


public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback  {
	
	protected MainThread thread;
	private Marker marker;
	
	private ArrayList<Line> lines = new ArrayList<Line>();
	private Paint p;
	private Paint markerCoor;
	
	
	public MainGamePanel(Context context, long screenWidth, long screenHeight) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
		marker = new Marker(screenWidth, screenHeight);
		p = new Paint();
		p.setColor(Color.BLACK);
		markerCoor = new Paint();
		markerCoor.setColor(Color.GRAY);
	}

	public void pause() {
	    thread.setRunning(false);
	}

	public void resume() {  
	    thread.setRunning(true);
	}
	
	public void start() {
	    thread.setRunning(true);
	}
	
	public void destroy() {
	    thread.setRun(false);
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		// TODO Auto-generated method stub
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		thread.setRunning(true);
		thread.setRun(true);
		thread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				thread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	public void changeColor(int color) {
		switch (color) {
			case 0 : p.setColor(Color.WHITE); break;
			case 1 : p.setColor(Color.RED); break;
			case 2 : p.setColor(Color.GREEN); break;
			case 3 : p.setColor(Color.BLUE); break;
			default : p.setColor(Color.BLACK);
		}
					
	}

	protected void render(Canvas canvas) {
		
		canvas.drawColor(Color.WHITE);
		
        for (Line myLine : lines) {
        	changeColor(myLine.getColor());
            canvas.drawLine(myLine.getX1(), myLine.getY1(), myLine.getX2(), myLine.getY2(), p);
        }
        
        
        long x = marker.getX();
        long y = marker.getY();
        canvas.drawLine(x-10,y,x+10,y,markerCoor);
        canvas.drawLine(x,y-10,x,y+10,markerCoor);
        
	}
	
	protected void clear() {
		lines.clear();
		marker.reset();
	}

	public void addLine(long x1, long y1, long x2, long y2, int c) {
        Line newLine = new Line(x1, y1, x2, y2,c);
        System.out.println("dodalem linie");
        lines.add(newLine);
    }
	
	public void drawResults()
	{
		
		for (MarkerCommand c : MainActivity.commands) {
			System.out.println("DODAJE LINIE");
			marker.takeCommand(c);
			addLine(marker.getOldX(), marker.getOldY(), marker.getX(), marker.getY(), marker.getColor());
		}
		
	}

}

