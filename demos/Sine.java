import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.DefaultController;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.PlaneFactory;
import net.net16.jeremiahlowe.scicalc.functions.FunctionFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;

public class Sine{
	public static int amount = 20;
	public static double amplitude = 1;
	public static UnaryFunction[] toPlot = new UnaryFunction[amount];
	
	public static void regen(int amount) {
		toPlot = new UnaryFunction[amount];
		for(int i = 0; i < amount; i++) {
			int j = i * 2 + 1;
			toPlot[i] = FunctionFactory.sin(amplitude / j, j);
			toPlot[i].setColor(Color.BLUE);
		}
	}
	public static void main(String[] args) {
		CoordinatePlane cp = PlaneFactory.makePlaneWindow2D();
		cp.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {}
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_EQUALS) amount++;
				if(e.getKeyCode() == KeyEvent.VK_MINUS) amount--;
				if(amount <= 0) amount = 1;
				for(UnaryFunction u : toPlot)
					cp.removeFunction(u);
				regen(amount);
				for(UnaryFunction u : toPlot)
					cp.addFunction(u);
				cp.repaint();
			}
			@Override
			public void keyReleased(KeyEvent e) {}
		});
		new DefaultController(cp);
		regen(amount);
		UnaryFunction sum = new UnaryFunction() {
			@Override
			public double f(double x) {
				double fx = 0;
				for(UnaryFunction u : toPlot) {
					fx += u.f(x);
				}
				return fx;
			}
		};
		sum.setLineWidth(3);
		for(UnaryFunction u : toPlot)
			cp.addFunction(u);
		cp.addFunction(sum);
		cp.repaint();
	}
}
