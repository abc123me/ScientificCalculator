package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.functions.FunctionLabel;
import net.net16.jeremiahlowe.scicalc.utility.DoubleVector;

public class Point implements Cloneable{
	public FunctionLabel fLabel;
	private Color color;
	public DoubleVector position;
	public PointStyle style = PointStyle.Circle;
	public int size = 3;
	
	//[start] Constructors
	public Point(DoubleVector position, Color color, PointStyle style, int size){
		this.position = position;
		this.color = color;
		this.style = style;
		this.size = size;
		this.fLabel = new FunctionLabel(color);
	}
	public Point(DoubleVector position, Color color, int size){this(position, color, PointStyle.Circle, size);}
	public Point(DoubleVector position, PointStyle style, int size){this(position, Color.BLACK, style, size);}
	public Point(DoubleVector position, Color color, PointStyle style){this(position, color, style, 3);}
	public Point(DoubleVector position, PointStyle style){this(position, Color.BLACK, style, 3);}
	public Point(DoubleVector position, int size){this(position, Color.BLACK, 3);}
	public Point(DoubleVector position, Color color){this(position, color, 3);}
	public Point(DoubleVector position){this(position, Color.BLACK, 3);}
	//[end]
	
	public void draw(Graphics g, int x, int y, int size) {
		style.draw(g, x, y, size);
		DoubleVector pos = new DoubleVector(x, y + 5);
		fLabel.draw(g, pos, pos.add(new DoubleVector(size)));
	}
	public void setColor(Color color){
		this.color = color;
		fLabel.color = color;
	}
	public Color getColor(){
		return color;
	}
	
	@Override
	public Point clone(){
		return new Point(position.clone(), new Color(color.getRed(), color.getGreen(), color.getBlue()));
	}
}
