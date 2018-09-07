package net.net16.jeremiahlowe.scicalc.functions.tracer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import net.net16.jeremiahlowe.scicalc.Enums.PointStyle;
import net.net16.jeremiahlowe.scicalc.cartesian_plane.Point;
import net.net16.jeremiahlowe.shared.math.Vector;

public abstract class AbstractFunctionTracer {
	protected Point point;
	protected List<Runnable> changeListeners;
	
	public AbstractFunctionTracer(Color pointColor, PointStyle pointStyle, int pointSize){
		point = new Point(new Vector(0, 0), pointColor, pointStyle, pointSize);
		changeListeners = new ArrayList<Runnable>();
	}
	
	public abstract void setPosition(float pos);
	public abstract void pan(float amount);
	public Point getPoint(){return point;}
	public final void addChangeListener(Runnable onChange){changeListeners.add(onChange);}
	public final void removeChangeListener(Runnable onChange){changeListeners.remove(onChange);}
	public final List<Runnable> getChangeListeners(){return changeListeners;}
	public final void delegateChangeEvent(){for(Runnable r : changeListeners) r.run();}
}
