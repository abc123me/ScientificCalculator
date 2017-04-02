package net.net16.jeremiahlowe.scicalc.graphing.cartesian;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.net16.jeremiahlowe.bettercollections.vector.Vector2;

//TODO: Implement panning
public class DefaultController implements KeyListener, MouseWheelListener{
	public int toogleLabelsKey = KeyEvent.VK_L;
	public int nextQuadrantKey = KeyEvent.VK_Q;
	public int prevQuadrantKey = KeyEvent.VK_E;
	public int zoomIn = KeyEvent.VK_W;
	public int zoomOut = KeyEvent.VK_S;
	public int zoomInH = KeyEvent.VK_LEFT;
	public int zoomOutH = KeyEvent.VK_RIGHT;
	public int zoomInV = KeyEvent.VK_UP;
	public int zoomOutV = KeyEvent.VK_DOWN;
	public int animateToggle = KeyEvent.VK_A;
	
	public Vector2 ticksOnScreen = new Vector2(10, 10);
	public boolean lockAxisSize = false;
	public long animationSpeedMS = 50;
	public boolean slowAnimation = true;
	
	private boolean animating = false;
	private Thread animatorThread;
	private CoordinatePlane cp;
	
	public DefaultController(CoordinatePlane cp) {this(cp, true);}
	public DefaultController(CoordinatePlane cp, boolean al) {
		this.cp = cp;
		if(al) addListeners();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int rot = arg0.getWheelRotation();
		zoom(rot, rot);
	}
	public void addListeners(){
		cp.addMouseWheelListener(this);
		cp.addKeyListener(this);
		if(!cp.isFocusable()) cp.setFocusable(true);
		cp.requestFocus();
	}
	public void removeListeners(){
		cp.removeMouseWheelListener(this);
		cp.removeKeyListener(this);
	}
	public void zoom(int xp, int yp, boolean forced){
		if(forced || !animating){
			//Resize viewport
			Vector2Precise vs = cp.getViewportSize();
			if(vs.x + xp > 0) vs.x += xp;
			if(vs.y + yp > 0) vs.y += yp;
			cp.setViewportSize(vs);
			//Resize ticks
			if(!lockAxisSize) return;
			Vector2Precise ntc = new Vector2Precise(0, 0);
			ntc.x = vs.x / ticksOnScreen.x;
			ntc.y = vs.y / ticksOnScreen.y;
			cp.setTickCounts(ntc);
		}
	}
	public void zoom(int xp, int yp){zoom(xp, yp, false);}
	public void animateToggle(){
		if(animatorThread != null && animatorThread.isAlive()) animatorThread.interrupt();
		animating = !animating;
		if(!animating) return;
		animatorThread = new Thread(){
			@Override
			public void run(){
				Thread ct = Thread.currentThread();
				ct.setName("AnimationThread-" + ct.getId());
				boolean mult = false;
				while(true){
					int j = mult ? -1 : 1;
					for(int i = 0; i < 100; i++){
						if(Thread.interrupted()) return;
						if(slowAnimation){
							try{
								Thread.sleep(animationSpeedMS);
							}catch(Exception e){return;}
						}
						zoom(j, j, true);
					}
					mult = !mult;
				}
			}
		};
		animatorThread.start();
	}

	public void setTickAmount(int x, int y){
		if(x > 0 && y > 0) ticksOnScreen = new Vector2(x, y);
		else throw new RuntimeException("Cannot have negative ticks on screen");
	}
	public Vector2 getTickAmount(){
		return ticksOnScreen.clone();
	}
	@Override 
	public void keyPressed(KeyEvent k) {
		int kc = k.getKeyCode();
		if(kc == toogleLabelsKey) cp.setAxesLabels(!cp.getAxesLabelsEnabled());
		if(kc == zoomIn) zoom(1, 1);
		if(kc == zoomOut) zoom(-1, -1);
		if(kc == zoomInH) zoom(1, 0);
		if(kc == zoomOutH) zoom(-1, 0);
		if(kc == zoomInV) zoom(0, 1);
		if(kc == zoomOutV) zoom(0, -1);
		if(kc == animateToggle) animateToggle();
		if(kc == nextQuadrantKey) cp.setViewQuadrant(cp.getViewQuadrant().next());
		if(kc == prevQuadrantKey) cp.setViewQuadrant(cp.getViewQuadrant().prev());
	}
	@Override public void keyReleased(KeyEvent arg0) {}
	@Override public void keyTyped(KeyEvent arg0) {}
}