package net.net16.jeremiahlowe.scicalc.functions;

import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.PointSlopeLine;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

//TODO: Add more factories
public class FunctionFactory {
	public static UnaryFunction sin(double amplitude, double frequency){
		return new UnaryFunction() {
			@Override
			public double f(double x) {
				return amplitude * Math.sin(frequency * x);
			}
		};
	}
	public static UnaryFunction cos(double amplitude, double frequency){
		return new UnaryFunction() {
			@Override
			public double f(double x) {
				return amplitude * Math.cos(frequency * x);
			}
		};
	}
	//ax + b
	public static UnaryFunction line(double slope, double yIntercept){
		return new UnaryFunction() {
			@Override
			public double f(double x) {
				return slope * x + yIntercept;
			}
		};
	}
	//ax^2 + bx + c
	public static UnaryFunction quadratic(double a, double b, double c){
		return new UnaryFunction() {
			@Override
			public double f(double x) {
				return a * Math.pow(x, 2) + b * x + c;
			}
		};
	}
	//ax^2 + bx + c
	public static UnaryFunction quadratic(Vector2Precise vertex, double a){
		return new UnaryFunction() {
			@Override
			public double f(double x) {
				return a * Math.pow(x - vertex.x, 2) + vertex.y;
			}
		};
	}
	public static BinaryFunction circle(Vector2Precise vertex, double r){
		return new BinaryFunction() {
			@Override
			public double Yt(double t) {
				return r * Math.sin(t + vertex.y);
			}
			@Override
			public double Xt(double t) {
				return r * Math.cos(t + vertex.x);
			}
			@Override
			public double getThetaMax(){
				return 2 * Math.PI;
			}
			@Override
			public double getThetaMin(){
				return 0;
			}
		};
	}
	public static BinaryFunction ellipse(Vector2Precise vertex, double w, double h){
		return new BinaryFunction() {
			@Override
			public double Yt(double t) {
				return h * Math.sin(t + vertex.y);
			}
			@Override
			public double Xt(double t) {
				return w * Math.cos(t + vertex.x);
			}
			@Override
			public double getThetaMax(){
				return 2 * Math.PI;
			}
			@Override
			public double getThetaMin(){
				return 0;
			}
		};
	}
	public static PointSlopeLine line(Vector2Precise start, Vector2Precise end){
		return line(start, end, false);
	}
	public static PointSlopeLine line(Vector2Precise start, Vector2Precise end, boolean limited){
		return new PointSlopeLine(start, end, limited);
	}
}
