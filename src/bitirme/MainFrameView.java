package bitirme;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrameView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// MainFrameViewController mainFrameViewController;
	JButton butonInput, butonout;
	JPanel chartPanel = new ChartPanel();

	ActionListener listener;
	BorderLayout layout;
	MouseListener mListener;
	MouseMotionListener motionListener;
	int x, y;
	int sx, sy;
	int dragx, dragy;

	public MainFrameView() {

		// pencere boyutu
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		// int frameWidth = 1000;
		// int frameHeight = 600;
		// Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		// setBounds(0, 0, frameWidth, frameHeight);

		// pencere kapatýldýgýnda programý sonlandýr.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// cerceveyý gorunur yap.
		setVisible(true);

		// adým 2: düzen belirleme
		setLayout(new BorderLayout());
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				// TODO Auto-generated method stub
				System.out.println("aþaðý alýndý");

			}

			@Override
			public void windowDeiconified(WindowEvent e) {
				// TODO Auto-generated method stub
				System.out.println("tekrar yukarý açýldý");
				// pack();
				repaint();

			}

			@Override
			public void windowDeactivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosed(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowActivated(WindowEvent e) {
				// TODO Auto-generated method stub

			}
		});

		// // connect MainFrameViewController
		// mainFrameViewController = new MainFrameViewController(this);
		// this.addMouseListener(mainFrameViewController);
		add(chartPanel);

	}

	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		update(g);
	}
}
