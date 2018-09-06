package demos;
import java.awt.Color;

import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;

public class MainPageGraphs {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		
		UnaryFunction uf = new UnaryFunction() {
			@Override
			public double f(double x) {
				return Math.sin(x);
			}
		};
		cp.addFunction(uf);
		uf.setLineWidth(2);
		uf.getFunctionLabel().label = "sin x";
		uf.getFunctionLabel().vAllign = VerticalAllignment.Up;
		uf.getFunctionLabel().hAllign = HorizontalAllignment.HalfLeft;
		uf.getFunctionLabel().labelRotation.setAngleDegrees(40);
		
		UnaryFunction uf2 = new UnaryFunction() {
			@Override
			public double f(double x) {
				return Math.cos(x);
			}
		};
		cp.addFunction(uf2);
		uf2.setLineWidth(2);
		uf2.getFunctionLabel().label = "cos x";
		uf2.setColor(Color.BLUE);
		uf2.getFunctionLabel().vAllign = VerticalAllignment.HalfDown;
		uf2.getFunctionLabel().hAllign = HorizontalAllignment.HalfLeft;
		uf2.getFunctionLabel().labelRotation.setAngleDegrees(40);
		
		cp.recalculate();
		cp.takeScreenshot("demos/images/sine-main-page.png", "png");
		System.exit(0);
	}
}
