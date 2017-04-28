package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.bettercollections.Rotation;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.MathUtility;
import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

public class CoordinatePlaneGraphics {
	public Vector2Precise getPlaneDomain(Vector2Precise viewportSize, Vector2Precise offset) {
		double h = viewportSize.x / 2, nh = -h;
		h -= offset.x;
		nh -= offset.x;
		return new Vector2Precise(nh, h);
	}
	public Vector2Precise getPlaneRange(Vector2Precise viewportSize, Vector2Precise offset) {
		double h = viewportSize.y / 2, nh = -h;
		h -= offset.y;
		nh -= offset.y;
		return new Vector2Precise(nh, h);
	}
	public Vector2Precise getPixelOrigin(Vector2Precise size , int surroundingOffset){
		double hx = size.x / 2, hy = size.y / 2;
		return new Vector2Precise(hx, hy);
	}
	public int getLineX(CoordinatePlane c, Vector2Precise size, int surroundingOffset){
		return c.castFromOrigin(new Vector2Precise(0, 0), size, surroundingOffset).getXI();
	}
	public int getLineY(CoordinatePlane c, Vector2Precise size, int surroundingOffset){
		return c.castFromOrigin(new Vector2Precise(0, 0), size, surroundingOffset).getYI();
	}
	public void drawTick(Graphics g, Vector2Precise size, int x, int y, int surroundingOffset, int tickWidth, int lineWidth, boolean horizontal){
		double xm = size.x - surroundingOffset, ym = size.y - surroundingOffset;
		if(MathUtility.betweenOrEqual(xm, surroundingOffset, x) && MathUtility.betweenOrEqual(ym, surroundingOffset, y)){
			if(horizontal){
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, x - tickWidth, y, x + tickWidth, y, lineWidth);
				else g.drawLine(x - tickWidth, y, x + tickWidth, y);
			}
			else{
				if(lineWidth > 1) GraphicsUtility.drawLineWithWidth(g, x, y - tickWidth, x, y + tickWidth, lineWidth);
				else g.drawLine(x, y - tickWidth, x, y + tickWidth);
			}
		}
	}
	public void drawAxes(CoordinatePlane c, Graphics g, Vector2Precise size, Color axesColor, int surroundingOffset, int lineWidth){
		g.setColor(axesColor);
		//Calculate axis positions
		int lineX = getLineX(c, size, surroundingOffset); //X-Axis position
		int lineY = getLineY(c, size, surroundingOffset); //Y-Axis position
		//Actually draw axes
		if(lineWidth >= 1){
			GraphicsUtility.drawLineWithWidth(g, lineX, surroundingOffset, lineX, size.getYI() - surroundingOffset, lineWidth);
			GraphicsUtility.drawLineWithWidth(g, surroundingOffset, lineY, size.getXI() - surroundingOffset, lineY, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.NORTH, lineX, surroundingOffset, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.SOUTH, lineX, size.getYI() - surroundingOffset, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.EAST, size.getXI() - surroundingOffset, lineY, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.WEST, surroundingOffset, lineY, g, lineWidth);
			GraphicsUtility.resetWidth(g);
		}
		else{
			g.drawLine(lineX, surroundingOffset, lineX, size.getYI() - surroundingOffset);
			g.drawLine(surroundingOffset, lineY, size.getXI() - surroundingOffset, lineY);
			GraphicsUtility.drawArrow(Rotation.NORTH, lineX, surroundingOffset, g);
			GraphicsUtility.drawArrow(Rotation.SOUTH, lineX, size.getYI() - surroundingOffset, g);
			GraphicsUtility.drawArrow(Rotation.EAST, size.getXI() - surroundingOffset, lineY, g);
		}
	}
}
