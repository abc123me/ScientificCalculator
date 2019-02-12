package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.Graphics;

public interface PlanePaintHandler {
	/**
	 * Called after the background is drawn, but before the axis, points, functions, and labels are drawn
	 * Lifecycle: Enter, PrePaint, Axis grid, Points, Functions, Paint, Axes, PostPaint
	 *
	 * @param cp The CoordinatePlane object holding the PaintHandler
	 * @param g The Graphics object being painted to
	 */
	public void prePaint(CoordinatePlane cp, Graphics g);
	/**
	 * Called after prePaint(), and functions, labels, points, etc.
	 * Lifecycle: Enter, PrePaint, Axis grid, Points, Functions, Paint, Axes, PostPaint, Exit
	 * 
	 * @param cp The CoordinatePlane object holding the PaintHandler
	 * @param g The Graphics object being painted to
	 */
	public void paint(CoordinatePlane cp, Graphics g);
	/**
	 * Called after a call to paint() and the axis are drawn;
	 * Lifecycle: Enter, PrePaint, Axis grid, Points, Functions, Paint, Axes, PostPaint, Exit
	 * 
	 * @param cp The CoordinatePlane object holding the PaintHandler
	 * @param g The Graphics object being painted to
	 */
	public void postPaint(CoordinatePlane cp, Graphics g);
}
