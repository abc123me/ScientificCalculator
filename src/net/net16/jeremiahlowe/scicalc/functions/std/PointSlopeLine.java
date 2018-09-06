package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.Point;
import net.net16.jeremiahlowe.scicalc.utility.DoubleVector;

public class PointSlopeLine extends UnaryFunction{
	public final double slope, intercept, min, max;	
	//[start] Constructors
	public PointSlopeLine(DoubleVector a, DoubleVector b, boolean hasLimits){
		super(false, hasLimits);
		double y = b.y - a.y, x = b.x - a.x;
		if(x == 0){
			drawOnX = true; slope = 0;
			intercept = a.x - slope * a.y;
			min = a.y < b.y ? a.y : b.y;
			max = a.y > b.y ? a.y : b.y;
		}
		else{
			drawOnX = false; slope = y / x;
			intercept = a.y - slope * a.x;
			min = a.x < b.x ? a.x : b.x;
			max = a.x > b.x ? a.x : b.x;
		}
	}
	public PointSlopeLine(Point a, Point b, boolean hasLimits){this(a.position, b.position, hasLimits);}
	public PointSlopeLine(Point a, Point b){this(a.position, b.position);}
	public PointSlopeLine(DoubleVector a, DoubleVector b){this(a, b, false);}
	//[end]
	@Override
	public double f(double x){return (x * slope) + intercept;}
	@Override
	public double getMin(){return min;}
	@Override
	public double getMax(){return max;}
}