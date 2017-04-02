package net.net16.jeremiahlowe.scicalc.functions.tracer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class FunctionTracerPanner implements KeyListener{
	public int keyIncrement = KeyEvent.VK_A;
	public int keyDecrement = KeyEvent.VK_D;
	private UnaryFunctionTracer uft;
	
	public FunctionTracerPanner(UnaryFunctionTracer uft){
		this.uft = uft;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int kc = e.getKeyCode();
		if(kc == keyIncrement) uft.pan(1);
		if(kc == keyDecrement) uft.pan(-1);
	}
	@Override
	public void keyReleased(KeyEvent e) {}
	@Override
	public void keyTyped(KeyEvent e) {}
}
