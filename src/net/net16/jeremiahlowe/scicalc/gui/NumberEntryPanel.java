package net.net16.jeremiahlowe.scicalc.gui;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class NumberEntryPanel extends JPanel {
	private static final long serialVersionUID = 597548877570257650L;
	private SimpleButton[] numberButtons;
	private SimpleButton btnP, btnD;
	private String buffer = "";
	private List<Runnable> onChangeListeners = new ArrayList<Runnable>();

	public NumberEntryPanel() {
		setBackground(Color.WHITE);
		setLayout(new GridLayout(0, 3, 3, 3));
		numberButtons = new SimpleButton[10];
		for(int i = 2; i >= 0; i--){
			for(int j = 0; j < 3; j++){
				int btn = 3 * i + j + 1;
				numberButtons[btn] = new SimpleButton();
				char c = (char) (btn + '0');
				configureButton(numberButtons[btn], c + "", Color.DARK_GRAY, Color.LIGHT_GRAY, 2, c);
				add(numberButtons[btn]);
			}
		}
		numberButtons[0] = new SimpleButton();
		configureButton(numberButtons[0], "0", Color.DARK_GRAY, Color.LIGHT_GRAY, 2, '0');
		add(numberButtons[0]);
		btnP = new SimpleButton();
		configureButton(btnP, ".", Color.DARK_GRAY, Color.LIGHT_GRAY, 2, '.');
		add(btnP);
		btnD = new SimpleButton();
		configureButton(btnD, "Del", Color.DARK_GRAY, Color.LIGHT_GRAY, 2, '\b');
		add(btnD);
		KeyListener kl = new KeyAdapter() {
			@Override 
			public void keyPressed(KeyEvent ke){
				int kec = ke.getKeyCode();
				if(kec == KeyEvent.VK_PERIOD) btnP.press();
				else if(kec == KeyEvent.VK_DELETE || kec == KeyEvent.VK_BACK_SPACE) btnD.press();
				else for(int i = 0; i < 10; i++){
					char c = (char) (i + '0');
					int kc = KeyEvent.getExtendedKeyCodeForChar(c);
					if(kc == kec) numberButtons[i].press();
					else numberButtons[i].release();
				}
			}
			@Override 
			public void keyReleased(KeyEvent ke){
				int kec = ke.getKeyCode();
				if(kec == KeyEvent.VK_PERIOD) btnP.press();
				else if(kec == KeyEvent.VK_DELETE || kec == KeyEvent.VK_BACK_SPACE) btnD.press();
				else for(int i = 0; i < 10; i++){
					char c = (char) (i + '0');
					int kc = KeyEvent.getExtendedKeyCodeForChar(c);
					if(kc == kec) numberButtons[i].release();
				}
				dispatchChange();
			}
		};
		addKeyListener(kl);
		setFocusable(true);
		requestFocus();
	}
	private void configureButton(SimpleButton to, String text, Color fore, Color back, int border, char symbol){
		to.setToolTipText("Button: " + text);
		to.setText(text);
		to.setForeground(fore);
		to.setBorderWidth(border);
		to.setBackground(back);
		addEventHandlers(to, symbol);
	}
	private void addEventHandlers(SimpleButton to, char symbol){
		to.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent me){
				if(symbol == '\b'){
					if(buffer.length() >= 1) buffer = buffer.substring(0, buffer.length() - 1);
					else return;
				}
				else buffer += symbol;
				dispatchChange();
			}
		});
	}
	public SimpleButton[] getNumberButtons() {return numberButtons;}
	public void setNumberButtons(SimpleButton[] numberButtons) {this.numberButtons = numberButtons;}
	public SimpleButton getPeriodButton() {return btnP;}
	public void setPeriodButton(SimpleButton btnP) {this.btnP = btnP;}
	public SimpleButton getDeleteButton() {return btnD;}
	public void setDeleteButton(SimpleButton btnD) {this.btnD = btnD;}
	public String getBuffer() {return buffer;}
	public void setBuffer(String buffer) {this.buffer = buffer;}
	public void clearBuffer() {buffer = "";}
	public void dispatchChange(){for(Runnable onChange : onChangeListeners) onChange.run();}
	public void addChangeListener(Runnable onChange){onChangeListeners.add(onChange);}
	public void removeChangeListener(Runnable onChange){onChangeListeners.remove(onChange);}
	public List<Runnable> getChangeListeners(){return onChangeListeners;}
	public void setChangeListeners(List<Runnable> onChangeListeners){this.onChangeListeners = onChangeListeners;}
}
