package net.net16.jeremiahlowe.scicalc;

import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.utility.Utility;

public interface Enums {
	public static enum Inequality{
		LessThan, LessThanOrEqualTo, EqualTo, GreatorThanOrEqualTo, GreatorThan;
	}
	//TODO: Finish interpolator
	public static class Interpolator{
		private double val = 0, min = 0, max = 1;
		public double evaluate(double min, double max){
			double nmax = max - min;
			double ntmax = this.max - this.min;
			double ntval = val - min;
			return min + (ntval * nmax) * ntmax;
		}
		public void scale(double newMin, double newMax){
			double perc = getPercent();
			setMax(newMax);
			setMin(newMin);
			setPercent(perc);
		}
		public void setMax(double max){
			if(max > min) this.max = max;
			setValue(val);
		}
		public void setMin(double min){
			if(min < max) this.min = min;
			setValue(val);
		}
		public void setValue(double val){
			this.val = Utility.clamp(min, max, val);
		}
		public void setValue(double val, double min, double max){
			double oldMin = this.min, oldMax = this.max;
			scale(min, max);
			this.val = val;
			scale(oldMin, oldMax);
		}
		public double getPercent(){return (val - min) / (max - min);}
		public double getValue(){return val;}
		public double getMin(){return min;}
		public double getMax(){return max;}
		public void setPercent(double percent){val = percent * (max - min) + min;}
	}
	public static enum VerticalAllignment{
		Up, HalfUp, Center, HalfDown, Down, Custom;
		private double percent = 0;
		public VerticalAllignment negate(){
			if(this == Up) return Down;
			else if(this == Down) return Up;
			else if(this == HalfUp) return HalfDown;
			else if(this == HalfDown) return HalfUp;
			else if(this == Custom){
				VerticalAllignment special = VerticalAllignment.Custom;
				special.setCustomPercent(1 - percent);
				return special;
			}
			else return Center;
		}
		public void setCustomPercent(double percent){
			if(percent >= 0 && percent <= 1){
				this.percent = percent;
				return;
			}
			throw new RuntimeException("Percentages must be >= 0 and <= 1!");
		}
		public double getCustomMultiplier(){return percent;}
		public double interpolate(double min, double max){
			double mid = (max + min) / 2;
			switch(this){
				case Center: return mid;
				case Down: return min;
				case HalfDown: return (mid + min) / 2;
				case HalfUp: return (mid + max) / 2;
				case Up: return max;
				case Custom: return (min + max) * percent;
				default: return mid;
			}
		}
	}
	public static enum HorizontalAllignment{
		Left, HalfLeft, Center, HalfRight, Right, Custom;
		private double percent = 0;
		public HorizontalAllignment negate(){
			if(this == Left) return Right;
			else if(this == Right) return Left;
			else if(this == HalfLeft) return HalfRight;
			else if(this == HalfRight) return HalfLeft;
			else if(this == Custom){
				HorizontalAllignment special = HorizontalAllignment.Custom;
				special.setCustomPercent(1 - percent);
				return special;
			}
			else return Center;
		}
		public void setCustomPercent(double percent){
			if(percent >= 0 && percent <= 1){
				this.percent = percent;
				return;
			}
			throw new RuntimeException("Percentages must be >= 0 and <= 1!");
		}
		public double getCustomMultiplier(){return percent;}
		public double interpolate(double min, double max){
			double mid = (max + min) / 2;
			switch(this){
				case Center: return mid;
				case Left: return min;
				case HalfLeft: return (mid + min) / 2;
				case HalfRight: return (mid + max) / 2;
				case Right: return max;
				case Custom: return (min + max) * percent;
				default: return mid;
			}
		}
	}
	public static enum LineStyle{
		Normal, Dotted, Dashed, DashedDotted, Wavy;
		public boolean isFancy(){
			if(this != Normal) return true;
			else return false;
		}
		public boolean smoothenWithTrig(){
			if(this == Wavy) return true;
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
					int lw = Utility.clamp(2, 4, size / 4);
					Utility.drawLineWithWidth(g, x - size / 2, y, x + size / 2, y, lw);
					Utility.drawLineWithWidth(g, x, y - size / 2, x, y + size / 2, lw);
					Utility.resetWidth(g);
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
