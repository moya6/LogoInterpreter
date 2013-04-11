package com.example.logointerpreter;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	
		private Object mPauseLock = new Object();  
		private boolean mPaused;
		// desired fps
		private final static int    MAX_FPS = 40;
		// maximum number of frames to be skipped
		private final static int    MAX_FRAME_SKIPS = 5;
		// the frame period
		private final static int    FRAME_PERIOD = 1000 / MAX_FPS; 

		private SurfaceHolder surfaceHolder ;
		private MainGamePanel gamePanel ;
		private boolean run ;
		private boolean running ;
		
		public MainThread ( SurfaceHolder surfaceHolder ,  MainGamePanel gamePanel ) {
			super ();
			this . surfaceHolder = surfaceHolder ;
			this . gamePanel = gamePanel ;
			//run = true;
		}
		
		public void setRunning ( boolean running ) {
			this . running = running ;
		}
		
		public void setRun ( boolean run) {
			this . run= run ;
		}
		
		
		public void run () {
			Canvas canvas ;
			long beginTime;     // the time when the cycle begun
			long timeDiff;      // the time it took for the cycle to execute
			int sleepTime;      // ms to sleep (<0 if we're behind)
			int framesSkipped;  // number of frames being skipped
			 
			sleepTime = 0;
			canvas = null;
			try {
				canvas = this . surfaceHolder . lockCanvas ();
				synchronized ( surfaceHolder ) {
					this.gamePanel.render(canvas);
				}
			}
			finally {
				if ( canvas != null ) {
					surfaceHolder . unlockCanvasAndPost ( canvas );
				}
			}
			while (run) {
				if(running){
				canvas = null ;
				try {
					canvas = this . surfaceHolder . lockCanvas ();
					synchronized ( surfaceHolder ) {
						beginTime = System.currentTimeMillis();
						framesSkipped = 0;
						this.gamePanel.update();
						this.gamePanel.render(canvas);
						
						synchronized (mPauseLock) {
						    while (mPaused) {
						        try {
						            mPauseLock.wait();
						        } catch (InterruptedException e) {
						        }
						    }
						}
						
						// calculate how long did the cycle take
						timeDiff = System.currentTimeMillis() - beginTime;
						// calculate sleep time
						sleepTime = (int)(FRAME_PERIOD - timeDiff);
						 
						if (sleepTime > 0) {
							// if sleepTime > 0 we're OK
						    try {
						    	// send the thread to sleep for a short period
						        // very useful for battery saving
						        Thread.sleep(sleepTime);
						        } catch (InterruptedException e) {}
						}
						 
						while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
							// we need to catch up
						    // update without rendering
						    this.gamePanel.update();
						    // add frame period to check if in next frame
						    sleepTime += FRAME_PERIOD;
						    framesSkipped++;
						}
					}
				} finally {
					if ( canvas != null ) {
						surfaceHolder . unlockCanvasAndPost ( canvas );
					}
				}
				}
			}
		}
		
	}