package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class HelperConnector extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	boolean isParent, isChild;
	private boolean isTrueChild, isFalseChild;

	public HelperConnector(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(21, 21));
		setPreferredSize(new Dimension(21, 21));
		setOpaque(false);
	}

	@Override
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setChild(boolean isChild) {
		this.isChild = isChild;
	}

	public boolean isChild() {
		return isChild;
	}

	public void setTrueChild(boolean isTrueChild) {
		this.isTrueChild = isTrueChild;
	}

	public boolean isTrueChild() {
		return isTrueChild;
	}

	public boolean isFalseChild() {
		return isFalseChild;
	}

	public void setFalseChild(boolean isFalseChild) {
		this.isFalseChild = isFalseChild;
	}

	public Shape drawStart(int x, int y, int width, int heigth) {

		Ellipse2D ellipse2d = new Ellipse2D.Double(x, y, width, heigth);

		return ellipse2d;

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawStart(0, 0, 20, 20);
		graphics.draw(shape);

	}
}
