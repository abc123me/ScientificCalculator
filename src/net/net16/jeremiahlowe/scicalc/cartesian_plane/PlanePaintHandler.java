package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Graphics;

public interface PlanePaintHandler {
	public void paint(CoordinatePlane cp, Graphics g);
	public void prePaint(CoordinatePlane cp, Graphics g);
	public void postPaint(CoordinatePlane cp, Graphics g);
}
