package bitirme;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class OutputDetailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Output shape;
	JButton save, exit;
	ActionListener actionListener;
	JLabel outputLabel;
	JTextField outputTextField;
	String outputDetail;

	public OutputDetailFrame(Output shape) {
		this.shape = shape;
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.setTitle("Output");

		outputLabel = new JLabel("Çýktýlar: ");
		outputTextField = new JTextField();

		save = new JButton("  Kaydet ");
		save.setBounds(30, 30, 100, 50);
		save.setActionCommand("kaydet");
		exit = new JButton("  Çýkýþ ");
		exit.setBounds(30, 30, 100, 50);
		exit.setActionCommand("çýkýþ");

		actionListener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				switch (e.getActionCommand()) {
				case "kaydet":
					outputDetail = outputTextField.getText();
					shape.setStrings(outputDetail);
					shape.repaint();
					setVisible(false);
					break;
				case "çýkýþ":
					setVisible(false);
					break;
				default:
					break;
				}
			}
		};

		exit.addActionListener(actionListener);
		save.addActionListener(actionListener);

		add(outputLabel);
		add(outputTextField);
		add(save);
		add(exit);

		setVisible(true);
		setLayout(new GridLayout(2, 2));
	}

}
