package net.net16.jeremiahlowe.scicalc.statistics;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;
import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;
import net.net16.jeremiahlowe.scicalc.utility.IToString;
import net.net16.jeremiahlowe.scicalc.utility.Utility;

public class DrawableList<T>{
	public List<T> base = null;
	public IToString<T> toString = null;
	
	private Color foregroundColor;
	private Color normalBackgroundColor;
	private Color selectedBackgroundColor;
	private Color textColor;
	private Color selectedTextColor;
	private int selectedIndex = -1;
	private boolean showSelector = false;
	private int elementStringLength = -1;
	private int boxWidth = -1, boxHeight = -1;
	private int textSpacing = 3;
	
	public DrawableList(List<T> base, IToString<T> iToString){
		this.base = base;
		this.toString = iToString;
	}
	public DrawableList(List<T> base){this(base, defaultToString());}
	public DrawableList(){this(new ArrayList<T>());}
	public void draw(Graphics g, int x, int y){
		if(toString == null) throw new NullPointerException("IToString is null");
		else draw(g, toString, x, y);
	}
	public void draw(Graphics g, IToString<T> toString, int x, int y){
		FontMetrics fm = g.getFontMetrics();
		int fh = fm.getHeight();
		int mw = -1;
		String[] conv = new String[base.size()];
		for(int i = 0; i < base.size(); i++){
			String elem = toString.getString(base.get(i));
			if(elementStringLength > 0) elem = Utility.clampLength(elem, elementStringLength);
			conv[i] = elem;
			int w = (int) fm.getStringBounds(elem, g).getWidth();
			if(w > mw) mw = w;
		}
		for(int i = 0; i < base.size(); i++){
			String elem = conv[i];
			int cw = boxWidth > 0 ? boxWidth : mw; 
			int ch = boxHeight > 0 ? boxHeight : fh;
			int ty = y + ch * i;
			g.setColor(foregroundColor);
			g.drawRect(x, ty, cw, ch);
			g.setColor(textColor);
			g.drawString(elem, x + textSpacing, ty + fh);
		}
	}
	public int getBoxHeight(){return boxHeight;}
	public int getBoxWidth() {return boxWidth;}
	public void setBoxHeight(int boxHeight){this.boxHeight = boxHeight;}
	public int getElementStringLength() {return elementStringLength;}
	public void setElementStringLength(int elementStringLength) {this.elementStringLength = elementStringLength;}
	public void setBoxWidth(int boxWidth) {this.boxWidth = boxWidth;}
	public boolean getShowSelector() {return showSelector;}
	public void setShowSelector(boolean showSelector) {this.showSelector = showSelector;}
	public int getSelectedIndex() {return selectedIndex;}
	public void setSelectedIndex(int selectedIndex) {this.selectedIndex = selectedIndex;}
	public void selectNext(){selectedIndex++;}
	public void selectPrev(){selectedIndex--;}
	public void skipNext(int amount){selectedIndex += amount;}
	public void skipPrev(int amount){selectedIndex -= amount;}
	public Color getForegroundColor() {return foregroundColor;}
	public void setForegroundColor(Color foregroundColor) {this.foregroundColor = foregroundColor;}
	public Color getNormalBackgroundColor() {return normalBackgroundColor;}
	public void setNormalBackgroundColor(Color normalBackgroundColor) {this.normalBackgroundColor = normalBackgroundColor;}
	public Color getSelectedBackgroundColor() {return selectedBackgroundColor;}
	public void setSelectedBackgroundColor(Color selectedBackgroundColor) {this.selectedBackgroundColor = selectedBackgroundColor;}
	public Color getTextColor() {return textColor;}
	public void setTextColor(Color textColor) {this.textColor = textColor;}
	public Color getSelectedTextColor() {return selectedTextColor;}
	public void setSelectedTextColor(Color selectedTextColor) {this.selectedTextColor = selectedTextColor;}
	public static <U> IToString<U> defaultToString(){
		return new IToString<U>() {
			@Override 
			public String getString(U from) {
				return from.toString();
			}
		};
	}
	public void setBoxSize(int boxWidth2, int boxHeight2) {
		setBoxHeight(boxHeight2);
		setBoxWidth(boxWidth2);
	}
	public void setTextSpacing(int textSpacing) {this.textSpacing = textSpacing;}
	public int getTextSpacing(){return textSpacing;}
}
