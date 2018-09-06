package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.DoubleVector;

//TODO: Implement multi-threading in FunctionManager
//TODO: Fix panning and overshooting bugs
public class FunctionManager {
	public List<UnaryFunction> unaryFunctions = new ArrayList<UnaryFunction>();
	public List<BinaryFunction> binaryFunctions = new ArrayList<BinaryFunction>();
	
	public void drawFunctions(CoordinatePlane c, Graphics g, DoubleVector size){
		for(UnaryFunction d : unaryFunctions){ //Boring functions
			drawUnaryFunction(c, g, d, size, c.getUnitsPerPixel(), c.getViewportSize(), c.getSurroundingOffset());
			if(d.getLineWidth() > 1) GraphicsUtility.resetWidth(g);
		}
		for(BinaryFunction d : binaryFunctions){ //Cool functions
			drawBinaryFunction(c, g, d, size, c.getUnitsPerPixel(), c.getViewportSize(), c.getSurroundingOffset());
			if(d.getLineWidth() > 1) GraphicsUtility.resetWidth(g);
		}
	}
	public void drawBinaryFunction(CoordinatePlane c, Graphics g, BinaryFunction f, DoubleVector size, DoubleVector pixelSize, DoubleVector viewportSize, int surroundingOffset){
		g.setColor(f.getColor());
		//Build the point list
		List<DoubleVector> pointsL = new ArrayList<DoubleVector>();
		//Get the range of the equation
		DoubleVector range = new DoubleVector(f.getThetaMin(), f.getThetaMax());
		double incrementer = f.getIncrementer();
		//Build and cast the point list
		for(double i = range.x; i < range.y; i += incrementer){
			if(!f.isFunctionDefined(i))
				continue;
			pointsL.add(c.castFromOrigin(new DoubleVector(f.Xt(i), f.Yt(i)), size, surroundingOffset));
		}
		//Finish up
		finalDraw(g, pointsL, f, c.getWidth(), c.getHeight(), surroundingOffset);
	}
	public void drawUnaryFunction(CoordinatePlane c, Graphics g, UnaryFunction f, DoubleVector size, DoubleVector pixelSize, DoubleVector viewportSize, int surroundingOffset){
		g.setColor(f.getColor());
		//Get equation domain
		boolean onX = f.drawOnX;
		DoubleVector d = new DoubleVector(0, 0), md = new DoubleVector(0, 0);
		if(onX) md = c.getPlaneRange();
		else md = c.getPlaneDomain();
		if(f.hasLimits){
			d = new DoubleVector(f.getMin(), f.getMax()); 
			if(d.x < md.x) d.x = md.x; 
			if(d.y > md.y) d.y = md.y;
		}
		else d = md;
		//System.out.println(d);
		double inc = onX ? pixelSize.y : pixelSize.x;
		if(f.hasCustomIncrementer()) inc = f.getCustomIncrementerValid();
		//Build and cast the point list
		List<DoubleVector> pointsL = makePointList(c, f, onX, d, size, surroundingOffset, inc);
		finalDraw(g, pointsL, f, c.getWidth(), c.getHeight(), surroundingOffset);
	}
	public List<DoubleVector> makePointList(CoordinatePlane c, UnaryFunction f, boolean onX, DoubleVector d, DoubleVector size, int surroundingOffset, double inc){
		List<DoubleVector> pointsL = new ArrayList<DoubleVector>();
		for(double i = d.x; i < d.y; i += inc){
			double ffi = f.f(i);
			double x = onX ? ffi : i, y = !onX ? ffi  : i; 
			if(!f.isFunctionDefined(x))
				continue;
			if(Double.isNaN(ffi))
				continue;
			pointsL.add(c.castFromOrigin(new DoubleVector(x, y), size, surroundingOffset));
		}
		return pointsL;
	}
	public void finalDraw(Graphics g, List<DoubleVector> pointsL, FunctionDrawParameters f, int width, int height, int surroundingOffset){
		f.getLineDrawer().resetIterator();
		//Convert the points list to an array
		DoubleVector[] points = new DoubleVector[0];
		points = pointsL.toArray(points);
		//Grab the max and min positions, Needed later
		if(points.length <= 0) return; //Bug fix #900000000000
		DoubleVector maxPos = points[0].clone();
		DoubleVector minPos = points[0].clone();
		//Draw all points
		for(int i = 1; i < points.length; i++){
			DoubleVector a = points[i], b = points[i - 1];
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
