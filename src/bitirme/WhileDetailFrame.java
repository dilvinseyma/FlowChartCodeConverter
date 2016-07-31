package bitirme;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class WhileDetailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	While shape;
	JLabel cond1, cond2, cond3;
	String cond1str, cond2str, cond3str;
	JTextField cond1texField, cond2texField, cond3texField;
	JButton save, exit;
	ActionListener actionListener;

	public WhileDetailFrame(While shape) {
		this.shape = shape;
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.setTitle("While");

		cond1 = new JLabel("Koþul : ");
		cond1texField = new JTextField();

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
					cond1str = cond1texField.getText();

					shape.setStrings(cond1str);
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

		add(cond1);
		add(cond1texField);

		add(save);
		add(exit);

		setVisible(true);
		setLayout(new GridLayout(4, 2));

	}

}
