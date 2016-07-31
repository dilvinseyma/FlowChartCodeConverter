package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

public class Output extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	String outputDetail = "";
	boolean isParent, isChild;
	private boolean isLoopChild;
	private boolean isLoopParent;
	private boolean isIfParent;
	private boolean isIfChild;
	private boolean isHelperConnectorTrueParent;
	private boolean isHelperConnectorFalseParent;
	private boolean isHelperConnectorChild;

	public Output(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(203, 203));
		setPreferredSize(new Dimension(203, 113));
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

	public GeneralPath drawOutput(int x, int y, int width, int heigth) {
		GeneralPath gp = new GeneralPath();

		// x 220 y 70 w 200 h 110 3/10 w

		gp.moveTo(x, y);
		gp.lineTo(x, y + heigth);
		gp.quadTo(x + (width * 3 / 10), y + heigth - 40, x + width / 2, y
				+ heigth);
		gp.quadTo(x + (width / 2) + (width * 3 / 10), y + heigth + 40, x
				+ width, y + heigth);
		gp.lineTo(x + width, y);
		gp.lineTo(x, y);

		return gp;
	}

	public void setStrings(String outputDetail) {
		this.outputDetail = outputDetail;
	}

	public String getOutputDetail() {
		return outputDetail;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawOutput(0, 0, 200, 90);
		graphics.draw(shape);
		// g.drawRect(0, 0, 200, 80);
		g.drawString(getOutputDetail(), 15, 30);
	}

}
