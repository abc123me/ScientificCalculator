package net.net16.jeremiahlowe.scicalc.functions.tracer;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.Vector2Precise;

//TODO: Create a function tracer
public class UnaryFunctionTracer extends AbstractFunctionTracer{
	private UnaryFunction function;	
	private double currentDelta, currentTheta;
	private Vector2Precise pointPos;
	
	public UnaryFunctionTracer(UnaryFunction function){this(function, PointStyle.Square);}
	public UnaryFunctionTracer(UnaryFunction function, PointStyle pointStyle){this(function, pointStyle, 8);}
	public UnaryFunctionTracer(UnaryFunction function, PointStyle pointStyle, int pointSize){
		super(function.getColor(), pointStyle, pointSize);
		this.function = function;
		setPosition(0);
	}
	
	public void setPosition(double pos){
		boolean onX = function.drawOnX;
		double np = function.f(pos);
		currentDelta = pos; currentTheta = np;
		pointPos = new Vector2Precise(onX ? np : pos, onX ? pos : np);
		point.position = pointPos; 
		delegateChangeEvent();
	}
	public void pan(double amount){setPosition(currentDelta + amount);}
	public Vector2Precise getPointPos(){return pointPos.clone();}
	public UnaryFunction getFunction(){return function;}
	public double getCurrentDelta(){return currentDelta;}
	public double getCurrentTheta(){return currentTheta;}
}
