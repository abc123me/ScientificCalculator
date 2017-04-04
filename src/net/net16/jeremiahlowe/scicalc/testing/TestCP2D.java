package net.net16.jeremiahlowe.scicalc.testing;

import java.awt.Color;

import net.net16.jeremiahlowe.scicalc.Enums.LineStyle;
import net.net16.jeremiahlowe.scicalc.functions.FunctionFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.functions.tracer.FunctionTracerPanner;
import net.net16.jeremiahlowe.scicalc.functions.tracer.UnaryFunctionTracer;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.DefaultController;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.PlaneFactory;

public class TestCP2D {
	public static void main(String[] args) throws Exception{
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D(true);
		DefaultController dc = new DefaultController(cp);
		dc.lockAxisSize = false;
		dc.slowAnimation = true;
		UnaryFunction q = FunctionFactory.quadratic(1, 0, 0);
		q.setLineStyle(LineStyle.DashedDotted);
		q.setLineWidth(2);
		//q.setInequality(Inequality.LessThan);
		cp.addFunction(q);
		UnaryFunctionTracer uft = new UnaryFunctionTracer(q);
		FunctionTracerPanner ftp = new FunctionTracerPanner(uft);
		ftp.setPanAmount(0.25d);
		cp.addKeyListener(ftp);
		cp.addPoint(uft.getPoint());
		uft.addChangeListener(new Runnable(){
			@Override
			public void run() {
				cp.recalculate();
			}
		});/*
		cp.setViewportSize(30, 30);
		boolean cs = false;
		for(float i = -10; i <= 10; i += 0.25f){
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
			cp.addFunction(f2);
			cs = !cs;
		}
		cp.recalculate();/**/
	}
}
