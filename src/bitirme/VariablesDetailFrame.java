package bitirme;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class VariablesDetailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JPanel shape;
	JButton save, exit;
	ActionListener actionListener;
	JLabel nameLabel;
	JTextField variablesTextField;
	String variables;

	public VariablesDetailFrame(Variables shape) {
		this.shape = shape;
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.setTitle("Variables");

		nameLabel = new JLabel("Deðiþkenler");
		variablesTextField = new JTextField();

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
					variables = variablesTextField.getText();
					shape.setStrings(variables);
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

		add(nameLabel);
		add(variablesTextField);
		add(save);
		add(exit);

		setVisible(true);
		setLayout(new GridLayout(2, 2));

	}

}
