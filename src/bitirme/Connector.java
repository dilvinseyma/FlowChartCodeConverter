package bitirme;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.GeneralPath;

import javax.swing.JPanel;

public class Connector extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -874973016194555664L;
	public int x1, y1, x2, y2;
	int childIdx = 0, loopChildIdx = 0, loopParentIdx = 0, parentIdx = 0,
			ifParentIdx = 0, ifChildIdx = 0;
	boolean isLoopConnector = false;
	String loopConnectorTypeString = "";
	private int helperConnectorTrueParentIdx = 0;
	private int helperConnectorTrueChildIdx = 0;
	private int helperConnectorFalseParentIdx;
	private int helperConnectorFalseChildIdx;

	public Connector(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		childIdx = 0;
		parentIdx = 0;
		setOpaque(false);
		setMaximumSize(new Dimension(x2 - x1 + 102, y2 - y1 - 89));
		setPreferredSize(new Dimension(x2 - x1 + 102, y2 - y1 - 89));
	}

	public int getX1() {
		return x1;
	}

	public void setX1(int x1) {
		this.x1 = x1;
	}

	public int getY1() {
		return y1;
	}

	public void setY1(int y1) {
		this.y1 = y1;
	}

	public int getX2() {
		return x2;
	}

	public void setX2(int x2) {
		this.x2 = x2;
	}

	public int getY2() {
		return y2;
	}

	public void setY2(int y2) {
		this.y2 = y2;
	}

	public void setChildIdx(int childIdx) {
		this.childIdx = childIdx;
	}

	public int getChildIdx() {
		return childIdx;
	}

	public void setParentIdx(int parentIdx) {
		this.parentIdx = parentIdx;
	}

	public int getParentIdx() {
		return parentIdx;
	}

	public void setLoopChildIdx(int loopChildIdx) {
		this.loopChildIdx = loopChildIdx;
	}

	public int getLoopChildIdx() {
		return loopChildIdx;
	}

	public void setLoopParentIdx(int loopParentIdx) {
		this.loopParentIdx = loopParentIdx;
	}

	public int getLoopParentIdx() {
		return loopParentIdx;
	}

	public void setLoopConnector(boolean isLoopConnector) {
		this.isLoopConnector = isLoopConnector;
	}

	public boolean isLoopConnector() {
		return isLoopConnector;
	}

	public void setLoopConnectorTypeString(String loopConnectorTypeString) {
		this.loopConnectorTypeString = loopConnectorTypeString;
	}

	public String getLoopConnectorTypeString() {
		return loopConnectorTypeString;
	}

	public void setIfParentIdx(int ifParentIdx) {
		this.ifParentIdx = ifParentIdx;
	}

	public int getIfChildIdx() {
		return ifChildIdx;
	}

	public void setIfChildIdx(int ifChildIdx) {
		this.ifChildIdx = ifChildIdx;
	}

	public int getIfParentIdx() {
		return ifParentIdx;
	}

	public void setHelperConnectorParentIdx(int helperConnectorTrueParentIdx) {
		// TODO Auto-generated method stub
		this.helperConnectorTrueParentIdx = helperConnectorTrueParentIdx;

	}

	public void setHelperConnectorChildIdx(int helperConnectorTrueChildIdx) {
		// TODO Auto-generated method stub
		this.helperConnectorTrueChildIdx = helperConnectorTrueChildIdx;

	}

	public int getHelperConnectorTrueParentIdx() {
		return helperConnectorTrueParentIdx;
	}

	public void setHelperConnectorTrueParentIdx(int helperConnectorTrueParentIdx) {
		this.helperConnectorTrueParentIdx = helperConnectorTrueParentIdx;
	}

	public int getHelperConnectorTrueChildIdx() {
		return helperConnectorTrueChildIdx;
	}

	public void setHelperConnectorTrueChildIdx(int helperConnectorTrueChildIdx) {
		this.helperConnectorTrueChildIdx = helperConnectorTrueChildIdx;
	}

	public int getHelperConnectorFalseParentIdx() {
		return helperConnectorFalseParentIdx;
	}

	public void setHelperConnectorFalseParentIdx(
			int helperConnectorFalseParentIdx) {
		this.helperConnectorFalseParentIdx = helperConnectorFalseParentIdx;
	}

	public int getHelperConnectorFalseChildIdx() {
		return helperConnectorFalseChildIdx;
	}

	public void setHelperConnectorFalseChildIdx(int helperConnectorFalseChildIdx) {
		this.helperConnectorFalseChildIdx = helperConnectorFalseChildIdx;
	}

	public GeneralPath drawFor(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x1 + (x2 / 2), y1);
		gp.lineTo(x1 + (x2 / 2), y1 + y2);
		gp.lineTo(x1 + x2, y1 + y2);
		this.setMaximumSize(new Dimension(x2 - 200 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 - 200 + 1, y2 + 1));

		// gp.moveTo(x1 + 200, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y2 + 45);
		// gp.lineTo(x2, y2 + 45);

		return gp;
	}

	public GeneralPath drawForReturn(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x2 + 20, y1);
		gp.lineTo(x2 + 20, y1 + y2);
		gp.lineTo(x2, y1 + y2);
		this.setMaximumSize(new Dimension(x2 + 21, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 21, y2 + 1));

		// gp.moveTo(x1 + 200, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y2 + 45);
		// gp.lineTo(x2, y2 + 45);

		return gp;
	}

	public GeneralPath drawWhileReturn(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x2 + 20, y1);
		gp.lineTo(x2 + 20, y1 + y2);
		gp.lineTo(x2, y1 + y2);
		this.setMaximumSize(new Dimension(x2 + 51, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 51, y2 + 1));

		// gp.moveTo(x1 + 200, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y2 + 45);
		// gp.lineTo(x2, y2 + 45);

		return gp;
	}

	public GeneralPath drawWhile(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x1 + 50, 30);
		gp.lineTo(x2 + 100, 30);
		gp.lineTo(x2 + 100, y2 - 45);
		// gp.lineTo(x1 + (x2 / 5), y2);
		// gp.lineTo(x1 + x2, y2);

		this.setMaximumSize(new Dimension(x2 + 101, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 101, y2 + 1));

		// gp.moveTo(x1 + 200, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y2 + 45);
		// gp.lineTo(x2, y2 + 45);

		return gp;
	}

	public GeneralPath drawWhileReturn1(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 30);
		gp.lineTo(x1 + 50, 0);
		gp.lineTo(x1 + x2, 0);
		gp.lineTo(x1 + x2, y2);
		gp.lineTo(x1 + x2 - 20, y2);

		this.setMaximumSize(new Dimension(x2 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 1, y2 + 1));

		// gp.moveTo(x1 + 200, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y1 + 50);
		// gp.lineTo((x1 + 200 - x2) / 2, y2 + 45);
		// gp.lineTo(x2, y2 + 45);

		return gp;
	}

	public GeneralPath drawTrue(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(x1 + x2, 0);
		gp.lineTo(0, 0);
		gp.lineTo(x1, y2);

		this.setMaximumSize(new Dimension(x2 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 1, y2 + 1));
		return gp;
	}

	public GeneralPath drawFalse(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x1 + x2, 0);
		gp.lineTo(x1 + x2, y2);

		this.setMaximumSize(new Dimension(x2 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 1, y2 + 1));
		return gp;
	}

	public GeneralPath drawTrueHelperConnector(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(0, 0);
		gp.lineTo(x1, y2);
		gp.lineTo(x1 + x2, y2);

		this.setMaximumSize(new Dimension(x2 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 1, y2 + 1));
		return gp;
	}

	public GeneralPath drawFalseHelperConnector(int x1, int y1, int x2, int y2) {

		GeneralPath gp = new GeneralPath();
		gp.moveTo(x2, 0);
		gp.lineTo(x2, y2);
		gp.lineTo(0, y2);

		this.setMaximumSize(new Dimension(x2 + 1, y2 + 1));
		this.setPreferredSize(new Dimension(x2 + 1, y2 + 1));
		return gp;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics = (Graphics2D) g;
		if (getLoopConnectorTypeString().equals("for")) {
			Shape shape = drawFor(0, 0, x2 - x1 - 200, y2 + 45 - y1 - 60);
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("forReturn")) {
			Shape shape = drawForReturn(0, 0, x2 - x1, y2 + 25 - y1);
			// this.setBackground(new Color(0, 0, 0, 0));---transparanlýk için
			// denendi
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("while")) {
			Shape shape = drawWhile(0, 0, x2 - (x1 + 220), y2 + 45 - (y1 + 60));
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("whileReturn")) {
			Shape shape = drawWhileReturn(0, 0, x2 - x1 + 40, y2 + 25 - y1);
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("whileReturn1")) {
			Shape shape = drawWhileReturn1(0, 0, x2 - x1, y2 + 45 - y1);
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("true")) {
			Shape shape = drawTrue(0, 0, x1 - (x2 + 99), y2 - y1 - 40);
			g.drawString("T", x1 - (x2 + 99) - 8, 20);
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("false")) {
			Shape shape = drawFalse(0, 0, (x2 + 101) - (x1 + 240), y2 - y1 - 40);
			g.drawString("F", 8, 20);
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("trueHelperConnector")) {
			Shape shape = drawTrueHelperConnector(0, 0, x2 - (x1 + 101), y2
					- (y1 + 90));
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("falseHelperConnector")) {
			Shape shape = drawFalseHelperConnector(0, 0,
					(x1 + 101) - (x2 + 20), y2 - (y1 + 90));
			graphics.draw(shape);
		} else if (getLoopConnectorTypeString().equals("helperConnector")) {

			g.drawLine(0, 0, x2 - x1 + 90, y2 - (y1 + 20));

		} else {

			g.drawLine(0, 0, x2 - x1, y2 - (y1 + 90));
		}

		// g.drawRect(0, 0, 200, 80);
	}

}
