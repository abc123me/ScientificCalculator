import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;

public class MainPageGraphs {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		cp.clearAllFunctions();
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
		uf.getFunctionLabel().hAllign = allign;;
		cp.recalculate();
		cp.takeScreenshot("demos/images/sine-main-page.png", "png");
		System.exit(0);
	}
}
