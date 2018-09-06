package net.net16.jeremiahlowe.scicalc.utility;

import net.net16.jeremiahlowe.bettercollections.Rotation;

public class Utility {

	public static final DoubleVector getBoundingBox(DoubleVector... verticies){
		DoubleVector out = new DoubleVector(Float.MIN_VALUE, Float.MIN_VALUE);
		for(int i = 0; i < verticies.length; i++){
			if(verticies[i].x > out.x) out.x = verticies[i].x;
			if(verticies[i].y > out.y) out.y = verticies[i].y;
		}
		return out;
	}

	public static final DoubleVector getCenter(int x, int y, int w, int h){
		return new DoubleVector((x + w) / 2, (y + h) / 2);
	}
	
	public static final DoubleVector getMidpoint(DoubleVector... verticies){
		DoubleVector origin = new DoubleVector();
		for(int i = 0; i < verticies.length; i++) origin.translate(verticies[i]);
		origin.x /= verticies.length; origin.y /= verticies.length;
		return origin;
	}

	public static final DoubleVector[] rotateVerticiesAround(Rotation rot, DoubleVector origin, DoubleVector... verticies){
		if(rot.getAngleDegrees() == 360 || rot.getAngleDegrees() == 0) return verticies;
		else{
			DoubleVector[] out = new DoubleVector[verticies.length];
			float angle = rot.getAngleRadians();
			for(int i = 0; i < out.length; i++){
				double x1 = verticies[i].x - origin.x, y1 = verticies[i].y - origin.y;
				double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
				double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
				out[i] = new DoubleVector();
				out[i].x = (float) x2 + origin.x;
				out[i].y = (float) y2 + origin.y;
			}
			return out;
		}
	}

	public static final boolean between(long max, long min, long val){
		if(val >= max || val <= min) return false;
		else return true;
	}

	public static final boolean between(int max, int min, int val){
		if(val >= max || val <= min) return false;
		else return true;
	}

	public static final boolean between(float max, float min, float val){
		if(val >= max || val <= min) return false;
		else return true;
	}

	public static final boolean between(double max, double min, double val){
		if(val >= max || val <= min) return false;
		else return true;
	}

	public static double clamp(double min, double max, double val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}

	public static long clamp(long min, long max, long val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}

	public static float clamp(float min, float max, float val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}

	public static int clamp(int min, int max, int val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}

	public static String numberToString(double num, int digits){
		if(digits < 0) throw new RuntimeException("Digits must be >= 0!");
		String[] parts = String.valueOf(num).split("[.]");
		if(parts[1].matches("0")) return parts[0];
		if(parts[1].length() > digits){
			String end = parts[1].substring(0, digits);
			while(end.endsWith("0")) end = end.substring(0, end.length() - 1);
			return parts[0] + '.' + end;
		}
		return parts[0] + '.' + parts[1];
	}

	public static final DoubleVector[] rotateVerticiesAroundMidpoint(Rotation rot, DoubleVector... verticies){
		return rotateVerticiesAround(rot, getMidpoint(verticies), verticies);
	}

	public static final boolean betweenOrEqual(double max, double min, double val){
		if(val > max || val < min) return false;
		else return true;
	}

	public static final boolean betweenOrEqual(float max, float min, float val){
		if(val > max || val < min) return false;
		else return true;
	}

	public static final boolean betweenOrEqual(int max, int min, int val){
		if(val > max || val < min) return false;
		else return true;
	}

	public static final boolean betweenOrEqual(long max, long min, long val){
		if(val > max || val < min) return false;
		else return true;
	}

	public static final double slopeXY(DoubleVector a, DoubleVector b, boolean highNumOverInf){
		double x = b.x - a.x, y = a.y - b.y;
		if(y == 0){
			if(highNumOverInf) return 1e10;
			else return Double.POSITIVE_INFINITY;
		}
		return x / y;
	}
	public static final double slopeYX(DoubleVector a, DoubleVector b, boolean highNumOverInf){
		double x = b.x - a.x, y = a.y - b.y;
		if(x == 0){
			if(highNumOverInf) return 1e10;
			else return Double.POSITIVE_INFINITY;
		}
		return y / x;
	}
	public static final double slope(DoubleVector a, DoubleVector b, boolean highNumOverInf){
		return slopeYX(a, b, highNumOverInf);
	}
	public static final double slope(DoubleVector a, DoubleVector b){
		return slopeYX(a, b, false);
	}
	public static final double yIntercept(DoubleVector a, DoubleVector b){
		double m = slopeYX(a, b, false);
		return a.y - (m * b.x);
	}
	public static final DoubleVector[] combineXYArrays(double[] x, double[] y){
		if(x.length != y.length)
			throw new RuntimeException("X and Y Arrays must be same in size");
		int len = x.length;
		DoubleVector[] out = new DoubleVector[len];
		for(int i = 0; i < len; i++)
			out[i] = new DoubleVector(x[i], y[i]);
		return out;
	}
}
