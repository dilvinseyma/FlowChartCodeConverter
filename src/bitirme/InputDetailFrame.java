package bitirme;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class InputDetailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Input shape;
	JButton save, exit;
	ActionListener actionListener;
	JLabel inputLabel;
	JTextField inputTextField;
	String inputDetail;

	public InputDetailFrame(Input shape) {
		this.shape = shape;
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.setTitle("Input");

		inputLabel = new JLabel("Girdiler : ");
		inputTextField = new JTextField();

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
					inputDetail = inputTextField.getText();
					shape.setStrings(inputDetail);
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

		add(inputLabel);
		add(inputTextField);
		add(save);
		add(exit);

		setVisible(true);
		setLayout(new GridLayout(2, 2));
	}

}
