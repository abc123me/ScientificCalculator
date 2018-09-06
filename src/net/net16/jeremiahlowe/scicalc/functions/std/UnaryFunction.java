package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;

public abstract class UnaryFunction extends FunctionDrawParameters{
	public boolean drawOnX = false;
	public boolean hasLimits = false;
	
	public double getMin(){return 0;}
	public double getMax(){return 2 * Math.PI;}
	
	public UnaryFunction(boolean drawOnX, boolean hasLimits){
		super();
		this.drawOnX = drawOnX;
		this.hasLimits = hasLimits;
	}
	public UnaryFunction(boolean drawOnX){this(drawOnX, false);}
	public UnaryFunction(){this(false, false);}
	
	public boolean isFunctionDefined(double x) {
		return true;
	}
	
	public abstract double f(double x);
	
	public final BinaryFunction toBinaryFunction(){return toBinaryFunction(this);}
	public static final BinaryFunction toBinaryFunction(UnaryFunction f){
		BinaryFunction out = new BinaryFunction() {
			@Override
			public double Yt(double t) {
				if(!f.drawOnX) return f.f(t);
				else return t;
			}
			@Override
			public double Xt(double t) {
				if(f.drawOnX) return f.f(t);
				else return t;
			}
		};
		out.setColor(f.getColor());
		out.setFunctionLabel(f.getFunctionLabel());
		out.setConnectPoints(f.connectPoints());
		out.setIgnoreBorders(f.ignoreBorders());
		out.setLineIteratorPixels(f.getLineIteratorPixels());
		out.setLineWidth(f.getLineWidth());
		out.setLineStyle(f.getLineStyle());
		out.setPointStyle(f.getPointStyle());
		return out;
	}
}