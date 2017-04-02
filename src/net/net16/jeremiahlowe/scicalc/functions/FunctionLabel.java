package net.net16.jeremiahlowe.scicalc.functions;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.bettercollections.Rotation;
import net.net16.jeremiahlowe.scicalc.graphing.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.graphing.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.Vector2Precise;
import net.net16.jeremiahlowe.scicalc.graphing.Utility;

public class FunctionLabel {
	public String label = null;
	public Color color;
	public Rotation labelRotation;
	public Vector2Precise offset = new Vector2Precise(0, 0);
	public HorizontalAllignment hAllign = HorizontalAllignment.Center;
	public VerticalAllignment vAllign = VerticalAllignment.Center;
	
	public FunctionLabel(Color color){
		this.color = color;
		labelRotation = new Rotation(0);
	}
	
	public void draw(Graphics g, Vector2Precise maxPos, Vector2Precise minPos) {
		g.setColor(color);
		if(label != null){
			int x = (int) Math.round(hAllign.negate().interpolate(minPos.x, maxPos.x) + offset.x);
			int y = (int) Math.round(vAllign.negate().interpolate(minPos.y, maxPos.y) + offset.y);
			if(labelRotation.getAngleDegrees() == 0) Utility.drawCenteredString(g, label, x, y);
			else Utility.drawRotatedString(g, labelRotation, label, x, y);
		}
	}
}
