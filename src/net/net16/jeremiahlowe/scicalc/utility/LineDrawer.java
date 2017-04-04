package net.net16.jeremiahlowe.scicalc.utility;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.Enums.LineStyle;

public class LineDrawer {
	public static int defaultIteratorPixels = 30;
	public static LineStyle defaultLineStyle = LineStyle.Normal;
	
	private LineStyle style = defaultLineStyle;
	private Color lineColor = Color.BLACK;
	private int lineWidth = 1;
	private int lineIteratorPixels = 10;
	private double lineIterator = 0;
	private int dotWidth = 0, dotHeight = 0;
	private int[] waveBank;
	private int waveAmplitude = 5;
	private int waveFrequency = 20;
	private boolean dotCycle = false;
	
	public LineDrawer(){this(defaultLineStyle, defaultIteratorPixels);}
	public LineDrawer(int lip){this(defaultLineStyle, lip);}
	public LineDrawer(Color c){this(defaultLineStyle, c, defaultIteratorPixels);}
	public LineDrawer(LineStyle ls){this(ls, defaultIteratorPixels);}
	public LineDrawer(LineStyle s, int lip){this(s, Color.black, lip);}
	public LineDrawer(LineStyle style, Color lineColor, int lineIteratorPixels){
		//initializeTrigonometryBank(lineIteratorPixels, waveAmplitude, waveFrequency);
		this.lineIteratorPixels = lineIteratorPixels;
		this.lineColor = lineColor;
		dotWidth = lineIteratorPixels / 4;
		dotHeight = dotWidth;
		this.style = style;
	}
	
	public void resetIterator(){
		lineIterator = 0;
	}
	public void draw(Graphics g, int x1, int y1, int x2, int y2){
		if(style == LineStyle.Normal){
			drawLineGeneral(g, x1, y1, x2, y2, lineWidth);
			return;
		}
		//Initialization
		g.setColor(lineColor);
		//if(waveBank.length != lineIteratorPixels) initializeTrigonometryBank(lineIteratorPixels, waveAmplitude, waveFrequency);
		//Get stuff we might need
		int a = x2 - x1, b = y2 - y1; 
		//Calculate slope
		double m = Double.POSITIVE_INFINITY;
		if(a != 0) m = (double) b / (double) a;
		//Get y-intercept
		int yi = (int) (y1 - m * x1);
		// max (m1) and min (m0) values
		int m0 = x1 < x2 ? x1 : x2, m1 = x1 < x2 ? x2 : x1;
		int lastX = m0, lastY = (int) (m0 * m + yi);
		for(int i = m0 + 1; i <= m1; i++){
			//Get x and y (via y=mx+b)
			int x = i, y = (int) m * i + yi;
			if(style == LineStyle.Dashed) drawDashed(g, lastX, lastY, x, y);
			else if(style == LineStyle.Dotted) drawDot(g, x, y);
			else if(style == LineStyle.DashedDotted){
				drawDashed(g, lastX, lastY, x, y);
				if(!dotCycle && drawDot(g, x, y)) dotCycle = true;
			}
			else drawLineGeneral(g, lastX, lastY, x, y, lineWidth);
			double pm = m < 0 ? -m : m;
			lineIterator += pm; //This code is FUNKY!!!
			if(lineIterator >= lineIteratorPixels){
				dotCycle = false;
				lineIterator = 0;
			}
			lastX = x;
			lastY = y;
		} 
	}
	private static void drawLineGeneral(Graphics g, int x1, int y1, int x2, int y2, int lw){
		if(lw > 1) Utility.drawLineWithWidth(g, x1, y1, x2, y2, lw);
		else g.drawLine(x1, y1, x2, y2);
	}
	private void drawDashed(Graphics g, int x1, int y1, int x2, int y2){
		if(lineIterator < lineIteratorPixels / 2d) drawLineGeneral(g, x1, y1, x2, y2, lineWidth);
	}
	private boolean drawDot(Graphics g, int x, int y){
		if(lineIterator > lineIteratorPixels / 2d + lineIteratorPixels / 4d){
			g.fillOval(x - dotWidth / 2, y - dotHeight / 2, dotWidth, dotHeight);
			return true;
		}
		return false;
	}
	/*//We use this to hold all the points in a sine wave so we don't have to re-compute them later
	private void initializeTrigonometryBank(int to, int amp, int freq){
		waveBank = new int[to];
		for(int i = 0; i < to; i++) waveBank[i] = (int) (amp * Math.sin(gradientToRadiant(0, to, i)));
	}*/
	private double gradientToRadiant(double min, double max, double val){
		return ((val - min) / (max - min)) * 2 * Math.PI;
	}
	
	public LineStyle getStyle() {return style;}
	public void setStyle(LineStyle style) {this.style = style;}
	public Color getLineColor() {return lineColor;}
	public void setLineColor(Color lineColor) {this.lineColor = lineColor;}
	public int getLineWidth() {return lineWidth;}
	public void setLineWidth(int lineWidth) {this.lineWidth = lineWidth;}
	public int getLineIteratorPixels() {return lineIteratorPixels;}
	public void setLineIteratorPixels(int lineIteratorPixels) {this.lineIteratorPixels = lineIteratorPixels;}
}
