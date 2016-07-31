package bitirme;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ForDetailFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String type;
	JLabel firstLabel, secondLabel, thirdLabel;
	JButton save, exit;
	JTextField firstArea, secondArea, thirdArea;
	ActionListener actionListener;
	String forFirstStr, forSecondStr, forThirdStr;
	JPanel shape;

	public ForDetailFrame(String type, For shape) {
		this.shape = shape;
		this.setLocationRelativeTo(null);
		this.setSize(500, 300);
		this.type = type;
		this.setTitle(type);

		firstLabel = new JLabel("	Ýlk deðer :  ");
		firstArea = new JTextField();
		secondLabel = new JLabel("	Artýþ Miktarý  :   ");
		secondArea = new JTextField();
		thirdLabel = new JLabel("	Son deðer : ");
		thirdArea = new JTextField();

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
					forFirstStr = firstArea.getText();
					forSecondStr = secondArea.getText();
					forThirdStr = thirdArea.getText();
					shape.setStrings(forFirstStr, forSecondStr, forThirdStr);
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
		if (type.equalsIgnoreCase("for")) {

			add(firstLabel);
			add(firstArea);
			add(secondLabel);
			add(secondArea);
			add(thirdLabel);
			add(thirdArea);
			add(save);
			add(exit);
		}

		// setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(new GridLayout(4, 2));
	}

}
