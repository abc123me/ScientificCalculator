package net.net16.jeremiahlowe.scicalc.graphing.functions.tracer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.graphing.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.Point;
import net.net16.jeremiahlowe.scicalc.graphing.cartesian.Vector2Precise;

public abstract class AbstractFunctionTracer {
	private Point point;
	private List<Runnable> changeListeners;
	
	public AbstractFunctionTracer(Color pointColor, PointStyle pointStyle, int pointSize){
		point = new Point(new Vector2Precise(0, 0), pointColor, pointStyle, pointSize);
		changeListeners = new ArrayList<Runnable>();
	}
	
	public abstract void setPosition(double pos);
	public abstract void pan(double amount);
	public Point getPoint(){return point;}
	public final void addChangeListener(Runnable onChange){changeListeners.add(onChange);}
	public final void removeChangeListener(Runnable onChange){changeListeners.remove(onChange);}
	public final List<Runnable> getChangeListeners(){return changeListeners;}
	public final void delegateChangeEvent(){for(Runnable r : changeListeners) r.run();}
}
