package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;

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
		return (getThetaMax() - getThetaMin()) / 100d;
	}
	public boolean isFunctionDefined(double t) {
		return true;
	}
	
	public abstract double Xt(double t);
	public abstract double Yt(double t);
}