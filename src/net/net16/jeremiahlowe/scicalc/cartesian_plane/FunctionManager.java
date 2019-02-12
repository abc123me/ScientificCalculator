package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.functions.FunctionDrawParameters;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.shared.math.Vector;

public class FunctionManager {
	private List<BinaryFunction> binaryFunctions = new ArrayList<BinaryFunction>();
	
	public void clear() {
		binaryFunctions.clear();
	}
	public void add(BinaryFunction f){
		binaryFunctions.add(f);
	}
	public void remove(BinaryFunction f){
		binaryFunctions.remove(f);
	}
	public void drawFunctions(CoordinatePlane c, Graphics g, Vector size){
		for(BinaryFunction d : binaryFunctions){ //Cool functions
			drawBinaryFunction(c, g, d, size, c.getPixelSize(), c.getViewportSize(), c.getSurroundingOffset());
			if(d.getLineWidth() > 1) GraphicsUtility.resetWidth(g);
		}
	}
	public void drawBinaryFunction(CoordinatePlane c, Graphics g, BinaryFunction f, Vector size, Vector pixelSize, Vector viewportSize, int surroundingOffset){
		g.setColor(f.getColor());
		//Get the range of the equation
		Vector cpd = c.getPlaneDomain().copy(), cpr = c.getPlaneRange().copy(), cpp = pixelSize.copy();
		Vector range = new Vector((float) f.getThetaMin(cpp, cpd, cpr), (float) f.getThetaMax(cpp, cpd, cpr));
		float incrementer = (float) f.getIncrementer(cpp, cpd, cpr);
		//Build and cast the point list
		List<Vector> pointsL = makePointList(c, f, range, incrementer, size, surroundingOffset);
		//Finish up
		finalDraw(g, pointsL, f, c.getWidth(), c.getHeight(), surroundingOffset);
	}
	public List<Vector> makePointList(CoordinatePlane c, BinaryFunction f, Vector range, float incrementer, Vector size, int surroundingOffset){
		List<Vector> pointsL = new ArrayList<Vector>();
		for(float i = range.x; i < range.y; i += incrementer){
			if(!f.isFunctionDefined(i))
				continue;
			pointsL.add(c.castFromOrigin(f.asVector(i), size, surroundingOffset));
		}
		return pointsL;
	}
	public void finalDraw(Graphics g, List<Vector> pointsL, FunctionDrawParameters f, int width, int height, int surroundingOffset){
		f.getLineDrawer().resetIterator();
		//Convert the points list to an array
		Vector[] points = new Vector[0];
		points = pointsL.toArray(points);
		//Grab the max and min positions, Needed later
		if(points.length <= 0) return; //Bug fix #900000000000
		Vector maxPos = points[0].copy();
		Vector minPos = points[0].copy();
		//Draw all points
		for(int i = 1; i < points.length; i++){
			Vector a = points[i], b = points[i - 1];
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
}
