package net.net16.jeremiahlowe.scicalc.functions.std;

public abstract class PolarFunction extends BinaryFunction{
	public PolarFunction(){
		super();
	}
	@Override
	public final double getThetaMin(){
		return 0;
	}
	@Override
	public final double getThetaMax(){
		return 2 * Math.PI;
	}
	@Override
	public final double Xt(double t){
		return Math.cos(t) * r(t);
	}
	@Override
	public final double Yt(double t){
		return Math.sin(t) * r(t);
	}
	
	public abstract double r(double t);
}