package demos.thingy;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.BoundedRangeModel;
import javax.swing.Box;
import javax.swing.DefaultBoundedRangeModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;

public class ThingyFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		ThingyFrame frame = new ThingyFrame();
		frame.setVisible(true);
	}

	/**
	 * Create the frame.
	 */
	public ThingyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		Box vbox1 = Box.createVerticalBox();
		contentPane.add(vbox1);
		contentPane.add(makeSliderContainer("A", 0, -100, 100));
		contentPane.add(makeSliderContainer("B", 0, -100, 100));
		contentPane.add(makeSliderContainer("C", 0, -100, 100));
		contentPane.add(makeSliderContainer("D", 0, 0, 10));
	}
	public Box makeSliderContainer(String name, double value, double min, double max) {
		JLabel lbl = new JLabel(name);
		DoubleJSlider slider = new DoubleJSlider(min, max, value);
		Box out = Box.createHorizontalBox();
		out.add(lbl);
		out.add(slider);
		return out;
	}
}
