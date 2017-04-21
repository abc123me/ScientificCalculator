package net.net16.jeremiahlowe.scicalc.statistics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

import javax.swing.JComponent;

public class StatisticsPanel extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private int boxWidth = 50, boxHeight = 20;
	private DrawableList<Double>[] lists;
	private Color textColor = Color.BLACK;
	private Color selectedColor = new Color(125, 125, 200);
	private int textSpacing = 3;
	
	@SuppressWarnings("unchecked")
	public StatisticsPanel(int listAmount) {
		setForeground(Color.BLACK);
		lists = new DrawableList[listAmount];
		for(int i = 0; i < lists.length; i++){
			lists[i] = new DrawableList<Double>();
			lists[i].base.add(new Double(i));
			lists[i].base.add(new Double(i));
			lists[i].base.add(new Double(i));
			lists[i].base.add(new Double(i));
		}
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
		for(int i = 0; i < lists.length; i++){
			int x = i * boxWidth;
			
			g.setColor(getForeground());
			g.drawRect(x, 0, boxWidth, boxHeight);		
			g.setColor(Color.RED);
			g.drawString("List: " + (i + 1), x + textSpacing, boxHeight - textSpacing);

			drawList(g, lists[i], x, boxHeight);
		}
	}
	private void drawList(Graphics g, DrawableList<Double> list, int x, int y){
		list.setBoxSize(boxWidth, boxHeight);
		list.setTextColor(textColor);
		list.setForegroundColor(getForeground());
		list.setNormalBackgroundColor(getBackground());
		list.setSelectedBackgroundColor(selectedColor);
		list.setTextSpacing(textSpacing);
		list.draw(g, x, y);
	}
}
