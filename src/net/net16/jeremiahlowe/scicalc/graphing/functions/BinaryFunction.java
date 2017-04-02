package net.net16.jeremiahlowe.scicalc.graphing.functions;

public abstract class BinaryFunction extends FunctionDrawParameters{
	public BinaryFunction(){
		super();
	}
	public double getThetaMin(){
		return 0;
	}
	public double getThetaMax(){
		return 2 * Math.PI;
	}
	public double getIncrementer() {
		double dist = getThetaMax() - getThetaMin();
		return dist / 100;
	}
	
	public abstract double Xt(double t);
	public abstract double Yt(double t);
}