package net.net16.jeremiahlowe.scicalc.graphing.functions;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.graphing.Enums.LineStyle;
import net.net16.jeremiahlowe.scicalc.graphing.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.graphing.Utility;

public class FunctionDrawParameters implements Cloneable{
	//[start] Variables
	public static final int LINE_MODEL_ALTERNATION = 5;
	
	private boolean connectPoints = true;
	private boolean smoothen = true;
	private boolean hasCustomIncrementer = false;
	private double customIncrementer = 0.01d;
	private boolean ignoreBorders = false;
	private LineStyle style = LineStyle.Normal;
	private PointStyle styleP = PointStyle.Pixel;
	private int lineWidth = 1;
	private int lineIteratorPixels = 10;
	//TODO: this
	//private double lineIterator = 0;
	private FunctionLabel fLabel;
	private Color color = Color.RED;
	//[end]
	//[start] Constructors
	public FunctionDrawParameters(){
		fLabel = new FunctionLabel(color);
	}
	//[end]
	//[start] Graphics
	public void drawPoint(Graphics g, int xi, int yi) {
		styleP.draw(g, xi, yi, lineWidth);
	}
	public void drawLine(Graphics g, int x1, int y1, int x2, int y2) {
		if(lineWidth > 1) Utility.drawLineWithWidth(g, x1, y1, x2, y2, lineWidth);
		else g.drawLine(x1, y1, x2, y2);
	}
	//[end]
	//[start] Overriding
	@Override
	public final FunctionDrawParameters clone(){
		FunctionDrawParameters out = new FunctionDrawParameters();
		out.connectPoints = connectPoints;
		out.style = LineStyle.values()[style.ordinal()];
		out.lineWidth = lineWidth;
		out.color = new Color(color.getRGB());
		return out;
	}
	//[end]
	//[start] Getters and setters
	public Color getColor(){
		return color;
	}
	public void setColor(Color color){
		this.color = color;
		fLabel.color = color;
	}
	public boolean hasCustomIncrementer(){
		return hasCustomIncrementer;
	}
	public void setCustomIncrementer(boolean enabled, double val){
		hasCustomIncrementer = enabled;
		customIncrementer = val;
	}
	public final double getCustomIncrementerValid(){
		double inc = getCustomIncrementer();
		return inc > 0 ? inc : 0.01d;
	}
	public double getCustomIncrementer(){
		return customIncrementer;
	}
	public boolean connectPoints() {
		return connectPoints;
	}
	public void setConnectPoints(boolean connectPoints) {
		this.connectPoints = connectPoints;
	}
	public boolean isSmoothen() {
		return smoothen;
	}
	public void setSmoothen(boolean smoothen) {
		this.smoothen = smoothen;
	}
	public void setHasCustomIncrementer(boolean hasCustomIncrementer) {
		this.hasCustomIncrementer = hasCustomIncrementer;
	}
	public boolean ignoreBorders() {
		return ignoreBorders;
	}
	public void setIgnoreBorders(boolean ignoreBorders) {
		this.ignoreBorders = ignoreBorders;
	}
	public LineStyle getStyle() {
		return style;
	}
	public void setStyle(LineStyle style) {
		this.style = style;
	}
	public PointStyle getStyleP() {
		return styleP;
	}
	public void setStyleP(PointStyle styleP) {
		this.styleP = styleP;
	}
	public int getLineWidth() {
		return lineWidth;
	}
	public void setLineWidth(int lineWidth) {
		this.lineWidth = lineWidth;
	}
	public int getLineIteratorPixels() {
		return lineIteratorPixels;
	}
	public void setLineIteratorPixels(int lineIteratorPixels) {
		this.lineIteratorPixels = lineIteratorPixels;
	}
	public FunctionLabel getFunctionLabel() {
		return fLabel;
	}
	public void setfLabel(FunctionLabel fLabel) {
		this.fLabel = fLabel;
	}
	public void setCustomIncrementer(double customIncrementer) {
		this.customIncrementer = customIncrementer;
	}
	//[end]
}