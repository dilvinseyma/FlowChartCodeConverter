package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class Function extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	String funcName = "";
	boolean isParent, isChild;
	private boolean isLoopChild;
	private boolean isLoopParent;
	private boolean isHelperConnectorChild;
	private boolean isHelperConnectorFalseParent;
	private boolean isHelperConnectorTrueParent;
	private boolean isIfChild;
	private boolean isIfParent;

	public Function(int x, int y) {
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

	public GeneralPath drawFunction(int x, int y, int w, int h) {
		// x 30 y 500 w 200 h 100
		GeneralPath gp = new GeneralPath();
		gp.moveTo(x + 10, y);
		gp.lineTo(x + 10, y + h);
		gp.lineTo(x + w - 10, y + h);
		gp.lineTo(x + w - 10, y);
		Rectangle2D rectangle2d = new Rectangle2D.Double(x, y, w, h);
		gp.append(rectangle2d, false);
		return gp;
	}

	public void setStrings(String funcName) {
		this.funcName = funcName;

	}

	public String getFuncName() {
		return funcName;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawFunction(0, 0, 200, 90);
		graphics.draw(shape);
		// g.drawRect(0, 0, 200, 80);
		if (getFuncName() != null) {
			g.drawString(getFuncName(), 15, 30);
		}
	}

}
