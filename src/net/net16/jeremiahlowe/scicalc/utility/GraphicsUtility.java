package net.net16.jeremiahlowe.scicalc.utility;

import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

import net.net16.jeremiahlowe.shared.math.Rotation;
import net.net16.jeremiahlowe.shared.math.Vector;
import net.net16.jeremiahlowe.shared.math.VectorMath;
import net.net16.jeremiahlowe.scicalc.Enums.HorizontalAllignment;
import net.net16.jeremiahlowe.scicalc.Enums.VerticalAllignment;

public class GraphicsUtility {
	public static final void drawLineWithWidth(Graphics g, int x1, int y1, int x2, int y2, int lw){
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(lw));
		g2.drawLine(x1, y1, x2, y2);
	}
	public static final void drawArrowWithWidth(Rotation r, int x, int y, Graphics g, int lw){
		Vector[] v = VectorMath.rotateVerticiesAround(r, new Vector(x, y), GraphicsUtility.getArrowLines(x, y));
		drawLineWithWidth(g, v[0].getXInt(), v[0].getYInt(), v[1].getXInt(), v[1].getYInt(), lw); //Shaft [0, 1]
		drawLineWithWidth(g, v[2].getXInt() - lw / 2, v[2].getYInt(), v[3].getXInt() - lw / 2, v[3].getYInt(), lw); //Left [2, 3]
		drawLineWithWidth(g, v[4].getXInt(), v[4].getYInt(), v[5].getXInt(), v[5].getYInt(), lw); //Right [4, 5]
	}
	public static final void drawArrow(Rotation r, int x, int y, Graphics g){
		Vector[] v = VectorMath.rotateVerticiesAround(r, new Vector(x, y), GraphicsUtility.getArrowLines(x, y));
		g.drawLine(v[0].getXInt(), v[0].getYInt(), v[1].getXInt(), v[1].getYInt()); //Shaft [0, 1]
		g.drawLine(v[2].getXInt(), v[2].getYInt(), v[3].getXInt(), v[3].getYInt()); //Left [2, 3]
		g.drawLine(v[4].getXInt(), v[4].getYInt(), v[5].getXInt(), v[5].getYInt()); //Right [4, 5]
	}
	public static final Vector[] getArrowLines(int x, int y){
		Vector[] verticies = new Vector[6];
		//Shaft
		verticies[0] = new Vector(x, y - 3);
		verticies[1] = new Vector(x, y + 3);
		//Left side
		verticies[2] = new Vector(x, y - 3);
		verticies[3] = new Vector(x - 3, y);
		//Right side
		verticies[4] = new Vector(x, y - 3);
		verticies[5] = new Vector(x + 3, y);
		return verticies;
	}
	public static void drawCenteredString(Graphics g, String str, int x, int y) {
		FontMetrics fm = g.getFontMetrics();
		int strW = fm.stringWidth(str), strH = fm.getHeight();
		x -= strW / 2; y += strH / 5;
		g.drawString(str, x, y);
	}
	public static void resetWidth(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
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
}
