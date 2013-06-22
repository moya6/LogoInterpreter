package com.jfik.logo.marker;

import com.jfik.logo.command.MarkerCommand;
import com.jfik.logo.interpreter.MainActivity;

public class Marker implements Cloneable {

		private long angle;
		private long oldX;
		private long oldY;
		private long x;
		private long y;
		private long screenWidth;
		private long screenHeight;
		private int color;
		
		public Marker(long screenWidth, long screenHeight) {
			angle = 0;
			x = screenWidth/2;
			y = screenHeight/2;
			oldX = x;
			oldY = y;
			this.screenHeight = screenHeight;
			this.screenWidth = screenWidth;
		}
		
		public int getColor() {
			return this.color;
		}
		
		public void reset() {
			x = screenWidth/2;
			y = screenHeight/2;
			angle = 0;
		}
		
		public long getAngle() {
			return this.angle;
		}
		
		public void setAngle(long angle) {
			this.angle = (this.angle + angle)%360;
			System.out.println("changed angle to "+ angle);
		}
		
		public long getX() {
			return this.x;
		}
		
		public void setX(long x) {
			this.x = x;
		}
		
		public void addX(long x) {
			this.oldX = this.x;
			this.x = (this.x+x)%this.screenWidth;
		}
		
		public long getY() {
			return this.y;
		}
		
		public void setY(long y) {
			this.y = y;
		}
		
		public void addY(long y) {
			this.oldY = this.y;
			this.y = (this.y+y)%this.screenHeight;
		}
		
		public long getOldY() {
			return oldY;
		}
		
		public long getOldX() {
			return oldX;
		}
		
		public void setPosition(long distance) {
			long newX = Math.round(distance*Math.cos(Math.toRadians(getAngle())));
			long newY = Math.round(distance*Math.sin(Math.toRadians(getAngle())));
			addX(newX);
			addY(newY);
			System.out.println("moved to "+ x + " " + y);
		}
		
		public void takeCommand(MarkerCommand command) {
			String className = command.getClass().getSimpleName();
			System.out.println(className);
			if (className.equals("MarkerMoveCommand")) {
				this.setPosition(command.getDistance());
			}
			else if (className.equals("MarkerColorCommand")) {
				this.color = command.getColor();
			}
			else if (className.equals("MarkerTurnCommand")) {
				this.setAngle(command.getAngle());
			}
			
		}

		
}
