import java.awt.Color;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.Point;
import net.net16.jeremiahlowe.scicalc.functions.FunctionFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.collections.Vector2Precise;

public class PointsAndFunctions {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D(); //Make the plane
		Point p = new Point(new Vector2Precise(1, 1)); //Create the point
		p.setColor(Color.BLUE);
		p.style = PointStyle.Square;
		p.fLabel.label = "Our point!";
		cp.addPoint(p); //Add the point 
		cp.takeScreenshot("demos/images/functions-and-points-1.png", "png");
		p.position.y -= 0.5d; //Reposition it
		cp.recalculate(); //Redraw the plane
		cp.takeScreenshot("demos/images/functions-and-points-2.png", "png");
		Parabola para = new Parabola();
		cp.addFunction(para);
		cp.recalculate();
		cp.takeScreenshot("demos/images/functions-and-points-3.png", "png");
		para.h = p.position.x;
		para.k = p.position.y;
		p.fLabel.label = "A parabola!";
		cp.recalculate();
		cp.takeScreenshot("demos/images/functions-and-points-4.png", "png");
		BinaryFunction pf = FunctionFactory.circle(p.position, 2);
		cp.addFunction(pf);
		cp.recalculate();
		cp.takeScreenshot("demos/images/functions-and-points-5.png", "png");
	}
}
class Parabola extends UnaryFunction{
	public double h = 0, k = 0;
	@Override
	public double f(double x) {
		return Math.pow(x - h, 2) + k;
	}
}
