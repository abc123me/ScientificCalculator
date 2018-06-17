package net.net16.jeremiahlowe.scicalc.functions;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.Enums.LineStyle;
import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.utility.LineDrawer;
import net.net16.jeremiahlowe.scicalc.utility.MathUtility;

public class FunctionDrawParameters{
	private boolean connectPoints = true;
	private boolean hasCustomIncrementer = false;
	private double customIncrementer = 0.01d;
	private boolean ignoreBorders = false;
	private boolean nanMeansUndefined = true;
	private PointStyle styleP = PointStyle.Pixel;
	private FunctionLabel fLabel;
	private Color color = Color.RED;
	private LineDrawer lineDrawer;
	private int pointSize;
	
	//[start] Constructors
	public FunctionDrawParameters(){
		fLabel = new FunctionLabel(color);
		lineDrawer = new LineDrawer(color);
		init();
	}
	public void init(){}
	//[end]
	//[start] Graphics
	public void drawPoint(Graphics g, int xi, int yi) {styleP.draw(g, xi, yi, pointSize);}
	public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {lineDrawer.draw(g, x1, y1, x2, y2);}
	//[end]
	//[start] Getters and setters
	public Color getColor(){return color;}
	public void setColor(Color color){
		this.color = color;
		fLabel.color = color;
		lineDrawer.setLineColor(color);
	}
	public LineDrawer getLineDrawer(){return lineDrawer;}
	public boolean hasCustomIncrementer(){return hasCustomIncrementer;}
	public void setCustomIncrementer(boolean enabled, double val){
		hasCustomIncrementer = enabled;
		customIncrementer = val;
	}
	public final double getCustomIncrementerValid(){return MathUtility.clamp(0.01d, Double.MAX_VALUE, getCustomIncrementer());}
	public double getCustomIncrementer(){return customIncrementer;}
	public boolean connectPoints() {return connectPoints;}
	public void setConnectPoints(boolean connectPoints) {this.connectPoints = connectPoints;}
	public void setHasCustomIncrementer(boolean hasCustomIncrementer) {this.hasCustomIncrementer = hasCustomIncrementer;}
	public boolean ignoreBorders() {return ignoreBorders;}
	public void setIgnoreBorders(boolean ignoreBorders) {this.ignoreBorders = ignoreBorders;}
	public LineStyle getLineStyle() {return lineDrawer.getStyle();}
	public void setLineStyle(LineStyle style) {lineDrawer.setStyle(style);}
	public PointStyle getPointStyle() {return styleP;}
	public void setPointStyle(PointStyle styleP) {this.styleP = styleP;}
	public int getLineWidth() {return lineDrawer.getLineWidth();}
	public void setLineWidth(int lineWidth) {lineDrawer.setLineWidth(lineWidth);}
	public int getLineIteratorPixels() {return lineDrawer.getLineIteratorPixels();}
	public void setLineIteratorPixels(int lineIteratorPixels) {lineDrawer.setLineIteratorPixels(lineIteratorPixels);}
	public FunctionLabel getFunctionLabel() {return fLabel;}
	public void setFunctionLabel(FunctionLabel fLabel) {this.fLabel = fLabel;}
	public void setCustomIncrementer(double customIncrementer) {this.customIncrementer = customIncrementer;}
	public void setPointSize(int pointSize){this.pointSize = pointSize;}
	public int getPointSize(){return pointSize;}
	public boolean doesNaNMeanUndefined() {return nanMeansUndefined;}
	public void setNanMeansUndefined(boolean nanMeansUndefined) { this.nanMeansUndefined = nanMeansUndefined; }
	//[end]
}