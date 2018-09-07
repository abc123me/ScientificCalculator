package net.net16.jeremiahlowe.scicalc.functions.tracer;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.shared.math.Vector;

public class UnaryFunctionTracer extends AbstractFunctionTracer{
	private UnaryFunction function;
	private boolean autoLabelPoint = true;
	private int labelDigits = 2;
	private float currentDelta, currentTheta;
	private Vector pointPos;
	
	public UnaryFunctionTracer(UnaryFunction function){this(function, PointStyle.Square);}
	public UnaryFunctionTracer(UnaryFunction function, PointStyle pointStyle){this(function, pointStyle, 8);}
	public UnaryFunctionTracer(UnaryFunction function, PointStyle pointStyle, int pointSize){
		super(function.getColor(), pointStyle, pointSize);
		this.function = function;
		setPosition(0);
	}
	
	public void setPosition(float pos){
		boolean onX = function.drawOnX;
		float np = (float) function.f(pos);
		currentDelta = pos; currentTheta = np;
		pointPos = new Vector(onX ? np : pos, onX ? pos : np);
		point.position = pointPos; 
		if(autoLabelPoint) point.fLabel.label = String.format("%." + labelDigits + "f, " + "%." + labelDigits + "f");
		//TextUtility.numberToString(pointPos.x, labelDigits) + ", " + TextUtility.numberToString(pointPos.y, labelDigits);
		delegateChangeEvent();
	}
	public void pan(float amount){setPosition(currentDelta + amount);}
	public Vector getPointPos(){return pointPos.copy();}
	public UnaryFunction getFunction(){return function;}
	public float getCurrentDelta(){return currentDelta;}
	public float getCurrentTheta(){return currentTheta;}
	public void setAutoLabelPoint(boolean enabled){
		autoLabelPoint = enabled;
		if(!enabled) point.fLabel.label = null;
		delegateChangeEvent();
	}
	public boolean autoLabelPoint(){return autoLabelPoint;}
	public void setLabelDigits(int digits){if(digits >= 0) labelDigits = digits;}
	public int getLabelDigits(){return labelDigits;}
}
