package net.net16.jeremiahlowe.scicalc;

import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.MathUtility;

public interface Enums {
	public enum DrawTime{
		PrePaint, Paint, PostPaint, Disabled
	}
	public enum CPEReason{
		NoVariables, InvalidSymbol, Syntax, NotAFunction, InvalidName, InvalidVariableName, Unknown;
		public String toString(){
			switch(this){
				case NoVariables: return "No variables";
				case InvalidSymbol: return "Invalid symbol";
				case Syntax: return "Syntax error";
				case InvalidName: return "Invalid function name";
				case NotAFunction: return "Not a function";
				case InvalidVariableName: return "Invalid variable name";
				default: return "Unknown reason";
			}
		}
	}
	public static enum Inequality{
		LessThan, LessThanOrEqualTo, EqualTo, GreatorThanOrEqualTo, GreatorThan;
	}
	public static enum VerticalAllignment{
		Up(1d), HalfUp(0.75d), Center(0.5d), HalfDown(0.25d), Down(0d);
		private double percent = 0;
		private VerticalAllignment(double percent){
			this.percent = percent;
		}
		public void setPercent(double percent){
			if(percent >= 0 && percent <= 1) this.percent = percent;
			else throw new RuntimeException("Percentages must be >= 0 and <= 1!");
		}
		public double getPercent(){return percent;}
		public double interpolate(double min, double max){
			return (min + max) * percent;
		}
		public VerticalAllignment negate(){
			VerticalAllignment out = VerticalAllignment.Center;
			out.setPercent(1d - getPercent());
			return out;
		}
	}
	public static enum HorizontalAllignment{
		Left(0d), HalfLeft(0.25d), ForthLeft(0.375d), Center(0.5d), ForthRight(0.625d), HalfRight(0.75d), Right(1d);
		private double percent = 0;
		private HorizontalAllignment(double percent){
			this.percent = percent;
		}
		public void setPercent(double percent){
			if(percent >= 0 && percent <= 1) this.percent = percent;
			else throw new RuntimeException("Percentages must be >= 0 and <= 1!");
		}
		public double getPercent(){return percent;}
		public double interpolate(double min, double max){
			return (min + max) * percent;
		}
		public HorizontalAllignment negate(){
			HorizontalAllignment out = HorizontalAllignment.Center;
			out.setPercent(1d - getPercent());
			return out;
		}
	}
	public static enum LineStyle{
		Normal, Dotted, Dashed, DashedDotted;
		public boolean isFancy(){
			if(this != Normal) return true;
			else return false;
		}
	}
	public static enum PointStyle{
		Pixel, Circle, OpenCircle, Square, OpenSquare, Cross, ThickCross;
		public void draw(Graphics g, int x, int y, int size){
			switch(this){
				case Circle:
					g.fillOval(x - size / 2, y - size / 2, size, size);
					return;
				case Cross:
					g.drawLine(x - size / 2, y, x + size / 2, y);
					g.drawLine(x, y - size / 2, x, y + size / 2);
					return;
				case ThickCross:
					int lw = MathUtility.clamp(2, 4, size / 4);
					GraphicsUtility.drawLineWithWidth(g, x - size / 2, y, x + size / 2, y, lw);
					GraphicsUtility.drawLineWithWidth(g, x, y - size / 2, x, y + size / 2, lw);
					GraphicsUtility.resetWidth(g);
					return;
				case Pixel:
					g.drawLine(x, y, x, y);
					return;
				case OpenCircle:
					g.drawOval(x - size / 2, y - size / 2, size, size);
					return;
				case OpenSquare:
					g.drawRect(x - size / 2, y - size / 2, size, size);
					return;
				case Square:
					g.fillRect(x - size / 2, y - size / 2, size, size);
					return;
			}
		}
	}
}
