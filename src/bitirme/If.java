package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

public class If extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public int x, y;
	String c1 = "", c2 = "", c3 = "";
	boolean isParent, isChild, isTrue, isFalse, isIfParent, isIfChild;
	private boolean isLoopChild;
	private boolean isLoopParent;
	private boolean isHelperConnectorTrueParent;
	private boolean isHelperConnectorFalseParent;
	private boolean isHelperConnectorChild;

	public If(int x, int y) {
		// TODO Auto-generated constructor stub
		this.x = x;
		this.y = y;
		setMaximumSize(new Dimension(253, 253));
		setPreferredSize(new Dimension(253, 113));
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

	public void setTrue(boolean isTrue) {
		this.isTrue = isTrue;
	}

	public boolean isTrue() {
		return isTrue;
	}

	public void setFalse(boolean isFalse) {
		this.isFalse = isFalse;
	}

	public boolean isFalse() {
		return isFalse;
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

	public GeneralPath drawIf(int x, int y, int width, int heigth) {

		GeneralPath gp1 = new GeneralPath();
		gp1.moveTo(x + 40, y);
		gp1.lineTo(x, y + heigth / 2);
		gp1.lineTo(x + 40, y + heigth);

		gp1.lineTo(x + width, y + heigth);
		gp1.lineTo(x + width + 40, y + heigth / 2);
		gp1.lineTo(x + width, y);
		gp1.lineTo(x + 40, y);

		return gp1;
	}

	public void setStrings(String c1) {
		this.c1 = c1;

	}

	public String getCondition() {
		return c1;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		Shape shape = drawIf(0, 0, 200, 80);
		graphics.draw(shape);
		// g.drawRect(0, 0, 200, 80);
		g.drawString(getCondition(), 35, 20);
	}

}
