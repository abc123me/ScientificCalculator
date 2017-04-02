package net.net16.jeremiahlowe.scicalc.graphing.testing;

import net.net16.jeremiahlowe.scicalc.functions.FunctionFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.tracer.FunctionTracerPanner;
import net.net16.jeremiahlowe.scicalc.functions.tracer.UnaryFunctionTracer;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.DefaultController;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.Vector2Precise;

public class TestCP2D {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D(true);
		DefaultController dc = new DefaultController(cp);
		dc.lockAxisSize = false;
		dc.slowAnimation = true;
		UnaryFunction q = FunctionFactory.quadratic(new Vector2Precise(0, -1), 2);
		q.setLineWidth(2);
		cp.addFunction(q);
		UnaryFunctionTracer uft = new UnaryFunctionTracer(q);
		FunctionTracerPanner ftp = new FunctionTracerPanner(uft);
		cp.addKeyListener(ftp);
		cp.addPoint(uft.getPoint());
		uft.addChangeListener(new Runnable(){
			@Override
			public void run() {
				cp.recalculate();
			}
		});
		/*boolean cs = false;
		for(float i = -10; i < 10; i += 0.1f){
			final float d = i;
			UnaryFunction f2 = new UnaryFunction(){
				@Override
				public double f(double x) {
					return (Math.cos(20 * x) + x / d) * Math.sin(x);
					//return x / d;
				}	
			};
			f2.setCustomIncrementer(false, 0.01f);
			f2.setColor(cs ? Color.RED : Color.BLUE);
			f2.setIgnoreBorders(true);
			cp.setViewQuadrant(Quadrant.ALL);
			cp.addFunction(f2);
			cp.setViewportSize(30, 30);
			cs = !cs;
		}*/
	}
}
