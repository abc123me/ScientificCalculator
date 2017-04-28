package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Graphics;

import net.net16.jeremiahlowe.scicalc.Enums.DrawTime;

public abstract class PlanePaintHandler {
	public DrawTime drawOn = DrawTime.PrePaint;
	public abstract void paint(Graphics g);
}
