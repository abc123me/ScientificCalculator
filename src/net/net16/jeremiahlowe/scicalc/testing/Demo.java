package net.net16.jeremiahlowe.scicalc.testing;

import java.awt.Color;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.Point;
import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

public class Demo {

	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		Point p = new Point(new Vector2Precise(1, 1));

		p.setColor(Color.BLUE);
		p.style = PointStyle.Square;
		p.fLabel.label = "Our point!";
		
		cp.addPoint(p);
		
		p.position.y -= 0.5d;
		
		cp.recalculate();
		cp.takeScreenshot("plane.png", "png");
	}

}
