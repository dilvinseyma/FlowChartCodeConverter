package bitirme;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class End extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	boolean isParent, isChild;
	private boolean isIfChild;
	private boolean isHelperConnectorTrueParent;
	private boolean isHelperConnectorFalseParent;
	private boolean isHelperConnectorChild;
	private boolean isLoopChild;
	private boolean isLoopParent;
	private boolean isIfParent;

	public End(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(203, 203));
		setPreferredSize(new Dimension(203, 113));
		setBackground(Color.WHITE);
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

	public void setLoopChild(boolean isLoopChild) {
		this.isLoopChild = isLoopChild;
	}

	public boolean isLoopChild() {
		return isLoopChild;
	}

	public void setLoopParent(boolean isLoopParent) {
		this.isLoopParent = isLoopParent;
	}

	public boolean isLoopParent() {
		return isLoopParent;
	}

	public void setIfParent(boolean isIfParent) {
		this.isIfParent = isIfParent;
	}

	public boolean isIfParent() {
		return isIfParent;
	}

	public void setIfChild(boolean isIfChild) {
		this.isIfChild = isIfChild;
	}

	public boolean isIfChild() {
		return isIfChild;
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

	public Shape drawEnd(int x, int y, int width, int heigth) {

		Ellipse2D ellipse2d = new Ellipse2D.Double(x, y, width, heigth);

		return ellipse2d;

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawEnd(0, 0, 200, 90);
		graphics.draw(shape);
		// g.drawRect(0, 0, 200, 80);
		g.drawString("BÝTÝR", 80, 45);
	}

}
