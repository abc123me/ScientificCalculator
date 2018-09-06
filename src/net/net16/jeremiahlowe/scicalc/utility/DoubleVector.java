package net.net16.jeremiahlowe.scicalc.utility;

public class DoubleVector implements Cloneable{
	public double x, y;
	public DoubleVector(double x, double y){
		this.x = x; this.y = y;
	}
	public DoubleVector(double d){
		this(d, d);
	}
	public DoubleVector(){
		this(0);
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public int getXI(){
		return (int) Math.round(x);
	}
	public int getYI(){
		return (int) Math.round(y);
	}
	public void setX(float x){
		this.x = x;
	}
	public void setY(float y){
		this.y = y;
	}
	public DoubleVector negate(){
		this.x *= -1;
		this.y *= -1;
		return this;
	}
	public DoubleVector inverseSlope(){
		this.x *= -1;
		return this;
	}
	public static double distance(DoubleVector a, DoubleVector b){
		return Math.sqrt(distance2(a, b));
	}
	public static double distance2(DoubleVector a, DoubleVector b){
		double x = Math.pow((b.x - a.x), 2);
		double y = Math.pow((b.y - a.y), 2);
		return x + y;
	}
	public static DoubleVector midpoint(DoubleVector a, DoubleVector b){
		DoubleVector out = new DoubleVector();
		out.x = (a.x + b.x) / 2;
		out.y = (a.y + b.y) / 2;
		return out;
	}
	@Override
	public DoubleVector clone(){
		return new DoubleVector(x, y);
	}
	public DoubleVector add(DoubleVector b){
		return new DoubleVector(x + b.x, y + b.y);
	}
	public DoubleVector subtract(DoubleVector b){
		return new DoubleVector(x - b.x, y - b.y);
	}
	public static DoubleVector add(DoubleVector a, DoubleVector b){
		return new DoubleVector(a.x + b.x, a.y + b.y);
	}
	public static DoubleVector subtract(DoubleVector a, DoubleVector b){
		return new DoubleVector(a.x - b.x, a.y - b.y);
	}
	public void translate(DoubleVector by){
		x += by.x; y += by.y;
	}
	public void translate(double x, double y){
		this.x += x; this.y += y; 
	}
	@Override
	public String toString(){
		return "Vector2 (" + x + ", " + y + ")";
	}
	public static DoubleVector div(DoubleVector a, DoubleVector b) {
		DoubleVector out = new DoubleVector();
		out.x = a.x / b.x;
		out.y = a.y / b.y;
		return out;
	}
}
