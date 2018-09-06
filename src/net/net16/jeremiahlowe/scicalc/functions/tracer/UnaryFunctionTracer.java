package net.net16.jeremiahlowe.scicalc.functions.tracer;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.MathUtility;
import net.net16.jeremiahlowe.scicalc.utility.DoubleVector;

public class UnaryFunctionTracer extends AbstractFunctionTracer{
	private UnaryFunction function;
	private boolean autoLabelPoint = true;
	private int labelDigits = 2;
	private double currentDelta, currentTheta;
	private DoubleVector pointPos;
	
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
		pointPos = new DoubleVector(onX ? np : pos, onX ? pos : np);
		point.position = pointPos; 
		if(autoLabelPoint) point.fLabel.label = MathUtility.numberToString(pointPos.x, labelDigits) + ", " + MathUtility.numberToString(pointPos.y, labelDigits);
		delegateChangeEvent();
	}
	public void pan(double amount){setPosition(currentDelta + amount);}
	public DoubleVector getPointPos(){return pointPos.clone();}
	public UnaryFunction getFunction(){return function;}
	public double getCurrentDelta(){return currentDelta;}
	public double getCurrentTheta(){return currentTheta;}
	public void setAutoLabelPoint(boolean enabled){
		autoLabelPoint = enabled;
		if(!enabled) point.fLabel.label = null;
		delegateChangeEvent();
	}
	public boolean autoLabelPoint(){return autoLabelPoint;}
	public void setLabelDigits(int digits){if(digits >= 0) labelDigits = digits;}
	public int getLabelDigits(){return labelDigits;}
}
