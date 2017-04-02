package net.net16.jeremiahlowe.scicalc.graphing.functions;

import net.net16.jeremiahlowe.scicalc.graphing.cartesian_2d.Vector2Precise;

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
}
