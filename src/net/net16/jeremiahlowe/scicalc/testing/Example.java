package net.net16.jeremiahlowe.scicalc.testing;

import javax.swing.JFrame;

import net.net16.jeremiahlowe.scicalc.cartesian_plane.CoordinatePlane;

public class Example {
	public static void main(String[] args){
		CoordinatePlane cp = new CoordinatePlane();
		JFrame j = new JFrame();
		j.add(cp);
		j.setBounds(100, 100, 300, 300);
		j.setVisible(true);
	}
}
