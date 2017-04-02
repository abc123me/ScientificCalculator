package net.net16.jeremiahlowe.scicalc.graphing.cartesian;

import java.awt.Color;
import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.functions.FunctionLabel;

public class Point implements Cloneable{
	public FunctionLabel fLabel;
	private Color color;
	public Vector2Precise position;
	public PointStyle style = PointStyle.Circle;
	public int size = 3;
	
	//[start] Constructors
	public Point(Vector2Precise position, Color color, PointStyle style, int size){
		this.position = position;
		this.color = color;
		this.style = style;
		this.size = size;
		this.fLabel = new FunctionLabel(color);
	}
	public Point(Vector2Precise position, Color color, int size){this(position, color, PointStyle.Circle, size);}
	public Point(Vector2Precise position, PointStyle style, int size){this(position, Color.BLACK, style, size);}
	public Point(Vector2Precise position, Color color, PointStyle style){this(position, color, style, 3);}
	public Point(Vector2Precise position, PointStyle style){this(position, Color.BLACK, style, 3);}
	public Point(Vector2Precise position, int size){this(position, Color.BLACK, 3);}
	public Point(Vector2Precise position, Color color){this(position, color, 3);}
	public Point(Vector2Precise position){this(position, Color.BLACK, 3);}
	//[end]
	
	public void draw(Graphics g, int x, int y, int size) {
		style.draw(g, x, y, size);
		Vector2Precise pos = new Vector2Precise(x, y + 5);
		fLabel.draw(g, pos, pos.add(new Vector2Precise(size)));
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
