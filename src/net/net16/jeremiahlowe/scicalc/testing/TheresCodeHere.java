package net.net16.jeremiahlowe.scicalc.testing;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlanePaintHandler;

public class TheresCodeHere {

	public static void main(String[] args) {
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		cp.addPaintHandler(new PlanePaintHandler() {
			@Override
			public void paint(Graphics g) {
				int width = cp.getWidth(), height = cp.getHeight();
				g.setColor(Color.YELLOW);
				g.drawRect(0, 0, width, height);
				
			}
		});
		cp.repaint();
	}

}
