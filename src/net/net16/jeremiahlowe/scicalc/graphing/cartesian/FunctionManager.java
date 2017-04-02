package net.net16.jeremiahlowe.scicalc.graphing.cartesian;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.Utility;
import net.net16.jeremiahlowe.scicalc.Enums.Quadrant;
import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;

//TODO: Implement multi-threading in FunctionManager
//TODO: Fix panning and overshooting bugs
public class FunctionManager {
	public List<UnaryFunction> unaryFunctions = new ArrayList<UnaryFunction>();
	public List<BinaryFunction> binaryFunctions = new ArrayList<BinaryFunction>();
	public List<UnaryFunction> tempUnaryFunctions = new ArrayList<UnaryFunction>();
	public List<BinaryFunction> tempBinaryFunctions = new ArrayList<BinaryFunction>();
	
	public void drawFunctions(CoordinatePlane c, Graphics g, Vector2Precise size){
		for(UnaryFunction d : unaryFunctions){ //Boring functions
			drawUnaryFunction(c, g, d, c.getViewQuadrant(), size, c.getUnitsPerPixel(), c.getViewportSize(), c.getSurroundingOffset());
			if(d.getLineWidth() > 1) Utility.resetWidth(g);
		}
		for(BinaryFunction d : binaryFunctions){ //Cool functions
			drawBinaryFunction(c, g, d, c.getViewQuadrant(), size, c.getUnitsPerPixel(), c.getViewportSize(), c.getSurroundingOffset());
			if(d.getLineWidth() > 1) Utility.resetWidth(g);
		}
	}
	public void drawBinaryFunction(CoordinatePlane c, Graphics g, BinaryFunction f, Quadrant quadrant, Vector2Precise size, Vector2Precise pixelSize, Vector2Precise viewportSize, int surroundingOffset){
		g.setColor(f.getColor());
		//Build the point list
		List<Vector2Precise> pointsL = new ArrayList<Vector2Precise>();
		//Get the range of the equation
		Vector2Precise range = new Vector2Precise(f.getThetaMin(), f.getThetaMax());
		double incrementer = f.getIncrementer();
		//Build and cast the point list
		for(double i = range.x; i < range.y; i += incrementer){
			pointsL.add(c.castFromOrigin(new Vector2Precise(f.Yt(i), f.Xt(i)), size, surroundingOffset));
		}
		//Finish up
		finalDraw(g, pointsL, f, c.getWidth(), c.getHeight(), surroundingOffset);
	}
	public void drawUnaryFunction(CoordinatePlane c, Graphics g, UnaryFunction f, Quadrant quadrant, Vector2Precise size, Vector2Precise pixelSize, Vector2Precise viewportSize, int surroundingOffset){
		g.setColor(f.getColor());
		//Get equation domain
		boolean onX = f.drawOnX;
		Vector2Precise d = new Vector2Precise(0, 0), md = new Vector2Precise(0, 0);
		if(onX) md = c.getPlaneRange();
		else md = c.getPlaneDomain();
		if(f.hasLimits){
			d = new Vector2Precise(f.getMin(), f.getMax()); 
			if(d.x < md.x) d.x = md.x; 
			if(d.y > md.y) d.y = md.y;
		}
		else d = md;
		double inc = onX ? pixelSize.y : pixelSize.x;
		if(f.hasCustomIncrementer()) inc = f.getCustomIncrementerValid();
		//Build and cast the point list
		List<Vector2Precise> pointsL = makePointList(c, f, onX, d, size, surroundingOffset, inc);
		finalDraw(g, pointsL, f, c.getWidth(), c.getHeight(), surroundingOffset);
	}
	public List<Vector2Precise> makePointList(CoordinatePlane c, UnaryFunction f, boolean onX, Vector2Precise d, Vector2Precise size, int surroundingOffset, double inc){
		List<Vector2Precise> pointsL = new ArrayList<Vector2Precise>();
		for(double i = d.x; i < d.y; i += inc){
			double x = onX ? f.f(i) : i, y = !onX ? f.f(i) : i; //Get x and y values
			pointsL.add(c.castFromOrigin(new Vector2Precise(x, y), size, surroundingOffset));
		}
		return pointsL;
	}
	public void finalDraw(Graphics g, List<Vector2Precise> pointsL, FunctionDrawParameters f, int width, int height, int surroundingOffset){
		//Convert the points list to an array
		Vector2Precise[] points = new Vector2Precise[0];
		points = pointsL.toArray(points);
		//Grab the max and min positions, Needed later
		if(points.length <= 0) return; //Bug fix #900000000000
		Vector2Precise maxPos = points[0].clone();
		Vector2Precise minPos = points[0].clone();
		//Draw all points
		for(int i = 1; i < points.length; i++){
			Vector2Precise a = points[i], b = points[i - 1];
			boolean inX = (int) a.x >= surroundingOffset && (int) a.x <= width - surroundingOffset;
			boolean inY = (int) a.y >= surroundingOffset && (int) a.y <= height - surroundingOffset;
			if(f.ignoreBorders() || (inX && inY)){
				if(f.connectPoints()) f.drawLine(g, (int) a.x, (int) a.y, (int) b.x, (int) b.y);
				else f.drawPoint(g, (int) a.x, (int) a.y);
			}
			//Make sure we REALLY have the max and min values
			if(a.x > maxPos.x) maxPos.x = a.x; 
			if(a.y > maxPos.y) maxPos.y = a.y;
			if(a.x < minPos.x) minPos.x = a.x; 
			if(a.y < minPos.y) minPos.y = a.y;
		}
		//Draw the function's label
		f.getFunctionLabel().draw(g, maxPos, minPos);
	}
	public void add(UnaryFunction f){
		unaryFunctions.add(f);
	}
	public void add(BinaryFunction f){
		binaryFunctions.add(f);
	}
	public void remove(UnaryFunction f){
		unaryFunctions.remove(f);
	}
	public void remove(BinaryFunction f){
		binaryFunctions.remove(f);
	}
}
