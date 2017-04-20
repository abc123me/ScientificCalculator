package net.net16.jeremiahlowe.scicalc.statistics;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.utility.IToString;
import net.net16.jeremiahlowe.scicalc.utility.Utility;

public class DrawableList<T>{
	public List<T> base;
	public IToString<T> strGetter;
	private int elementStringLength = -1;
	private int maxBoxWidth = -1;
	private int borderSize = 2;
	public DrawableList(List<T> base, IToString<T> strGetter){
		this.base = base;
		this.strGetter = strGetter;
	}
	public DrawableList(List<T> base){this(base, new IToString<T>() {
		@Override public String getString(T from) {return from.toString();}
	});}
	public DrawableList(){this(new ArrayList<T>());}
	public void draw(Graphics g, int x, int y){
		if(strGetter == null) throw new NullPointerException("IToString is null");
		else draw(g, strGetter, x, y);
	}
	public void draw(Graphics g, IToString<T> strGetter, int x, int y){
		FontMetrics fm = g.getFontMetrics();
		int fh = fm.getHeight();
		int mw = -1;
		String[] conv = new String[base.size()];
		for(int i = 0; i < base.size(); i++){
			String elem = strGetter.getString(base.get(i));
			if(elementStringLength > 0) elem = Utility.clampLength(elem, elementStringLength);
			conv[i] = elem;
			int w = (int) fm.getStringBounds(elem, g).getWidth();
			if(w > mw) mw = w;
		}
		for(int i = 0; i < base.size(); i++){
			String elem = conv[i];
			int ty = y + fh * i;
			g.drawString(elem, x + borderSize, ty + fh - borderSize);
			int cw = maxBoxWidth > 0 && mw < maxBoxWidth ? maxBoxWidth : mw; 
			g.drawRect(x, ty, cw + borderSize, fh);
		}
	}
	public int getElementStringLength() {return elementStringLength;}
	public void setElementStringLength(int elementStringLength) {this.elementStringLength = elementStringLength;}
	public int getMaxBoxWidth() {return maxBoxWidth;}
	public void setMaxBoxWidth(int maxBoxWidth) {this.maxBoxWidth = maxBoxWidth;}
	public int getBorderSize() {return borderSize;}
	public void setBorderSize(int borderSize) {this.borderSize = borderSize;}
}
