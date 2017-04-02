package net.net16.jeremiahlowe.scicalc.graphing.functions;

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
	
	public abstract double f(double x);
}