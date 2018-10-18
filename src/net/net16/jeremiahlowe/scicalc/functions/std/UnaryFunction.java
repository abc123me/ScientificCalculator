package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.shared.math.Vector;

public abstract class UnaryFunction extends BinaryFunction{
	public boolean onX = false;
	public boolean hasLimits = false;
	
	public double getMin(){return 0;}
	public double getMax(){return 2 * Math.PI;}
	
	public UnaryFunction(boolean onX, boolean hasLimits){
		super();
		this.onX = onX;
		this.hasLimits = hasLimits;
	}
	public UnaryFunction(boolean onX){this(onX, false);}
	public UnaryFunction(){this(false, false);}
	
	public boolean isFunctionDefined(double x) {
		return true;
	}
	
	public abstract double f(double x);
	
	@Override
	public double Yt(double t) {
		if(!onX) return f(t);
		else return t;
	}
	@Override
	public double Xt(double t) {
		if(onX) return f(t);
		else return t;
	}
	@Override
	public double getIncrementer(Vector pixelSize, Vector domain, Vector range) {
		if(onX) return pixelSize.x;
		return pixelSize.y;
	}
	@Override
	public double getThetaMin(Vector pixelSize, Vector domain, Vector range) {
		if(onX) return domain.x;
		return range.x;
	}
	@Override
	public double getThetaMax(Vector pixelSize, Vector domain, Vector range) {
		if(onX) return domain.y;
		return range.y;
	}
}