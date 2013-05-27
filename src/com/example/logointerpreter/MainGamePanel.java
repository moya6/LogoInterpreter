package com.example.logointerpreter;

import java.util.Random;
import java.util.Vector;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Paint.Align;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback  {

	protected MainThread thread;
	
	public MainGamePanel(Context context) {
		super(context);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
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
		thread.setRunning(false);
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

	protected void render(Canvas canvas) {
	}

	


}