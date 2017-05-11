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
		VerticalAllignment allign2 = VerticalAllignment.Custom;
		allign2.setCustomPercent(0.63d);
		uf.getFunctionLabel().vAllign = allign2;
		HorizontalAllignment allign = HorizontalAllignment.Custom;
		allign.setCustomPercent(0.35d);
		uf.getFunctionLabel().hAllign = allign;
		
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
		VerticalAllignment allign4 = VerticalAllignment.Custom;
		allign4.setCustomPercent(0.63d);
		uf2.getFunctionLabel().vAllign = allign4;
		HorizontalAllignment allign3 = HorizontalAllignment.Custom;
		allign.setCustomPercent(0.35d);
		uf2.getFunctionLabel().hAllign = allign3;
		
		cp.recalculate();
		cp.takeScreenshot("demos/images/sine-main-page.png", "png");
		//System.exit(0);
	}
}
