package net.net16.jeremiahlowe.scicalc.functions.tracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FunctionTracerPanner implements KeyListener{
	public int keyIncrement = KeyEvent.VK_D;
	public int keyDecrement = KeyEvent.VK_A;
	private float panAmount = 1;
	private UnaryFunctionTracer uft;
	
	public FunctionTracerPanner(UnaryFunctionTracer uft){
		this.uft = uft;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if(kc == keyIncrement) uft.pan(panAmount);
		if(kc == keyDecrement) uft.pan(-panAmount);
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
	
	public void setPanAmount(float amount){panAmount = amount;}
	public double getPanAmount(){return panAmount;}
}
