package net.net16.jeremiahlowe.scicalc.statistics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.util.List;

import javax.swing.JComponent;

import net.net16.jeremiahlowe.scicalc.functions.FunctionFactory;
import net.net16.jeremiahlowe.scicalc.functions.std.UnaryFunction;
import net.net16.jeremiahlowe.scicalc.utility.DoubleVector;
import net.net16.jeremiahlowe.scicalc.utility.Utility;

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
	public UnaryFunction asYEqualsAXPlusB(int xList, int yList){
		DoubleVector[] points = getListsAsPointsArray(xList, yList);
		float avgSlope = 0, avgIntercept = 0;
		DoubleVector lastPoint = null, currentPoint = null;
		for(int i = 1; i < points.length; i++){
			lastPoint = points[i - 1];
			currentPoint = points[i];
			avgSlope += Utility.slope(lastPoint, currentPoint);
			avgIntercept += Utility.yIntercept(lastPoint, currentPoint);
		}
		avgSlope /= points.length;
		avgIntercept /= points.length;
		return FunctionFactory.line(avgSlope, avgIntercept);
	}
	public DoubleVector[] getListsAsPointsArray(int xList, int yList){
		double[] xValues = new double[lists[xList].base.size()];
		double[] yValues = new double[lists[yList].base.size()];
		int xLen = xValues.length, yLen = yValues.length;
		for(int i = 0; i < xLen; i++)
			xValues[i] = lists[xList].base.get(i).doubleValue();
		for(int i = 0; i < yLen; i++)
			yValues[i] = lists[yList].base.get(i).doubleValue();
		if(xLen != yLen){
			int minLen = xLen < yLen ? xLen : yLen;
			int maxLen = xLen > yLen ? xLen : yLen;
			double[] resized = new double[maxLen];
			for(int i = 0; i < minLen; i++)
				resized[i] = (xLen < yLen ? xValues : yValues)[i];
			for(int i = minLen - 1; i < maxLen; i++)
				resized[i] = 0;
		}
		return Utility.combineXYArrays(xValues, yValues);
	}
}
