package net.net16.jeremiahlowe.scicalc.testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlanePaintHandler;

public class TheresCodeHere {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		cp.addPaintHandler(new PlanePaintHandler() {
			@Override
			public void prePaint(Graphics g) {
				g.setColor(Color.BLACK);
				int so = cp.getSurroundingOffset();
				g.setFont(new Font("Comic Sans MS", Font.PLAIN, 24)); 
				g.drawString("Hello github!", so, g.getFontMetrics().getHeight() + so);
			}
			@Override public void paint(Graphics g) {}
			@Override public void postPaint(Graphics g) {}
		});
		cp.takeScreenshot("demos/github-logo-paint-handler.png", "png");
	}

}
