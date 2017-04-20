package net.net16.jeremiahlowe.scicalc.statistics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.JComponent;

import net.net16.jeremiahlowe.scicalc.utility.IToString;

public class StatisticsPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	DrawableList<String> dl = new DrawableList<String>();
	public StatisticsPanel() {
		dl.base.add("hello");
		dl.base.add("world");
		dl.base.add("foo");
		dl.base.add("bar");
		dl.strGetter = new IToString<String>() {
			@Override
			public String getString(String from) {
				System.out.println("getString()");
				return from.toString();
			}
		};
		//dl.setMaxBoxWidth(30);
	}
	@Override
	public void paintComponent(Graphics g){
		BufferedImage buffer = new BufferedImage(getWidth(), getHeight(), ColorModel.OPAQUE);
		drawUnbuffered(buffer.getGraphics());
		g.drawImage(buffer, 0, 0, null);
	}
	public void drawUnbuffered(Graphics g){
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(Color.BLACK);
		dl.draw(g, 10, 10);
	}
}
