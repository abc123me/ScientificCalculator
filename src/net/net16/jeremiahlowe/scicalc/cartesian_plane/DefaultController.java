package net.net16.jeremiahlowe.scicalc.cartesian_plane;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import net.net16.jeremiahlowe.shared.math.Vector;

public class DefaultController implements KeyListener, MouseWheelListener, MouseMotionListener{
	public int toogleLabelsKey = KeyEvent.VK_E;
	public int zoomIn = KeyEvent.VK_W;
	public int zoomOut = KeyEvent.VK_S;
	public int zoomInH = KeyEvent.VK_LEFT;
	public int zoomOutH = KeyEvent.VK_RIGHT;
	public int zoomInV = KeyEvent.VK_UP;
	public int zoomOutV = KeyEvent.VK_DOWN;
	public int panUp = KeyEvent.VK_I;
	public int panDown = KeyEvent.VK_K;
	public int panLeft = KeyEvent.VK_J;
	public int panRight = KeyEvent.VK_L;
	public int redraw = KeyEvent.VK_SPACE;
	public int animateToggle = KeyEvent.VK_P;
	public int escapeKey = KeyEvent.VK_ESCAPE;
	
	public Vector ticksOnScreen = new Vector(10, 10);
	public boolean lockAxisSize = false;
	public long animationSpeedMS = 150;
	public int animationSteps = 30;
	public boolean slowAnimation = true;
	
	private boolean animating = false;
	private Thread animatorThread;
	private CoordinatePlane cp;
	
	public DefaultController(CoordinatePlane cp) {this(cp, true);}
	public DefaultController(CoordinatePlane cp, boolean al) {
		this.cp = cp;
		if(al) addListeners();
	}

	public void addListeners(){
		cp.addMouseMotionListener(this);
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
			Vector vs = cp.getViewportSize();
			if(vs.x + xp > 0) vs.x += xp;
			if(vs.y + yp > 0) vs.y += yp;
			cp.setViewportSize(vs);
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
					for(int i = 0; i < animationSteps; i++){
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
		if(x > 0 && y > 0) ticksOnScreen = new Vector(x, y);
		else throw new RuntimeException("Cannot have negative ticks on screen");
	}
	public Vector getTickAmount(){
		return ticksOnScreen.copy();
	}
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		int rot = arg0.getWheelRotation();
		zoom(rot, rot);
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
		if(kc == panDown) cp.pan(0, -1);
		if(kc == panUp) cp.pan(0, 1);
		if(kc == panLeft) cp.pan(-1, 0);
		if(kc == panRight) cp.pan(1, 0);
		if(kc == redraw) cp.recalculate();
		if(kc == escapeKey) System.exit(0);
	}
	@Override 
	public void keyReleased(KeyEvent arg0) {}
	@Override 
	public void keyTyped(KeyEvent arg0) {}
	@Override
	public void mouseDragged(MouseEvent e) {
		Vector wpos = new Vector();
		wpos.x = ((e.getX()) / cp.getWidth()) * cp.getViewportSize().x;
		wpos.y = (((cp.getHeight() - e.getY())) / cp.getHeight()) * cp.getViewportSize().y;
		wpos.x -= cp.getViewportSize().x / 2;
		wpos.y -= cp.getViewportSize().y / 2;
		//wpos.x += cp.getOriginPanningOffset().x;
		cp.setOriginPanningOffset(wpos);
	}
	@Override
	public void mouseMoved(MouseEvent arg0) {}
}