package net.net16.jeremiahlowe.scicalc;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.net16.jeremiahlowe.bettercollections.Rotation;
import net.net16.jeremiahlowe.bettercollections.vector.Vector2;
import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;

public class Utility {
	public static final Vector2 getBoundingBox(Vector2... verticies){
		Vector2 out = new Vector2(Float.MIN_VALUE, Float.MIN_VALUE);
		for(int i = 0; i < verticies.length; i++){
			if(verticies[i].x > out.x) out.x = verticies[i].x;
			if(verticies[i].y > out.y) out.y = verticies[i].y;
		}
		return out;
	}
	public static final Vector2 getMidpoint(Vector2... verticies){
		Vector2 origin = new Vector2();
		for(int i = 0; i < verticies.length; i++) origin.translate(verticies[i]);
		origin.x /= verticies.length; origin.y /= verticies.length;
		return origin;
	}
	public static final void drawArrow(Rotation r, int x, int y, Graphics g){
		Vector2[] v = rotateVerticiesAround(r, new Vector2(x, y), getArrowLines(x, y));
		g.drawLine(v[0].getXI(), v[0].getYI(), v[1].getXI(), v[1].getYI()); //Shaft [0, 1]
		g.drawLine(v[2].getXI(), v[2].getYI(), v[3].getXI(), v[3].getYI()); //Left [2, 3]
		g.drawLine(v[4].getXI(), v[4].getYI(), v[5].getXI(), v[5].getYI()); //Right [4, 5]
	}
	public static final void drawArrowWithWidth(Rotation r, int x, int y, Graphics g, int lw){
		Vector2[] v = rotateVerticiesAround(r, new Vector2(x, y), getArrowLines(x, y));
		drawLineWithWidth(g, v[0].getXI(), v[0].getYI(), v[1].getXI(), v[1].getYI(), lw); //Shaft [0, 1]
		drawLineWithWidth(g, v[2].getXI() - lw / 2, v[2].getYI(), v[3].getXI() - lw / 2, v[3].getYI(), lw); //Left [2, 3]
		drawLineWithWidth(g, v[4].getXI(), v[4].getYI(), v[5].getXI(), v[5].getYI(), lw); //Right [4, 5]
	}
	public static final Vector2[] getArrowLines(int x, int y){
		Vector2[] verticies = new Vector2[6];
		//Shaft
		verticies[0] = new Vector2(x, y - 3);
		verticies[1] = new Vector2(x, y + 3);
		//Left side
		verticies[2] = new Vector2(x, y - 3);
		verticies[3] = new Vector2(x - 3, y);
		//Right side
		verticies[4] = new Vector2(x, y - 3);
		verticies[5] = new Vector2(x + 3, y);
		return verticies;
	}
	public static final Vector2[] rotateVerticiesAround(Rotation rot, Vector2 origin, Vector2... verticies){
		if(rot.getAngleDegrees() == 360 || rot.getAngleDegrees() == 0) return verticies;
		else{
			Vector2[] out = new Vector2[verticies.length];
			float angle = rot.getAngleRadians();
			for(int i = 0; i < out.length; i++){
				float x1 = verticies[i].x - origin.x, y1 = verticies[i].y - origin.y;
				double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
				double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
				out[i] = new Vector2();
				out[i].x = (float) x2 + origin.x;
				out[i].y = (float) y2 + origin.y;
			}
			return out;
		}
	}
	public static final void drawLineWithWidth(Graphics g, int x1, int y1, int x2, int y2, int lw){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(lw));
		g2.drawLine(x1, y1, x2, y2);
	}
	public static final Vector2[] rotateVerticiesAroundMidpoint(Rotation rot, Vector2... verticies){
		return rotateVerticiesAround(rot, getMidpoint(verticies), verticies);
	}
	public static final boolean between(long max, long min, long val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(int max, int min, int val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(float max, float min, float val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	public static final boolean between(double max, double min, double val){
		if(val >= max || val <= min) return false;
		else return true;
	}
	
	public static final boolean betweenOrEqual(long max, long min, long val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(int max, int min, int val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(float max, float min, float val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static final boolean betweenOrEqual(double max, double min, double val){
		if(val > max || val < min) return false;
		else return true;
	}
	public static double clamp(double min, double max, double val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static long clamp(long min, long max, long val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static float clamp(float min, float max, float val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static int clamp(int min, int max, int val){
		if(val > max) return max;
		else if(val < min) return min;
		else return val;
	}
	public static void resetWidth(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
	}
	public static void drawCenteredString(Graphics g, String str, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		int strW = fm.stringWidth(str), strH = fm.getHeight();
		x -= strW / 2; y += strH / 5;
		g.drawString(str, x, y);
	}
	public static void drawAllignedString(Graphics g, String str, int x, int y, HorizontalAllignment h, VerticalAllignment v){
		FontMetrics fm = g.getFontMetrics();
		int strW = fm.stringWidth(str), strH = fm.getHeight();
		double ax = h.interpolate(0, strW);
		double ay = v.interpolate(0, strH);
		g.drawString(str, (int) (x + ax), (int) (y + ay));
	}
	public static void drawRotatedString(Graphics g, Rotation r, String str, int x, int y) {
		Graphics2D g2 = (Graphics2D) g;
		double angleRadians = r.getAngleRadians();
		g2.translate(x, y);
		g2.rotate(angleRadians);
		drawCenteredString(g2, str, 0, 0);
		g2.rotate(-angleRadians);
		g2.translate(-x, -y);
	}
	public static String numberToString(double num, int digits){
		if(digits < 0) throw new RuntimeException("Digits must be >= 0!");
		String[] parts = String.valueOf(num).split("[.]");
		if(parts[1].matches("0")) return parts[0];
		if(parts[1].length() > digits){
			String end = parts[1].substring(0, digits);
			while(end.endsWith("0")) end = end.substring(0, end.length() - 1);
			return parts[0] + '.' + end;
		}
		return parts[0] + '.' + parts[1];
	}
	@SuppressWarnings("unchecked")
	public static <T> boolean anyOf(T any, T... of){
		for(T t : of){
			if(any == t) return true;
		}
		return false;
	}
}
