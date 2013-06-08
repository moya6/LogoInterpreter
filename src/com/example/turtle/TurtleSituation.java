package com.example.turtle;

import com.example.logocommand.LogoCommand;

public class TurtleSituation implements Cloneable {

		private long angle;
		private long x;
		private long y;
		
		public TurtleSituation() {
			angle = 0;
			x = 100;
			y = 100;
		}
		
		public long getAngle() {
			return this.angle;
		}
		
		public void setAngle(long angle) {
			this.angle += angle;
			System.out.println("changed angle to "+ angle);
		}
		
		public long getX() {
			return this.x;
		}
		
		public void setX(long x) {
			this.x = x;
		}
		
		public void addX(long x) {
			this.x = (this.x+x)%360;
		}
		
		public long getY() {
			return this.y;
		}
		
		public void setY(long y) {
			this.y = y;
		}
		
		public void addY(long y) {
			this.y = (this.y+y)%360;
		}
		
		public void setPosition(long distance) {
			long newX = Math.round(distance*Math.cos(Math.toRadians(getAngle())));
			long newY = Math.round(distance*Math.sin(Math.toRadians(getAngle())));
			addX(newX);
			addY(newY);
			System.out.println("moved to "+ x + " " + y);
		}
		
		public void takeCommand(LogoCommand command) {
			String className = command.getClass().getSimpleName();
			System.out.println(className);
			if (className.equals("LogoMoveCommand")) {
				this.setPosition(command.getDistance());
			}
			else if (className.equals("LogoTurnCommand")) {
				this.setAngle(command.getAngle());
			}
			
		}

		
}
