package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.shared.math.Vector;

public abstract class PolarFunction extends BinaryFunction{
	public PolarFunction(){
		super();
	}
	@Override
	public final double getThetaMin(Vector pixelSize, Vector domain, Vector range){
		return 0;
	}
	@Override
	public final double getThetaMax(Vector pixelSize, Vector domain, Vector range){
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