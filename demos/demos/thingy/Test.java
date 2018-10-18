package demos.thingy;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.DefaultController;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;
import net.net16.jeremiahlowe.shared.math.Vector;

public class Test extends BinaryFunction{
	double a = 0;
	double b = 0;
	double c = 0;
	double f = 0;
	double amp = 0;
	public static void main(String[] args) {
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D(true);
		new DefaultController(cp);
		for(int i = 0; i < 2; i++) {
			Test t = new Test();
			t.a = 3;
			t.b = (Math.PI) / 3;
			t.c = Math.PI * (i / 100.0);
			t.amp = 0.1;
			t.f = 20;
			cp.addFunction(t);
		}
	}
	@Override
	public double getPointAmount() {
		return 1000d;
	}
	@Override
	public final double getThetaMin(Vector pixelSize, Vector domain, Vector range){
		return 0;
	}
	@Override
	public final double getThetaMax(Vector pixelSize, Vector domain, Vector range){
		return 2 * Math.PI;
	}
	@Override
	public final double Xt(double t){
	return Math.cos(t) * Math.cos(t);
	}
	@Override
	public final double Yt(double t){
		return Math.sin(t) * Math.sin(t);
	}
	public double r(double t) {
		double p1 = 1 + 0.9 * Math.cos(8 * t);
		double p2 = 1 + 0.1 * Math.cos(24 * t);
		double p3 = 0.9 + 0.05 * Math.cos(200 * t);
		double p4 = 1 + Math.sin(t);
		return p1 * p2 * p3 * p4 * c * a;
	}
}
