package net.net16.jeremiahlowe.scicalc.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;

import net.net16.jeremiahlowe.scicalc.utility.GraphicsUtility;

public class SimpleButton extends JComponent {
	private static final long serialVersionUID = -7912227360605364251L;
	
	private String text = "";
	private int curvature = 0;
	private int borderWidth = 0;
	private Color textColor = Color.BLACK;
	private boolean showSelected = true;
	private Color selectedBackground = new Color(125, 125, 200);
	private Color selectedForeground = new Color(125, 125, 200);
	private boolean selected = false;
	private boolean toggle = false;
	
	public SimpleButton(String text, int x, int y, int width, int height, int curvature, Color textColor) {
		super();
		this.curvature = curvature;
		this.textColor = textColor;
		this.text = text;
		setBounds(x, y, width, height);
		setForeground(Color.BLACK);
		setBackground(Color.WHITE);
		addMouseListener(new MouseAdapter(){
			@Override public void mousePressed(MouseEvent me){press();}
			@Override public void mouseReleased(MouseEvent me){release();}
		});
	}
	public SimpleButton(String text, int x, int y, int width, int height, int curvature) {this(text, x, y, width, height, curvature, Color.BLACK);}
	public SimpleButton(String text, int x, int y, int width, int height){this(text, x, y, width, height, 0);} 
	public SimpleButton(String text, int x, int y){this(text, x, y, 75, 25, 0);} 
	public SimpleButton(String text){this(text, 0, 0);} 
	public SimpleButton(){this("Button");} 
	
	@Override
	public void paintComponent(Graphics g){
		int w = getWidth(), h = getHeight();
		g.setColor((selected && showSelected) ? selectedForeground : getForeground());
		g.fillRoundRect(0, 0, w - 1, h - 1, curvature, curvature);
		if(borderWidth >= 1){
			g.setColor((selected && showSelected) ? selectedBackground : getBackground());
			g.fillRoundRect(borderWidth, borderWidth, (w - 2 * borderWidth) - 1, (h - 2 * borderWidth) - 1, curvature, curvature);
		}
		g.setColor(textColor);
		GraphicsUtility.drawCenteredString(g, text, w / 2, h / 2);
	}

	public Color getTextColor() {return textColor;}
	public void setTextColor(Color textColor) {this.textColor = textColor;}
	public String getText() {return text;}
	public void setText(String text) {this.text = text;}
	public int getCurvature() {return curvature;}
	public void setCurvature(int curvature) {this.curvature = curvature;}
	public int getBorderWidth() {return borderWidth;}
	public void setBorderWidth(int borderWidth) {this.borderWidth = borderWidth;}
	public boolean shouldShowSelected() {return showSelected;}
	public void setShowSelected(boolean showSelected) {this.showSelected = showSelected;}
	public Color getSelectedBackground() {return selectedBackground;}
	public void setSelectedBackground(Color selectedBackground) {this.selectedBackground = selectedBackground;}
	public Color getSelectedForeground() {return selectedForeground;}
	public void setSelectedForeground(Color selectedForeground) {this.selectedForeground = selectedForeground;}
	public boolean isSelected() {return selected;}
	public void setSelected(boolean selected) {
		this.selected = selected;
		repaint();
	}
	public boolean isToggle() {return toggle;}
	public void setToggle(boolean toggle) {this.toggle = toggle;}
	public void press(){
		if(!toggle){
			selected = true;
			repaint();
		}
	}
	public void release(){
		if(toggle) selected = !selected;
		else selected = false;
		repaint();
	}
	public void click(){press(); release();}
	public void click(long ms) throws InterruptedException{
		press();
		Thread.sleep(ms);
		release();
	}
}
