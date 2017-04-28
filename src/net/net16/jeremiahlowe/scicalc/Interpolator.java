package net.net16.jeremiahlowe.scicalc;

import net.net16.jeremiahlowe.scicalc.utility.MathUtility;

//TODO: Finish interpolator
public enum Interpolator{
	Zero(0), Sixteenth(1 / 16d), Eigth(1 / 8d), ThreeSizteenths(3 / 16d), Forth(1 / 4d), 
	FiveSixteenths(5 / 16d), ThreeEigths(3 / 8d), SevenSizteenths(7 / 16d), Half(1 / 2d), 
	NineSixteenths(9 / 16d), FiveEigths(5 / 8d), ElevenSixteenths(11 / 16d), ThreeForths(3 / 4d), 
	ThirteenSixteenths(13 / 16d), SevenEigths(7 / 8d), One(1), Third(1 / 3d), TwoThirds(2 / 3d);
	private double val = 0, min = 0, max = 1;
	private Interpolator(double val, double min, double max){setValue(val, min, max);}
	private Interpolator(double val){this(val, 0, 1);}
	public double evaluate(double min, double max){
		double nmax = max - min;
		double ntmax = this.max - this.min;
		double ntval = val - min;
		return min + (ntval * nmax) * ntmax;
	}
	public void scale(double newMin, double newMax){
		double perc = getPercent();
		setMax(newMax);
		setMin(newMin);
		setPercent(perc);
	}
	public void setMax(double max){
		if(max > min) this.max = max;
		setValue(val);
	}
	public void setMin(double min){
		if(min < max) this.min = min;
		setValue(val);
	}
	public void setValue(double val){
		this.val = MathUtility.clamp(min, max, val);
	}
	public void setValue(double val, double min, double max){
		double oldMin = this.min, oldMax = this.max;
		scale(min, max);
		this.val = val;
		scale(oldMin, oldMax);
	}
	public double getPercent(){return (val - min) / (max - min);}
	public double getValue(){return val;}
	public double getMin(){return min;}
	public double getMax(){return max;}
	public void setPercent(double percent){val = percent * (max - min) + min;}
}