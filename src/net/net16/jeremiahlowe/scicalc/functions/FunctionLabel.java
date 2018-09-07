package net.net16.jeremiahlowe.scicalc.functions;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.shared.math.Rotation;
import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.shared.math.Vector;

public class FunctionLabel {
	public String label = null;
	public Color color;
	public Rotation labelRotation;
	public Vector offset = new Vector(0, 0);
	public HorizontalAllignment hAllign = HorizontalAllignment.Center;
	public VerticalAllignment vAllign = VerticalAllignment.Center;
	
	public FunctionLabel(Color color){
		this.color = color;
		labelRotation = new Rotation(0);
	}
	
	public void draw(Graphics g, Vector maxPos, Vector minPos) {
		g.setColor(color);
		if(label != null){
			int x = (int) Math.round(hAllign.negate().interpolate(minPos.x, maxPos.x) + offset.x);
			int y = (int) Math.round(vAllign.negate().interpolate(minPos.y, maxPos.y) + offset.y);
			if(labelRotation.getAngleDegrees() == 0) GraphicsUtility.drawCenteredString(g, label, x, y);
			else GraphicsUtility.drawRotatedString(g, labelRotation, label, x, y);
		}
	}
}
