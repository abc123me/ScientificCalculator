package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class PlaneFactory {
	public static int exitOperation = JFrame.EXIT_ON_CLOSE;
	private static JFrame makeBaseFrame(String title, int w, int h, boolean full){
		JFrame j = new JFrame(title);
		j.setDefaultCloseOperation(exitOperation);
		if(full){
			j.setExtendedState(JFrame.MAXIMIZED_BOTH);
			j.setFocusableWindowState(true);
			j.setUndecorated(true);
		}
		j.setAlwaysOnTop(true);
		Dimension s = Toolkit.getDefaultToolkit().getScreenSize();
		int x = s.width / 2 - w / 2, y = s.height / 2 - h / 2;
		j.setBounds(x, y, w, h);
		return j;
	}
	public static CoordinatePlane makePlaneWindow2D(){return makePlaneWindow2D(null, false, 500, 500);}
	public static CoordinatePlane makePlaneWindow2D(String title){return makePlaneWindow2D(title, false, 500, 500);}
	public static CoordinatePlane makePlaneWindow2D(boolean fullscreen){return makePlaneWindow2D(null, fullscreen, 500, 500);}
	public static CoordinatePlane makePlaneWindow2D(int width, int height){return makePlaneWindow2D(null, false, width, height);}
	public static CoordinatePlane makePlaneWindow2D(String title, boolean fullscreen){return makePlaneWindow2D(title, fullscreen, 500, 500);}
	public static CoordinatePlane makePlaneWindow2D(String title, int width, int height){return makePlaneWindow2D(title, false, width, height);}
	public static CoordinatePlane makePlaneWindow2D(String title, boolean fullscreen, int width, int height){
		//Create a frame for the plane
		if(title == null) title = "Coordinate plane";
		JFrame j = makeBaseFrame("Coordinate plane", width, height, fullscreen);
		//Create the plane
		CoordinatePlane cp = new CoordinatePlane();
		cp.setViewportSize(10, 10);
		cp.setVisible(true); 
		cp.setEnabled(true);
		//Add the plane to the frame
		j.add(cp, BorderLayout.CENTER);
		//Re-Validate it so the plane will draw
		j.revalidate();
		j.setVisible(true);
		return cp;
	}
}
