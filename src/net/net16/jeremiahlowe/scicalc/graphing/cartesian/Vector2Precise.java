package net.net16.jeremiahlowe.scicalc.graphing.cartesian;

public class Vector2Precise implements Cloneable{
	public double x, y;
	public Vector2Precise(double x, double y){
		this.x = x; this.y = y;
	}
	public Vector2Precise(double d){
		this(d, d);
	}
	public Vector2Precise(){
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
	public Vector2Precise negate(){
		this.x *= -1;
		this.y *= -1;
		return this;
	}
	public Vector2Precise inverseSlope(){
		this.x *= -1;
		return this;
	}
	public static double distance(Vector2Precise a, Vector2Precise b){
		return Math.sqrt(distance2(a, b));
	}
	public static double distance2(Vector2Precise a, Vector2Precise b){
		double x = Math.pow((b.x - a.x), 2);
		double y = Math.pow((b.y - a.y), 2);
		return x + y;
	}
	public static Vector2Precise midpoint(Vector2Precise a, Vector2Precise b){
		Vector2Precise out = new Vector2Precise();
		out.x = (a.x + b.x) / 2;
		out.y = (a.y + b.y) / 2;
		return out;
	}
	@Override
	public Vector2Precise clone(){
		return new Vector2Precise(x, y);
	}
	public Vector2Precise add(Vector2Precise b){
		return new Vector2Precise(x + b.x, y + b.y);
	}
	public Vector2Precise subtract(Vector2Precise b){
		return new Vector2Precise(x - b.x, y - b.y);
	}
	public static Vector2Precise add(Vector2Precise a, Vector2Precise b){
		return new Vector2Precise(a.x + b.x, a.y + b.y);
	}
	public static Vector2Precise subtract(Vector2Precise a, Vector2Precise b){
		return new Vector2Precise(a.x - b.x, a.y - b.y);
	}
	public void translate(Vector2Precise by){
		x += by.x; y += by.y;
	}
	public void translate(double x, double y){
		this.x += x; this.y += y; 
	}
	@Override
	public String toString(){
		return "Vector2 (" + x + ", " + y + ")";
	}
}
