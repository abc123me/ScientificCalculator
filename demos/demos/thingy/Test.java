package demos.thingy;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.DefaultController;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.BinaryFunction;

public class Test extends BinaryFunction{
	double a = 0;
	double b = 0;
	double c = 0;
	double f = 0;
	double amp = 0;
	public static void main(String[] args) {
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D(true);
		DefaultController dc = new DefaultController(cp);
		Test t = new Test();
		t.a = 0.05;
		t.b = 30;
		t.c = 50;
		t.amp = 0.1;
		t.f = 20;
		cp.addFunction(t);
	}
	@Override
	public double getPointAmount() {
		return 100000d;
	}
	@Override
	public double getThetaMin() {
		return -50;
	}
	@Override
	public double getThetaMax() {
		return 50;
	}
	@Override
	public double Xt(double t) {
		return amp * ((a - b) * Math.cos(t * f) + c * Math.cos((a / b - 1) * t * f));
	}
	@Override
	public double Yt(double t) {
		return amp * ((a - b) * Math.sin(t * f) + c * Math.sin((a / b - 1) * t * f));
	}
}
