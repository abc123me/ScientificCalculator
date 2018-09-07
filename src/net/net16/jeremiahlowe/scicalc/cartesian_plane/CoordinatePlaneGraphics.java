package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.shared.math.Rotation;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.Utility;
import net.net16.jeremiahlowe.shared.math.Vector;

public class CoordinatePlaneGraphics {
	public Vector getPlaneDomain(Vector viewportSize, Vector offset) {
		float h = viewportSize.x / 2, nh = -h;
		h -= offset.x;
		nh -= offset.x;
		return new Vector(nh, h);
	}
	public Vector getPlaneRange(Vector viewportSize, Vector offset) {
		float h = viewportSize.y / 2, nh = -h;
		h -= offset.y;
		nh -= offset.y;
		return new Vector(nh, h);
	}
	public Vector getPixelOrigin(Vector size , int surroundingOffset){
		float hx = size.x / 2, hy = size.y / 2;
		return new Vector(hx, hy);
	}
	public int getLineX(CoordinatePlane c, Vector size, int surroundingOffset){
		return c.castFromOrigin(new Vector(0, 0), size, surroundingOffset).getXInt();
	}
	public int getLineY(CoordinatePlane c, Vector size, int surroundingOffset){
		return c.castFromOrigin(new Vector(0, 0), size, surroundingOffset).getYInt();
	}
	public void drawTick(Graphics g, Vector size, int x, int y, int surroundingOffset, int tickWidth, int lineWidth, boolean horizontal){
		float xm = size.x - surroundingOffset, ym = size.y - surroundingOffset;
		if(Utility.betweenOrEqual(xm, surroundingOffset, x) && Utility.betweenOrEqual(ym, surroundingOffset, y)){
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
	public void drawAxes(CoordinatePlane c, Graphics g, Vector size, Color axesColor, int surroundingOffset, int lineWidth){
		g.setColor(axesColor);
		//Calculate axis positions
		int lineX = getLineX(c, size, surroundingOffset); //X-Axis position
		int lineY = getLineY(c, size, surroundingOffset); //Y-Axis position
		//Actually draw axes
		if(lineWidth >= 1){
			GraphicsUtility.drawLineWithWidth(g, lineX, surroundingOffset, lineX, size.getYInt() - surroundingOffset, lineWidth);
			GraphicsUtility.drawLineWithWidth(g, surroundingOffset, lineY, size.getXInt() - surroundingOffset, lineY, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.NORTH, lineX, surroundingOffset, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.SOUTH, lineX, size.getYInt() - surroundingOffset, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.EAST, size.getXInt() - surroundingOffset, lineY, g, lineWidth);
			GraphicsUtility.drawArrowWithWidth(Rotation.WEST, surroundingOffset, lineY, g, lineWidth);
			GraphicsUtility.resetWidth(g);
		}
		else{
			g.drawLine(lineX, surroundingOffset, lineX, size.getYInt() - surroundingOffset);
			g.drawLine(surroundingOffset, lineY, size.getXInt() - surroundingOffset, lineY);
			GraphicsUtility.drawArrow(Rotation.NORTH, lineX, surroundingOffset, g);
			GraphicsUtility.drawArrow(Rotation.SOUTH, lineX, size.getYInt() - surroundingOffset, g);
			GraphicsUtility.drawArrow(Rotation.EAST, size.getXInt() - surroundingOffset, lineY, g);
		}
	}
}
