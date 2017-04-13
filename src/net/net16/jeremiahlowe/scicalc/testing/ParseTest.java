package net.net16.jeremiahlowe.scicalc.testing;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.net16.jeremiahlowe.scicalc.functions.parser.CannotParseException;
import net.net16.jeremiahlowe.scicalc.functions.parser.FunctionParser;

import javax.swing.JTextArea;
import javax.swing.JButton;

public class ParseTest extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton btnParse;
	public static void main(String[] args) {
		ParseTest frame = new ParseTest();
		frame.setVisible(true);
	}
	public ParseTest() {
		initialize();
		FunctionParser parser = new FunctionParser();
		btnParse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String equation = textArea.getText().trim();
				System.out.println("Parsing: " + equation);
				try{
					parser.parseUnaryFunction(equation);
				}catch(CannotParseException cpe){
					System.out.println(cpe.toString());
				}
			}
		});
	}
	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.CENTER);
		btnParse = new JButton("Parse!!!");
		contentPane.add(btnParse, BorderLayout.SOUTH);
	}
}
