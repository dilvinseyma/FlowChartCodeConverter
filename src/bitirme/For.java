package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class For extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	String first = "", second = "", third = "";
	boolean isParent, isChild, isLoopParent, isLoopChild;
	private boolean isHelperConnectorTrueParent;
	private boolean isHelperConnectorFalseParent;
	private boolean isHelperConnectorChild;

	public For(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(200, 90));
		setPreferredSize(new Dimension(200, 90));
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

	public void setStrings(String first, String second, String third) {
		this.first = first;
		this.second = second;
		this.third = third;
	}

	public String getFirst() {
		return first;
	}

	public String getSecond() {
		return second;
	}

	public String getThird() {
		return third;
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

	public void setLoopParent(boolean isLoopParent) {
		this.isLoopParent = isLoopParent;
	}

	public boolean isLoopParent() {
		return isLoopParent;
	}

	public void setLoopChild(boolean isLoopChild) {
		this.isLoopChild = isLoopChild;
	}

	public boolean isLoopChild() {
		return isLoopChild;
	}

	public void setHelperConnectorTrueParent(boolean isHelperConnectorTrueParent) {
		this.isHelperConnectorTrueParent = isHelperConnectorTrueParent;
	}

	public boolean isHelperConnectorTrueParent() {
		return isHelperConnectorTrueParent;
	}

	public void setHelperConnectorFalseParent(
			boolean isHelperConnectorFalseParent) {
		this.isHelperConnectorFalseParent = isHelperConnectorFalseParent;
	}

	public boolean isHelperConnectorFalseParent() {
		return isHelperConnectorFalseParent;
	}

	public void setHelperConnectorChild(boolean isHelperConnectorChild) {
		this.isHelperConnectorChild = isHelperConnectorChild;
		// TODO Auto-generated method stub

	}

	public boolean isHelperConnectorChild() {
		return isHelperConnectorChild;
	}

	public GeneralPath drawFor(int x, int y, int width, int heigth) {
		// x 30 y 200 w 250 h 100
		Rectangle2D rectangle2d = new Rectangle2D.Double(x, y, width, heigth);
		GeneralPath gp = new GeneralPath();
		gp.moveTo(x, y);
		gp.lineTo(x + width * 7 / 10, y);
		gp.lineTo(x + width * 7 / 10, y + heigth * 2 / 5);
		gp.lineTo(x, y + heigth * 2 / 5);
		gp.lineTo(x + width, y + heigth * 2 / 5);
		gp.append(rectangle2d, false);
		return gp;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawFor(0, 0, 200, 90);
		graphics.draw(shape);
		if (getFirst() != null) {
			g.drawString(getFirst(), 5, 20);
			// g.drawRect(0, 0, 200, 80);
		}
		if (getSecond() != null) {
			g.drawString(getSecond(), 185, 20);
			// g.drawRect(0, 0, 200, 80);
		}
		if (getThird() != null) {
			g.drawString(getThird(), 5, 60);
			// g.drawRect(0, 0, 200, 80);
		}
	}
}
