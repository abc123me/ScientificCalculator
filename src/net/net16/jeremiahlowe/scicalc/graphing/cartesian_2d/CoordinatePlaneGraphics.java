package net.net16.jeremiahlowe.scicalc.graphing.cartesian_2d;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.bettercollections.Rotation;
import net.net16.jeremiahlowe.scicalc.graphing.Enums.Quadrant;
import net.net16.jeremiahlowe.scicalc.graphing.Utility;

/**
 * This class handles the what don't really need to be 
 * handled by the CoordinatePlane class same goes for 
 * the FunctionManager, this is just a clone of it but
 * for the axes, tick marks, grid, etc.
 * @author Jeremiah Lowe
 *
 */
public class CoordinatePlaneGraphics {
	// II   I
	// III  IV
	//[start] Positional calculations
	public Vector2Precise getPlaneDomain(Quadrant quadrant, Vector2Precise viewportSize) {
		double h = viewportSize.x / 2, s = viewportSize.x;
		switch(quadrant){                                       
			//Single
			case I: return new Vector2Precise(0, s);
			case II: return new Vector2Precise(-s, 0);
			case III: return new Vector2Precise(-s, 0);
			case IV: return new Vector2Precise(0, s);
			//Multiple
			case I_II: return new Vector2Precise(-h, h);
			case II_III: return new Vector2Precise(-s, 0);
			case III_IV: return new Vector2Precise(-h, h);
			case I_IV: return new Vector2Precise(0, s);
			case ALL: return new Vector2Precise(-h, h);
			default: return null;
		}
	}
	public Vector2Precise getPlaneRange(Quadrant quadrant, Vector2Precise viewportSize) {
		double h = viewportSize.y / 2, s = viewportSize.y;
		switch(quadrant){
			//Single
			case I: return new Vector2Precise(0, s);
			case II: return new Vector2Precise(0, s);
			case III: return new Vector2Precise(-s, 0);
			case IV: return new Vector2Precise(-s, 0);
			//Multiple
			case I_II: return new Vector2Precise(0, s);
			case II_III: return new Vector2Precise(-h, h);
			case III_IV: return new Vector2Precise(-s, 0);
			case I_IV: return new Vector2Precise(-h, h);
			case ALL: return new Vector2Precise(-h, h);
			default: return null;
		}
	}
	public Vector2Precise getPixelOrigin(Quadrant q, Vector2Precise size , int surroundingOffset){
		double sx = size.x - surroundingOffset, sy = size.y - surroundingOffset;
		double hx = size.x / 2, hy = size.y / 2;
		switch(q){
			//Single
			case I: return new Vector2Precise(surroundingOffset, sy);
			case II: return new Vector2Precise(sx, sy);
			case III: return new Vector2Precise(sx, surroundingOffset);
			case IV: return new Vector2Precise(surroundingOffset, surroundingOffset);
			//Multiple
			case I_II: return new Vector2Precise(hx, sy);
			case II_III: return new Vector2Precise(sx, hy);
			case III_IV: return new Vector2Precise(hx, surroundingOffset);
			case I_IV: return new Vector2Precise(surroundingOffset, hy);
			case ALL: return new Vector2Precise(hx, hy);
			default: return null;
		}
	}
	public int getLineX(Quadrant q, Vector2Precise size, int surroundingOffset){
		if(q == Quadrant.ALL || q == Quadrant.I_II || q == Quadrant.III_IV) return size.getXI() / 2;
		else if(q == Quadrant.I || q == Quadrant.IV || q == Quadrant.I_IV) return surroundingOffset;
		else return size.getXI() - surroundingOffset;
	}
	public int getLineY(Quadrant q, Vector2Precise size, int surroundingOffset){
		if(q == Quadrant.ALL || q == Quadrant.II_III || q == Quadrant.I_IV) return size.getYI() / 2;
		else if(q == Quadrant.III || q == Quadrant.IV || q == Quadrant.III_IV) return surroundingOffset;
		else return size.getYI() - surroundingOffset;
	}
	//[end]
	//[start] Drawing
	public void drawTick(Graphics g, Vector2Precise size, int x, int y, int surroundingOffset, int tickWidth, int lineWidth, boolean horizontal){
		double xm = size.x - surroundingOffset, ym = size.y - surroundingOffset;
		if(Utility.betweenOrEqual(xm, surroundingOffset, x) && Utility.betweenOrEqual(ym, surroundingOffset, y)){
			if(horizontal){
				if(lineWidth > 1) Utility.drawLineWithWidth(g, x - tickWidth, y, x + tickWidth, y, lineWidth);
				else g.drawLine(x - tickWidth, y, x + tickWidth, y);
			}
			else{
				if(lineWidth > 1) Utility.drawLineWithWidth(g, x, y - tickWidth, x, y + tickWidth, lineWidth);
				else g.drawLine(x, y - tickWidth, x, y + tickWidth);
			}
		}
	}
	public void drawAxes(Graphics g, Quadrant q, Vector2Precise size, Color axesColor, int surroundingOffset, int lineWidth){
		g.setColor(axesColor);
		//Calculate axis positions
		int lineX = getLineX(q, size, surroundingOffset); //X-Axis position
		int lineY = getLineY(q, size, surroundingOffset); //Y-Axis position
		//Actually draw axes
		if(lineWidth >= 1){
			Utility.drawLineWithWidth(g, lineX, surroundingOffset, lineX, size.getYI() - surroundingOffset, lineWidth);
			Utility.drawLineWithWidth(g, surroundingOffset, lineY, size.getXI() - surroundingOffset, lineY, lineWidth);
			Utility.drawArrowWithWidth(Rotation.NORTH, lineX, surroundingOffset, g, lineWidth);
			Utility.drawArrowWithWidth(Rotation.SOUTH, lineX, size.getYI() - surroundingOffset, g, lineWidth);
			Utility.drawArrowWithWidth(Rotation.EAST, size.getXI() - surroundingOffset, lineY, g, lineWidth);
			Utility.drawArrowWithWidth(Rotation.WEST, surroundingOffset, lineY, g, lineWidth);
			Utility.resetWidth(g);
		}
		else{
			g.drawLine(lineX, surroundingOffset, lineX, size.getYI() - surroundingOffset);
			g.drawLine(surroundingOffset, lineY, size.getXI() - surroundingOffset, lineY);
			Utility.drawArrow(Rotation.NORTH, lineX, surroundingOffset, g);
			Utility.drawArrow(Rotation.SOUTH, lineX, size.getYI() - surroundingOffset, g);
			Utility.drawArrow(Rotation.EAST, size.getXI() - surroundingOffset, lineY, g);
		}
	}
	//[end]
}
