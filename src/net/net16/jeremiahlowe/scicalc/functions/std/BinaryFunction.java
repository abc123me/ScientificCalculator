package net.net16.jeremiahlowe.scicalc.functions.std;

import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;
import net.net16.jeremiahlowe.shared.math.Vector;

public abstract class BinaryFunction extends FunctionDrawParameters{
	public BinaryFunction(){
		super();
	}
	public double getThetaMin(Vector pixelSize, Vector domain, Vector range){
		return 0;
	}
	public double getThetaMax(Vector pixelSize, Vector domain, Vector range){
		return 2 * Math.PI;
	}
	public double getIncrementer(Vector pixelSize, Vector domain, Vector range) {
		return (getThetaMax(pixelSize, domain, range) - getThetaMin(pixelSize, domain, range)) / getPointAmount();
	}
	public double getPointAmount() {
		return 1000d;
	}
	public boolean isFunctionDefined(double t) {
		return true;
	}
	
	public abstract double Xt(double t);
	public abstract double Yt(double t);
	public final Vector asVector(double t) {
		return new Vector((float) Xt(t), (float) Yt(t));
	}
}