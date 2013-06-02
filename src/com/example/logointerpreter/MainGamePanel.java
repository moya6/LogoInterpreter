package com.example.logointerpreter;

import java.util.Random;
import java.util.Vector;

import com.example.turtle.TurtleSituation;

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

public class MainGamePanel extends SurfaceView implements SurfaceHolder.Callback  {

	public MainGamePanel(Context context, AttributeSet attrs) {
	    super(context, attrs);
		getHolder().addCallback(this);
		setFocusable(true);
		thread = new MainThread(getHolder(), this);
	}
	
	protected MainThread thread;
	
	TurtleSituation turtle;
	
	private Paint textPaint;
	private Paint rectPaint;
	Bitmap turtleBmp = BitmapFactory.decodeResource(getResources(), R.drawable.turtle);
	
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
		
		textPaint = new Paint();
		rectPaint = new Paint();
		textPaint.setARGB(200, 255, 55, 55);
		rectPaint.setARGB(255, 0, 55, 55);
		
		turtle = new TurtleSituation();
		
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
		
		canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(turtleBmp, 0, 0, null);
		canvas.drawText("Dzien: ", 2, 100, textPaint);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
	// TODO Auto-generated method stub
	 super.onDraw(canvas);
	 		
	 canvas.drawColor(Color.WHITE);
		canvas.drawBitmap(turtleBmp, 0, 0, null);
	 //canvas.drawCircle(50, 50, 30, textPaint);
	 
	}


	


}