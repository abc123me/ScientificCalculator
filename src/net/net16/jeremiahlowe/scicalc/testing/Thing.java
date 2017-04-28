package net.net16.jeremiahlowe.scicalc.testing;

import javax.swing.JFrame;

import net.net16.jeremiahlowe.scicalc.statistics.StatisticsPanel;

public class Thing {
	public static void main(String[] args){
		JFrame j = new JFrame();
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		StatisticsPanel sp = new StatisticsPanel(4);
		j.setContentPane(sp);
		j.setBounds(100, 100, 300, 200);
		sp.setVisible(true);
		j.setVisible(true);
	}
}
