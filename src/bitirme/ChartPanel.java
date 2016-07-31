package bitirme;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class ChartPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	JButton butonInput, butonOutput, butonStart, butonEnd, butonFor,
			butonWhile, butonIf, butonFunction, butonConnector, butonVariables,
			butonHelperConnector, butonShow, butonGenerate, butonPng;

	ActionListener listener;
	BorderLayout layout;
	MouseListener mListener;
	MouseMotionListener motionListener;

	Connector shapeConnector = new Connector(0, 0, 0, 0);

	private ArrayList<String> connectors;
	private ArrayList<JPanel> shapeLists;
	private ArrayList<Connector> shapeConnectors, shapeLoopConnectors;

	JFrame detailFrame, variablesFrame;
	boolean isConnectorActive = false, isWhileParent = false,
			isWhileOwnParent = false, isWhileOwnChild, isWhileChild = false,
			isHelperConnectorParent = false, isIfChild = false, isInIf = false;
	// boolean isChild = false, isParent = false;

	boolean isInInput = false;
	int x, y, x1, y1, x2, y2;
	int sx, sy;
	int dragx, dragy;
	int variablesFrameCount = 0, count = 0, connectorCount = 0,
			loopConnectorCount = 0, loopWhileConnectorCount = 0,
			loopForCount = 0, trueCount = 0, falseCount = 0, ifCount = 0,
			trueParentCount = 0, falseParentCount = 0;
	int index, parent, child, loopParent, loopChild, loopReturn, loopForReturn,
			ifParent, ifChild, helperConnectorTrueParent,
			helperConnectorTrueChild, helperConnectorFalseParent,
			helperConnectorFalseChild;

	String connectionName = "";

	Point point1, connectorStartPoint = new Point(),
			connectorEndPoint = new Point(),
			connectorLoopStartPoint = new Point(),
			connectorTrueStartPoint = new Point(),
			connectorFalseStartPoint = new Point(),
			helperConnectorTrueStartPoint = new Point(),
			helperConnectorFalseStartPoint = new Point();
	Point point2;
	Line2D line2d;

	public ChartPanel() {
		setMaximumSize(new Dimension(903, 903));
		setPreferredSize(new Dimension(903, 903));
		// setBackground(Color.white);
		connectors = new ArrayList<>();
		shapeLists = new ArrayList<>();
		shapeConnectors = new ArrayList<>();
		shapeLoopConnectors = new ArrayList<>();

		invalidate();

		butonConnector = new JButton("Birleþtir");
		butonConnector.setActionCommand("connector");

		butonHelperConnector = new JButton("Yardýmcý Baðlantý");
		butonHelperConnector.setActionCommand("helperconnector");

		butonInput = new JButton("Input");
		butonInput.setActionCommand("input");

		butonOutput = new JButton("Output");
		butonOutput.setActionCommand("output");

		butonStart = new JButton("Start");
		butonStart.setActionCommand("start");

		butonEnd = new JButton("End");
		butonEnd.setActionCommand("end");

		butonFor = new JButton("For");
		butonFor.setActionCommand("for");

		butonFunction = new JButton("Function");
		butonFunction.setActionCommand("function");

		butonIf = new JButton("If");
		butonIf.setActionCommand("if");

		butonWhile = new JButton("While");
		butonWhile.setActionCommand("while");

		butonVariables = new JButton("Variables");
		butonVariables.setActionCommand("variables");

		butonShow = new JButton("Deðiþken Tanýmla");
		butonShow.setActionCommand("show");

		butonGenerate = new JButton("Generate the code ");
		butonGenerate.setActionCommand("generatecode");

		butonPng = new JButton("Png olarak çýkart");
		butonPng.setActionCommand("png");

		// butonInput.setBounds(10, 10, 100, 50);

		listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switch (e.getActionCommand()) {
				case "png":

					BufferedImage bi = new BufferedImage(getWidth(),
							getHeight(), BufferedImage.TYPE_INT_ARGB);
					Graphics g = bi.createGraphics();
					paint(g); // this == JComponent
					g.dispose();
					try {
						ImageIO.write(bi, "png", new File("flowchart.png"));
					} catch (Exception e1) {
					}
					break;
				case "generatecode":
					GenerateCode cGenerateCode = new GenerateCode(shapeLists,
							shapeConnectors, shapeLoopConnectors);

					break;
				case "show":

					variablesFrame = new VariablesFrame();
					variablesFrame.setLocation(1305, 65);
					// variablesFrame = null;
					break;
				case "connector":

					// shapeConnector.setBounds(100, 100, 203, 123);
					// add(shapeConnector, BorderLayout.CENTER);
					// repaint();
					isConnectorActive = true;
					connectorCount = 0;
					// loopConnectorCount = 0;

					mListener = new MouseAdapter() {

					};
					addMouseListener(mListener);
					motionListener = new MouseMotionAdapter() {

					};

					addMouseMotionListener(motionListener);
					System.out.println("baglantý týklandý");

					break;

				case "output":
					Output shapeOutput = new Output(100, 100);
					shapeOutput.setBounds(100, 100, 203, 113);
					add(shapeOutput, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeOutput.getX();
							sy = shapeOutput.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeOutput);
								if (ifCount == 1) {
									isInIf = true;
									if (trueCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorTrueStartPoint.x,
												connectorTrueStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifChild = index;
										shapeOutput.setIfChild(true);
										shapeConnector
												.setLoopConnectorTypeString("true");

										shapeConnector
												.setBounds(
														connectorEndPoint.x + 99,
														connectorTrueStartPoint.y + 40,
														connectorTrueStartPoint.x
																- connectorEndPoint.x
																- 98,
														connectorEndPoint.y
																- connectorTrueStartPoint.y
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										isConnectorActive = false;
										ifCount = 0;
										shapeLoopConnectors.add(shapeConnector);
										trueCount = 0;

									}
									if (falseCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorFalseStartPoint.x,
												connectorFalseStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifParent = index;
										shapeOutput.setIfParent(true);
										shapeConnector
												.setLoopConnectorTypeString("false");

										shapeConnector
												.setBounds(
														connectorFalseStartPoint.x + 240,
														connectorFalseStartPoint.y + 40,
														(connectorEndPoint.x + 101)
																- (connectorFalseStartPoint.x + 240)
																+ 1,
														connectorEndPoint.y
																- (connectorFalseStartPoint.y + 40)
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										ifCount = 0;
										falseCount = 0;
										shapeLoopConnectors.add(shapeConnector);
									}

								} else if (SwingUtilities.isRightMouseButton(e)
										&& isInIf) {
									System.out
											.println("ifin yapragý sað týklandý");
									shapeOutput
											.setHelperConnectorTrueParent(true);
									trueParentCount++;
									helperConnectorTrueParent = index;
									helperConnectorTrueStartPoint.x = e
											.getComponent().getX();
									helperConnectorTrueStartPoint.y = e
											.getComponent().getY();

									falseParentCount++;
									helperConnectorFalseParent = index;
									helperConnectorFalseStartPoint.x = e
											.getComponent().getX();
									helperConnectorFalseStartPoint.y = e
											.getComponent().getY();
								} else if (loopWhileConnectorCount == 1) {
									shapeOutput.setLoopChild(true);
									isWhileChild = true;
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("while");
									// bound u degistir.
									System.out.println("start point x y "
											+ connectorStartPoint.x + " "
											+ connectorStartPoint.y);
									shapeConnector
											.setBounds(
													connectorStartPoint.x + 220,
													connectorStartPoint.y + 60,
													connectorEndPoint.x
															- (connectorStartPoint.x + 220)
															+ 101,
													connectorEndPoint.y
															+ 45
															- (connectorStartPoint.y + 60)
															+ 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopWhileConnectorCount++;// 2 oldugu
																// durumda sag
																// týk gelirse
																// birleþtir
																// tekrar
									shapeLoopConnectors.add(shapeConnector);

								} else if (loopConnectorCount == 1) {
									shapeOutput.setLoopChild(true);
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopConnectorCount++;
									shapeLoopConnectors.add(shapeConnector);

								} else if (SwingUtilities.isRightMouseButton(e)) {
									if (loopWhileConnectorCount == 2) {
										shapeOutput.setLoopParent(true);
										isWhileParent = true;

										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan,
																				// whiledan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("whileReturn1");
										// bound degisicek
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 220,
														connectorLoopStartPoint.y + 20 - 30,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 1,
														connectorEndPoint.y
																+ 45
																- (connectorLoopStartPoint.y)
																+ 1);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopWhileConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									} else if (loopConnectorCount == 2) {
										shapeOutput.setLoopParent(true);
										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("forReturn");
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 200,
														connectorLoopStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 21,
														connectorEndPoint.y
																- (connectorLoopStartPoint.y)
																+ 26);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									}
								} else {
									connectorCount++;
								}
								if (connectorCount == 1) {
									parent = index;
									shapeOutput.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									shapeOutput.setChild(true);
									if (isHelperConnectorParent == true) {
										shapeOutput
												.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										connectionName += Integer
												.toString(child);
										System.out.println("connection name : "
												+ connectionName);

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new OutputDetailFrame(shapeOutput);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};
					shapeOutput.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeOutput.setX(sx);
							shapeOutput.setY(sy);
							shapeOutput.setBounds(sx, sy, 203, 113);
							shapeOutput.repaint();
							if (shapeOutput.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeOutput)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeOutput.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeOutput));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeOutput)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeOutput.isHelperConnectorChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeOutput)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 10,
												conn.getY1() + 20,
												sx - (conn.getX1() + 10) + 101,
												sy - (conn.getY1() + 19));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// deðiþkenler returnu tamamlayan bileþen ise
							// tamamlama çizgisi
							if (shapeOutput.isLoopParent()) {
								if (isWhileParent == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeOutput)) {

											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 220,
													conn.getY1() + 20 - 30, sx
															- (conn.getX1())
															+ 1, sy + 45
															- (conn.getY1())
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeOutput)) {
											System.out
													.println("drag if isloopparent a girdi");
											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 20, sx
															- (conn.getX1())
															+ 21,
													sy - (conn.getY1()) + 26);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;
										}

									}
								}

							}
							if (shapeOutput.isLoopChild()) {
								if (isWhileChild == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeOutput)) {

											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 60,
													sx - (conn.getX1() + 220)
															+ 101,
													sy
															+ 45
															- (conn.getY1() + 60)
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeOutput)) {
											System.out
													.println("drag if isloopchild a girdi");
											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 61, sx
															- (conn.getX1())
															- 200 + 1, sy
															- (conn.getY1())
															- 15 + 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								}

							}

							if (shapeOutput.isIfChild()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfChildIdx() == shapeLists
											.indexOf(shapeOutput)) {

										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX2() + 99,
												conn.getY1() + 40, conn.getX1()
														- conn.getX2() - 98,
												conn.getY2() - (conn.getY1())
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							if (shapeOutput.isIfParent()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfParentIdx() == shapeLists
											.indexOf(shapeOutput)) {

										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(
												conn.getX1() + 240,
												conn.getY1() + 40,
												(conn.getX2() + 101)
														- (conn.getX1() + 240)
														+ 1, conn.getY2()
														- (conn.getY1() + 40)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							repaint();
						}

					};

					shapeOutput.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeOutput);
					break;
				case "input":
					Input shapeInput = new Input(100, 100);
					shapeInput.setBounds(100, 100, 203, 91);
					add(shapeInput, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

							x = e.getX();
							y = e.getY();
							sx = shapeInput.getX();
							sy = shapeInput.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeInput);
								if (ifCount == 1) {
									isInIf = true;
									if (trueCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorTrueStartPoint.x,
												connectorTrueStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifChild = index;
										shapeInput.setIfChild(true);
										shapeConnector
												.setLoopConnectorTypeString("true");

										shapeConnector
												.setBounds(
														connectorEndPoint.x + 99,
														connectorTrueStartPoint.y + 40,
														connectorTrueStartPoint.x
																- connectorEndPoint.x
																- 98,
														connectorEndPoint.y
																- connectorTrueStartPoint.y
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										isConnectorActive = false;
										ifCount = 0;
										shapeLoopConnectors.add(shapeConnector);
										trueCount = 0;

									}
									if (falseCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorFalseStartPoint.x,
												connectorFalseStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifParent = index;
										shapeInput.setIfParent(true);
										shapeConnector
												.setLoopConnectorTypeString("false");

										shapeConnector
												.setBounds(
														connectorFalseStartPoint.x + 240,
														connectorFalseStartPoint.y + 40,
														(connectorEndPoint.x + 101)
																- (connectorFalseStartPoint.x + 240)
																+ 1,
														connectorEndPoint.y
																- (connectorFalseStartPoint.y + 40)
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										ifCount = 0;
										falseCount = 0;
										shapeLoopConnectors.add(shapeConnector);
									}

								} else if (SwingUtilities.isRightMouseButton(e)
										&& isInIf) {
									System.out
											.println("ifin yapragý sað týklandý");
									shapeInput
											.setHelperConnectorTrueParent(true);
									trueParentCount++;
									helperConnectorTrueParent = index;
									helperConnectorTrueStartPoint.x = e
											.getComponent().getX();
									helperConnectorTrueStartPoint.y = e
											.getComponent().getY();

									falseParentCount++;
									helperConnectorFalseParent = index;
									helperConnectorFalseStartPoint.x = e
											.getComponent().getX();
									helperConnectorFalseStartPoint.y = e
											.getComponent().getY();
								} else if (loopWhileConnectorCount == 1) {
									shapeInput.setLoopChild(true);
									isWhileChild = true;
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("while");
									// bound u degistir.
									System.out.println("start point x y "
											+ connectorStartPoint.x + " "
											+ connectorStartPoint.y);
									shapeConnector
											.setBounds(
													connectorStartPoint.x + 220,
													connectorStartPoint.y + 60,
													connectorEndPoint.x
															- (connectorStartPoint.x + 220)
															+ 101,
													connectorEndPoint.y
															+ 45
															- (connectorStartPoint.y + 60)
															+ 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopWhileConnectorCount++;// 2 oldugu
																// durumda sag
																// týk gelirse
																// birleþtir
																// tekrar
									shapeLoopConnectors.add(shapeConnector);

								} else if (loopConnectorCount == 1) {
									shapeInput.setLoopChild(true);
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopConnectorCount++;
									shapeLoopConnectors.add(shapeConnector);

								} else if (SwingUtilities.isRightMouseButton(e)) {
									if (loopWhileConnectorCount == 2) {
										shapeInput.setLoopParent(true);
										isWhileParent = true;

										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan,
																				// whiledan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("whileReturn1");
										// bound degisicek
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 220,
														connectorLoopStartPoint.y + 20 - 30,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 1,
														connectorEndPoint.y
																+ 45
																- (connectorLoopStartPoint.y)
																+ 1);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopWhileConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									} else if (loopConnectorCount == 2) {
										shapeInput.setLoopParent(true);
										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("forReturn");
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 200,
														connectorLoopStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 21,
														connectorEndPoint.y
																- (connectorLoopStartPoint.y)
																+ 26);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									}
								} else {
									connectorCount++;
								}

								if (connectorCount == 1) {
									parent = index;
									shapeInput.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

								}
								if (connectorCount == 2) {
									child = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									shapeInput.setChild(true);
									if (isHelperConnectorParent == true) {
										shapeInput
												.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										connectionName += Integer
												.toString(child);
										System.out.println("connection name : "
												+ connectionName);

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new InputDetailFrame(shapeInput);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeInput.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeInput.setX(sx);
							shapeInput.setY(sy);
							shapeInput.setBounds(sx, sy, 203, 91);

							shapeInput.repaint();
							if (shapeInput.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeInput)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeInput.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeInput));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeInput)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeInput.isHelperConnectorChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeInput)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 10,
												conn.getY1() + 20,
												sx - (conn.getX1() + 10) + 101,
												sy - (conn.getY1() + 19));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// deðiþkenler returnu tamamlayan bileþen ise
							// tamamlama çizgisi
							if (shapeInput.isLoopParent()) {
								if (isWhileParent == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeInput)) {

											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 220,
													conn.getY1() + 20 - 30, sx
															- (conn.getX1())
															+ 1, sy + 45
															- (conn.getY1())
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeInput)) {
											System.out
													.println("drag if isloopparent a girdi");
											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 20, sx
															- (conn.getX1())
															+ 21,
													sy - (conn.getY1()) + 26);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;
										}

									}
								}

							}
							if (shapeInput.isLoopChild()) {
								if (isWhileChild == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeInput)) {

											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 60,
													sx - (conn.getX1() + 220)
															+ 101,
													sy
															+ 45
															- (conn.getY1() + 60)
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeInput)) {
											System.out
													.println("drag if isloopchild a girdi");
											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 61, sx
															- (conn.getX1())
															- 200 + 1, sy
															- (conn.getY1())
															- 15 + 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								}

							}

							if (shapeInput.isIfChild()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfChildIdx() == shapeLists
											.indexOf(shapeInput)) {

										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX2() + 99,
												conn.getY1() + 40, conn.getX1()
														- conn.getX2() - 98,
												conn.getY2() - (conn.getY1())
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							if (shapeInput.isIfParent()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfParentIdx() == shapeLists
											.indexOf(shapeInput)) {

										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(
												conn.getX1() + 240,
												conn.getY1() + 40,
												(conn.getX2() + 101)
														- (conn.getX1() + 240)
														+ 1, conn.getY2()
														- (conn.getY1() + 40)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}

							repaint();

						}
					};

					shapeInput.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeInput);
					break;

				case "helperconnector":
					HelperConnector shapeHelperConnector = new HelperConnector(
							100, 100);
					shapeHelperConnector.setBounds(100, 100, 21, 21);
					add(shapeHelperConnector, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

							x = e.getX();
							y = e.getY();
							sx = shapeHelperConnector.getX();
							sy = shapeHelperConnector.getY();
							if (isConnectorActive) {

								index = shapeLists
										.indexOf(shapeHelperConnector);
								if (trueParentCount > 0
										|| falseParentCount == 1) {
									shapeHelperConnector.setTrueChild(true);

									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									if (connectorEndPoint.x < helperConnectorFalseStartPoint.x) {
										helperConnectorFalseChild = index;
										falseParentCount = 0;
										Connector shapeConnector = new Connector(
												helperConnectorFalseStartPoint.x,
												helperConnectorFalseStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector
												.setLoopConnectorTypeString("falseHelperConnector");

										shapeConnector
												.setBounds(
														helperConnectorFalseStartPoint.x
																- (helperConnectorFalseStartPoint.x + 101 - (connectorEndPoint.x + 20))
																+ 101,
														helperConnectorTrueStartPoint.y + 90,
														(helperConnectorFalseStartPoint.x + 101)
																- (connectorEndPoint.x + 20)
																+ 1,
														connectorEndPoint.y
																- (helperConnectorTrueStartPoint.y + 90)
																+ 1);

										shapeConnector
												.setHelperConnectorFalseParentIdx(helperConnectorFalseParent);
										shapeConnector
												.setHelperConnectorFalseChildIdx(helperConnectorFalseChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeLoopConnectors.add(shapeConnector);
									}
									if (connectorEndPoint.x > helperConnectorTrueStartPoint.x) {
										helperConnectorTrueChild = index;
										trueParentCount = 0;
										Connector shapeConnector = new Connector(
												helperConnectorTrueStartPoint.x,
												helperConnectorTrueStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector
												.setLoopConnectorTypeString("trueHelperConnector");
										shapeConnector
												.setBounds(
														helperConnectorTrueStartPoint.x + 101,
														helperConnectorTrueStartPoint.y + 90,
														connectorEndPoint.x
																- (helperConnectorTrueStartPoint.x + 101)
																+ 1,
														connectorEndPoint.y
																- (helperConnectorTrueStartPoint.y + 90)
																+ 1);

										shapeConnector
												.setHelperConnectorTrueParentIdx(helperConnectorTrueParent);
										shapeConnector
												.setHelperConnectorTrueChildIdx(helperConnectorTrueChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeLoopConnectors.add(shapeConnector);

									}

								}
								connectorCount++;

								if (connectorCount == 1) {
									parent = index;
									isHelperConnectorParent = true;
									shapeHelperConnector.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

								}
								// if (connectorCount == 2) {
								// child = index;
								// shapeHelperConnector.setChild(true);
								// // connectorEndPoint = e.getPoint();
								// // connectors.add(drawConnector(
								// // connectorStartPoint,
								// // connectorEndPoint));
								// // isConnectorActive = false;
								// // repaint();
								//
								// }
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}
					};

					shapeHelperConnector.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeHelperConnector.setX(sx);
							shapeHelperConnector.setY(sy);
							shapeHelperConnector.setBounds(sx, sy, 21, 21);
							shapeHelperConnector.repaint();

							if (shapeHelperConnector.isParent()) {
								for (Connector conn : shapeConnectors) {
									// System.out
									// .println("parent start : "
									// + shapeLists
									// .indexOf(shapeHelperConnector));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeHelperConnector)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 10,
												conn.getY1() + 20, conn.getX2()
														- (conn.getX1() + 10)
														+ 101, conn.getY2()
														- (conn.getY1() + 19));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeHelperConnector.isTrueChild()) {
								for (Connector conn : shapeLoopConnectors) {
									if (conn.getHelperConnectorTrueChildIdx() == shapeLists
											.indexOf(shapeHelperConnector)) {
										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(conn.getX1() + 101,
												conn.getY1() + 90, conn.getX2()
														- (conn.getX1() + 101)
														+ 1, conn.getY2()
														- (conn.getY1() + 90)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();

									}
								}

							}
							if (shapeHelperConnector.isTrueChild()) {
								for (Connector conn : shapeLoopConnectors) {
									if (conn.getHelperConnectorFalseChildIdx() == shapeLists
											.indexOf(shapeHelperConnector)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(
												conn.getX1()
														+ 101
														- (conn.getX1() + 101 - (conn
																.getX2() + 20)),
												conn.getY1() + 90,
												(conn.getX1() + 101)
														- (conn.getX2() + 20)
														+ 1, conn.getY2()
														- (conn.getY1() + 90)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();

									}
								}

							}
							repaint();
						}
					};

					shapeHelperConnector.addMouseMotionListener(motionListener);
					shapeLists.add(shapeHelperConnector);
					break;
				case "start":
					Start shapeStart = new Start(100, 100);
					shapeStart.setBounds(100, 100, 201, 91);
					add(shapeStart, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub

							x = e.getX();
							y = e.getY();
							sx = shapeStart.getX();
							sy = shapeStart.getY();
							if (isConnectorActive) {

								index = shapeLists.indexOf(shapeStart);
								connectorCount++;
								if (SwingUtilities.isRightMouseButton(e)) {
									System.out.println("right click on start");
								}
								if (connectorCount == 1) {
									parent = index;
									shapeStart.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

								}
								if (connectorCount == 2) {
									child = index;
									shapeStart.setChild(true);
									// connectorEndPoint = e.getPoint();
									// connectors.add(drawConnector(
									// connectorStartPoint,
									// connectorEndPoint));
									// isConnectorActive = false;
									// repaint();

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}
					};

					shapeStart.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeStart.setX(sx);
							shapeStart.setY(sy);
							shapeStart.setBounds(sx, sy, 201, 91);
							shapeStart.repaint();

							if (shapeStart.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeStart));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeStart)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}

							repaint();
						}
					};

					shapeStart.addMouseMotionListener(motionListener);
					shapeLists.add(shapeStart);
					break;
				case "end":
					End shapeEnd = new End(100, 100);
					shapeEnd.setBounds(100, 100, 203, 113);
					add(shapeEnd, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeEnd.getX();
							sy = shapeEnd.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeEnd);
								connectorCount++;
								if (connectorCount == 1) {
									parent = index;
									shapeEnd.setParent(true);
									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									shapeEnd.setChild(true);
									connectionName += Integer.toString(child);
									System.out.println("connection name : "
											+ connectionName);

									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									isConnectorActive = false;
									if (isHelperConnectorParent == true) {
										shapeEnd.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setX1(connectorStartPoint.x);
										shapeConnector
												.setY1(connectorStartPoint.y);
										shapeConnector
												.setX2(connectorEndPoint.x);
										shapeConnector
												.setY2(connectorEndPoint.y);
										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										// isConnectorActive = false;
										// repaint();
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}
					};
					shapeEnd.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeEnd.setX(sx);
							shapeEnd.setY(sy);
							shapeEnd.setBounds(sx, sy, 203, 113);
							shapeEnd.repaint();
							if (shapeEnd.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeEnd)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							repaint();
						}
					};

					shapeEnd.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeEnd);
					break;

				case "function":
					Function shapeFunction = new Function(100, 100);
					shapeFunction.setBounds(100, 100, 201, 91);
					add(shapeFunction, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeFunction.getX();
							sy = shapeFunction.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeFunction);

								if (ifCount == 1) {
									isInIf = true;
									if (trueCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorTrueStartPoint.x,
												connectorTrueStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifChild = index;
										shapeFunction.setIfChild(true);
										shapeConnector
												.setLoopConnectorTypeString("true");

										shapeConnector
												.setBounds(
														connectorEndPoint.x + 99,
														connectorTrueStartPoint.y + 40,
														connectorTrueStartPoint.x
																- connectorEndPoint.x
																- 98,
														connectorEndPoint.y
																- connectorTrueStartPoint.y
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										isConnectorActive = false;
										ifCount = 0;
										shapeLoopConnectors.add(shapeConnector);
										trueCount = 0;

									}
									if (falseCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorFalseStartPoint.x,
												connectorFalseStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifParent = index;
										shapeFunction.setIfParent(true);
										shapeConnector
												.setLoopConnectorTypeString("false");

										shapeConnector
												.setBounds(
														connectorFalseStartPoint.x + 240,
														connectorFalseStartPoint.y + 40,
														(connectorEndPoint.x + 101)
																- (connectorFalseStartPoint.x + 240)
																+ 1,
														connectorEndPoint.y
																- (connectorFalseStartPoint.y + 40)
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										ifCount = 0;
										falseCount = 0;
										shapeLoopConnectors.add(shapeConnector);
									}

								} else if (SwingUtilities.isRightMouseButton(e)
										&& isInIf) {
									System.out
											.println("ifin yapragý sað týklandý");
									shapeFunction
											.setHelperConnectorTrueParent(true);
									trueParentCount++;
									helperConnectorTrueParent = index;
									helperConnectorTrueStartPoint.x = e
											.getComponent().getX();
									helperConnectorTrueStartPoint.y = e
											.getComponent().getY();

									falseParentCount++;
									helperConnectorFalseParent = index;
									helperConnectorFalseStartPoint.x = e
											.getComponent().getX();
									helperConnectorFalseStartPoint.y = e
											.getComponent().getY();
								} else if (loopWhileConnectorCount == 1) {
									shapeFunction.setLoopChild(true);
									isWhileChild = true;
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("while");
									// bound u degistir.
									System.out.println("start point x y "
											+ connectorStartPoint.x + " "
											+ connectorStartPoint.y);
									shapeConnector
											.setBounds(
													connectorStartPoint.x + 220,
													connectorStartPoint.y + 60,
													connectorEndPoint.x
															- (connectorStartPoint.x + 220)
															+ 101,
													connectorEndPoint.y
															+ 45
															- (connectorStartPoint.y + 60)
															+ 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopWhileConnectorCount++;// 2 oldugu
																// durumda sag
																// týk gelirse
																// birleþtir
																// tekrar
									shapeLoopConnectors.add(shapeConnector);

								} else if (loopConnectorCount == 1) {
									shapeFunction.setLoopChild(true);
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopConnectorCount++;
									shapeLoopConnectors.add(shapeConnector);

								} else if (SwingUtilities.isRightMouseButton(e)) {
									if (loopWhileConnectorCount == 2) {
										shapeFunction.setLoopParent(true);
										isWhileParent = true;

										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan,
																				// whiledan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("whileReturn1");
										// bound degisicek
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 220,
														connectorLoopStartPoint.y + 20 - 30,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 1,
														connectorEndPoint.y
																+ 45
																- (connectorLoopStartPoint.y)
																+ 1);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopWhileConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									} else if (loopConnectorCount == 2) {
										shapeFunction.setLoopParent(true);
										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("forReturn");
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 200,
														connectorLoopStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 21,
														connectorEndPoint.y
																- (connectorLoopStartPoint.y)
																+ 26);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									}
								} else {
									connectorCount++;
								}

								if (connectorCount == 1) {
									parent = index;
									shapeFunction.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									shapeFunction.setChild(true);
									if (isHelperConnectorParent == true) {
										shapeFunction
												.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										connectionName += Integer
												.toString(child);
										System.out.println("connection name : "
												+ connectionName);

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new FunctionDetailFrame(
										shapeFunction);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeFunction.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeFunction.setX(sx);
							shapeFunction.setY(sy);
							shapeFunction.setBounds(sx, sy, 201, 91);
							shapeFunction.repaint();
							if (shapeFunction.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeFunction)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// if (shapeFunction.isLoopParent()) {
							// for (Connector conn : shapeLoopConnectors) {
							// if (conn.getLoopParentIdx() == shapeLists
							// .indexOf(shapeFunction)) {
							// System.out
							// .println("drag if isloopparent a girdi");
							// conn.setX2(sx);
							// conn.setY2(sy);
							// conn.setBounds(conn.getX1() + 200,
							// conn.getY1() + 20,
							// sx - (conn.getX1()) + 21, sy
							// - (conn.getY1()) + 26);
							// add(conn, BorderLayout.CENTER);
							// conn.repaint();
							// isConnectorActive = false;
							//
							// }
							// }
							//
							// }
							// if (shapeFunction.isLoopChild()) {
							// for (Connector conn : shapeLoopConnectors) {
							// if (conn.getLoopChildIdx() == shapeLists
							// .indexOf(shapeFunction)) {
							// System.out
							// .println("drag if isloopchild a girdi");
							// conn.setX2(sx);
							// conn.setY2(sy);
							//
							// conn.setBounds(conn.getX1() + 200,
							// conn.getY1() + 61,
							// sx - (conn.getX1()) - 200 + 1,
							// sy - (conn.getY1()) - 15 + 1);
							// add(conn, BorderLayout.CENTER);
							// conn.repaint();
							// isConnectorActive = false;
							//
							// }
							// }
							//
							// }
							if (shapeFunction.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out
									// .println("parent start : "
									// + shapeLists
									// .indexOf(shapeFunction));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeFunction)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeFunction.isHelperConnectorChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeFunction)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 10,
												conn.getY1() + 20,
												sx - (conn.getX1() + 10) + 101,
												sy - (conn.getY1() + 19));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// deðiþkenler returnu tamamlayan bileþen ise
							// tamamlama çizgisi
							if (shapeFunction.isLoopParent()) {
								if (isWhileParent == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeFunction)) {

											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 220,
													conn.getY1() + 20 - 30, sx
															- (conn.getX1())
															+ 1, sy + 45
															- (conn.getY1())
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeFunction)) {
											System.out
													.println("drag if isloopparent a girdi");
											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 20, sx
															- (conn.getX1())
															+ 21,
													sy - (conn.getY1()) + 26);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;
										}

									}
								}

							}
							if (shapeFunction.isLoopChild()) {
								if (isWhileChild == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeFunction)) {

											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 60,
													sx - (conn.getX1() + 220)
															+ 101,
													sy
															+ 45
															- (conn.getY1() + 60)
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeFunction)) {
											System.out
													.println("drag if isloopchild a girdi");
											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 61, sx
															- (conn.getX1())
															- 200 + 1, sy
															- (conn.getY1())
															- 15 + 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								}

							}

							if (shapeFunction.isIfChild()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfChildIdx() == shapeLists
											.indexOf(shapeFunction)) {

										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX2() + 99,
												conn.getY1() + 40, conn.getX1()
														- conn.getX2() - 98,
												conn.getY2() - (conn.getY1())
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							if (shapeFunction.isIfParent()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfParentIdx() == shapeLists
											.indexOf(shapeFunction)) {

										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(
												conn.getX1() + 240,
												conn.getY1() + 40,
												(conn.getX2() + 101)
														- (conn.getX1() + 240)
														+ 1, conn.getY2()
														- (conn.getY1() + 40)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}

							repaint();
						}
					};
					shapeFunction.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeFunction);
					break;
				case "for":
					For shapeFor = new For(100, 100);
					shapeFor.setBounds(100, 100, 201, 91);
					add(shapeFor, BorderLayout.CENTER);
					shapeFor.setStrings("", "", "");
					// loopConnectorCount = 0;
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeFor.getX();
							sy = shapeFor.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeFor);
								// connectorCount++;

								if (SwingUtilities.isRightMouseButton(e)) {
									System.out.println("right click on for");
									shapeFor.setLoopParent(true);
									shapeFor.setLoopChild(true);
									loopParent = index;
									loopReturn = index;
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();
									connectorLoopStartPoint.x = connectorStartPoint.x;
									connectorLoopStartPoint.y = connectorStartPoint.y;
									loopConnectorCount++;
								} else if (loopConnectorCount == 1) {
									System.out.println("ikinci for a geldi");
									shapeFor.setLoopChild(true);
									loopChild = shapeLists.indexOf(shapeFor);
									System.out.println("loopParent "
											+ loopParent + " loopChild "
											+ loopChild);
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									// loopConnectorCount++;
									loopConnectorCount = 0;// içteki for un
															// direk sað týk ile
															// return u olmamalý
									loopForCount++;
									loopForReturn = loopParent;
									shapeLoopConnectors.add(shapeConnector);

								} else if (loopForCount == 1
										&& SwingUtilities.isRightMouseButton(e)) {

									shapeFor.setLoopParent(true);
									loopParent = index;
									Connector shapeConnector = new Connector(
											connectorLoopStartPoint.x,
											connectorLoopStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector
											.setLoopChildIdx(loopForReturn);// fordan
									// gelmeli

									shapeConnector
											.setLoopConnectorTypeString("forReturn");
									shapeConnector
											.setBounds(
													connectorLoopStartPoint.x + 200,
													connectorLoopStartPoint.y + 20,
													connectorEndPoint.x
															- (connectorLoopStartPoint.x)
															+ 21,
													connectorEndPoint.y
															- (connectorLoopStartPoint.y)
															+ 26);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopForCount = 0;
									shapeLoopConnectors.add(shapeConnector);

								} else {
									connectorCount++;
								}
								if (connectorCount == 1) {
									parent = index;
									shapeFor.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									shapeFor.setChild(true);
									isConnectorActive = false;
									connectionName += Integer.toString(child);
									System.out.println("connection name : "
											+ connectionName);

									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									if (isHelperConnectorParent == true) {
										shapeFor.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										// isConnectorActive = false;
										// repaint();
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}

						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								System.out.println("cift týklandý");
								detailFrame = new ForDetailFrame("For",
										shapeFor);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeFor.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeFor.setX(sx);
							shapeFor.setY(sy);
							shapeFor.setBounds(sx, sy, 201, 91);
							shapeFor.repaint();
							if (shapeFor.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeFor)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeFor.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeFor));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeFor)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeFor.isLoopParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeLoopConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeFor));
									if (conn.getLoopParentIdx() == shapeLists
											.indexOf(shapeFor)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 200,
												conn.getY1() + 61, conn.getX2()
														- (conn.getX1()) - 200
														+ 1, conn.getY2()
														- (conn.getY1()) - 15
														+ 1);

										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// duzenle
							if (shapeFor.isLoopChild()) {
								for (Connector conn : shapeLoopConnectors) {
									if (conn.getLoopChildIdx() == shapeLists
											.indexOf(shapeFor)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 200,
												conn.getY1() + 20, conn.getX2()
														- (conn.getX1()) + 61,
												conn.getY2() - (conn.getY1())
														+ 26);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							repaint();
						}
					};

					shapeFor.addMouseMotionListener(motionListener);
					shapeLists.add(shapeFor);
					connectors.add(connectionName);
					break;
				case "variables":
					Variables shapeVariables = new Variables(100, 100);
					shapeVariables.setBounds(100, 100, 201, 91);
					add(shapeVariables, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeVariables.getX();
							sy = shapeVariables.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeVariables);
								if (ifCount == 1) {
									isInIf = true;
									if (trueCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorTrueStartPoint.x,
												connectorTrueStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifChild = index;
										shapeVariables.setIfChild(true);
										shapeConnector
												.setLoopConnectorTypeString("true");

										shapeConnector
												.setBounds(
														connectorEndPoint.x + 99,
														connectorTrueStartPoint.y + 40,
														connectorTrueStartPoint.x
																- connectorEndPoint.x
																- 98,
														connectorEndPoint.y
																- connectorTrueStartPoint.y
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										isConnectorActive = false;
										ifCount = 0;
										shapeLoopConnectors.add(shapeConnector);
										trueCount = 0;

									}
									if (falseCount == 1) {
										connectorEndPoint.x = e.getComponent()
												.getX();
										connectorEndPoint.y = e.getComponent()
												.getY();
										Connector shapeConnector = new Connector(
												connectorFalseStartPoint.x,
												connectorFalseStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										ifParent = index;
										shapeVariables.setIfParent(true);
										shapeConnector
												.setLoopConnectorTypeString("false");

										shapeConnector
												.setBounds(
														connectorFalseStartPoint.x + 240,
														connectorFalseStartPoint.y + 40,
														(connectorEndPoint.x + 101)
																- (connectorFalseStartPoint.x + 240)
																+ 1,
														connectorEndPoint.y
																- (connectorFalseStartPoint.y + 40)
																+ 1);
										shapeConnector.setIfParentIdx(ifParent);
										shapeConnector.setIfChildIdx(ifChild);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										ifCount = 0;
										falseCount = 0;
										shapeLoopConnectors.add(shapeConnector);
									}

								} else if (SwingUtilities.isRightMouseButton(e)
										&& isInIf) {
									System.out
											.println("ifin yapragý sað týklandý");
									shapeVariables
											.setHelperConnectorTrueParent(true);
									trueParentCount++;
									helperConnectorTrueParent = index;
									helperConnectorTrueStartPoint.x = e
											.getComponent().getX();
									helperConnectorTrueStartPoint.y = e
											.getComponent().getY();

									falseParentCount++;
									helperConnectorFalseParent = index;
									helperConnectorFalseStartPoint.x = e
											.getComponent().getX();
									helperConnectorFalseStartPoint.y = e
											.getComponent().getY();
								} else if (loopWhileConnectorCount == 1) {
									shapeVariables.setLoopChild(true);
									isWhileChild = true;
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("while");
									// bound u degistir.
									System.out.println("start point x y "
											+ connectorStartPoint.x + " "
											+ connectorStartPoint.y);
									shapeConnector
											.setBounds(
													connectorStartPoint.x + 220,
													connectorStartPoint.y + 60,
													connectorEndPoint.x
															- (connectorStartPoint.x + 220)
															+ 101,
													connectorEndPoint.y
															+ 45
															- (connectorStartPoint.y + 60)
															+ 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopWhileConnectorCount++;// 2 oldugu
																// durumda sag
																// týk gelirse
																// birleþtir
																// tekrar
									shapeLoopConnectors.add(shapeConnector);

								} else if (loopConnectorCount == 1) {
									shapeVariables.setLoopChild(true);
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopConnectorCount++;
									shapeLoopConnectors.add(shapeConnector);

								} else if (SwingUtilities.isRightMouseButton(e)) {
									if (loopWhileConnectorCount == 2) {
										shapeVariables.setLoopParent(true);
										isWhileParent = true;

										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan,
																				// whiledan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("whileReturn1");
										// bound degisicek
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 220,
														connectorLoopStartPoint.y + 20 - 30,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 1,
														connectorEndPoint.y
																+ 45
																- (connectorLoopStartPoint.y)
																+ 1);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopWhileConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									} else if (loopConnectorCount == 2) {
										shapeVariables.setLoopParent(true);
										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("forReturn");
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 200,
														connectorLoopStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 21,
														connectorEndPoint.y
																- (connectorLoopStartPoint.y)
																+ 26);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									}
								} else {
									connectorCount++;
								}

								if (connectorCount == 1) {
									parent = index;
									shapeVariables.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									shapeVariables.setChild(true);
									if (isHelperConnectorParent == true) {
										shapeVariables
												.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										connectionName += Integer
												.toString(child);
										System.out.println("connection name : "
												+ connectionName);

										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									}
								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new VariablesDetailFrame(
										shapeVariables);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeVariables.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeVariables.setX(sx);
							shapeVariables.setY(sy);
							shapeVariables.setBounds(sx, sy, 201, 91);
							shapeVariables.repaint();
							if (shapeVariables.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeVariables)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91,
												sx - (conn.getX1()) + 101, sy
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeVariables.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists
									// .indexOf(shapeVariables));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeVariables)) {
										conn.setX1(sx);
										conn.setY1(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														- (conn.getX1()) + 101,
												conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;
									}
								}
							}
							if (shapeVariables.isHelperConnectorChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeVariables)) {
										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 10,
												conn.getY1() + 20,
												sx - (conn.getX1() + 10) + 101,
												sy - (conn.getY1() + 19));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							// deðiþkenler returnu tamamlayan bileþen ise
							// tamamlama çizgisi
							if (shapeVariables.isLoopParent()) {
								if (isWhileParent == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeVariables)) {

											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 220,
													conn.getY1() + 20 - 30, sx
															- (conn.getX1())
															+ 1, sy + 45
															- (conn.getY1())
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeVariables)) {
											System.out
													.println("drag if isloopparent a girdi");
											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 20, sx
															- (conn.getX1())
															+ 21,
													sy - (conn.getY1()) + 26);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;
										}

									}
								}

							}
							if (shapeVariables.isLoopChild()) {
								if (isWhileChild == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeVariables)) {

											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 60,
													sx - (conn.getX1() + 220)
															+ 101,
													sy
															+ 45
															- (conn.getY1() + 60)
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeVariables)) {
											System.out
													.println("drag if isloopchild a girdi");
											conn.setX2(sx);
											conn.setY2(sy);

											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 61, sx
															- (conn.getX1())
															- 200 + 1, sy
															- (conn.getY1())
															- 15 + 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								}

							}

							if (shapeVariables.isIfChild()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfChildIdx() == shapeLists
											.indexOf(shapeVariables)) {

										conn.setX2(sx);
										conn.setY2(sy);
										conn.setBounds(conn.getX2() + 99,
												conn.getY1() + 40, conn.getX1()
														- conn.getX2() - 98,
												conn.getY2() - (conn.getY1())
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							if (shapeVariables.isIfParent()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfParentIdx() == shapeLists
											.indexOf(shapeVariables)) {

										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(
												conn.getX1() + 240,
												conn.getY1() + 40,
												(conn.getX2() + 101)
														- (conn.getX1() + 240)
														+ 1, conn.getY2()
														- (conn.getY1() + 40)
														+ 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}
							}
							repaint();
						}
					};
					shapeVariables.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeVariables);
					break;
				case "while":
					While shapeWhile = new While(100, 100);
					shapeWhile.setBounds(100, 100, 244, 81);
					add(shapeWhile, BorderLayout.CENTER);
					repaint();

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeWhile.getX();
							sy = shapeWhile.getY();
							if (isConnectorActive) {

								index = shapeLists.indexOf(shapeWhile);
								if (SwingUtilities.isRightMouseButton(e)) {
									System.out.println("right click on while");
									shapeWhile.setLoopParent(true);
									isWhileOwnParent = true;
									shapeWhile.setLoopChild(true);
									isWhileOwnChild = true;
									loopParent = index;
									loopReturn = index;
									connectorStartPoint.x = e.getComponent()
											.getX();
									connectorStartPoint.y = e.getComponent()
											.getY();
									connectorLoopStartPoint.x = connectorStartPoint.x;
									connectorLoopStartPoint.y = connectorStartPoint.y;
									loopWhileConnectorCount++;// bunun 1 olduðu
																// yere
																// baðlanacak
																// artýk
								} else if (loopConnectorCount == 1) {
									shapeWhile.setLoopChild(true);
									loopChild = index;
									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									Connector shapeConnector = new Connector(
											connectorStartPoint.x,
											connectorStartPoint.y,
											connectorEndPoint.x,
											connectorEndPoint.y);
									shapeConnector.setLoopParentIdx(loopParent);
									shapeConnector.setLoopChildIdx(loopChild);
									shapeConnector
											.setLoopConnectorTypeString("for");
									shapeConnector.setBounds(
											connectorStartPoint.x + 200,
											connectorStartPoint.y + 61,
											connectorEndPoint.x
													- (connectorStartPoint.x)
													- 200 + 1,
											connectorEndPoint.y
													- (connectorStartPoint.y)
													- 15 + 1);
									add(shapeConnector, BorderLayout.CENTER);
									repaint();
									isConnectorActive = false;
									loopConnectorCount++;
									shapeLoopConnectors.add(shapeConnector);

								} else if (SwingUtilities.isRightMouseButton(e)) {
									if (loopConnectorCount == 2) {
										shapeWhile.setLoopParent(true);
										loopParent = index;
										Connector shapeConnector = new Connector(
												connectorLoopStartPoint.x,
												connectorLoopStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);
										shapeConnector
												.setLoopParentIdx(loopParent);
										shapeConnector
												.setLoopChildIdx(loopReturn);// fordan
																				// gelmeli

										shapeConnector
												.setLoopConnectorTypeString("whileReturn");
										shapeConnector
												.setBounds(
														connectorLoopStartPoint.x + 200,
														connectorLoopStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorLoopStartPoint.x)
																+ 61,
														connectorEndPoint.y
																- (connectorLoopStartPoint.y)
																+ 26);
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										loopConnectorCount = 0;
										shapeLoopConnectors.add(shapeConnector);

									}
								} else {
									connectorCount++;
								}

								if (connectorCount == 1) {
									parent = index;
									shapeWhile.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX() + 15;
									connectorStartPoint.y = e.getComponent()
											.getY() - 15;

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									shapeWhile.setChild(true);
									isConnectorActive = false;
									connectionName += Integer.toString(child);
									System.out.println("connection name : "
											+ connectionName);

									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									if (isHelperConnectorParent == true) {
										shapeWhile
												.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x + 15,
												connectorEndPoint.y);
										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("parent : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																+ 15
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										// isConnectorActive = false;
										// repaint();
										shapeConnectors.add(shapeConnector);
									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new WhileDetailFrame(shapeWhile);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeWhile.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeWhile.setX(sx);
							shapeWhile.setY(sy);
							shapeWhile.setBounds(sx, sy, 244, 81);
							shapeWhile.repaint();
							if (shapeWhile.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeWhile)) {
										conn.setX2(sx + 15);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, sx + 15
														- (conn.getX1()) + 101,
												sy - (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeWhile.isLoopParent()) {
								if (isWhileOwnParent == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeWhile)) {
											conn.setX1(sx);
											conn.setY1(sy);
											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 60,
													conn.getX2()
															- (conn.getX1() + 220)
															+ 101,
													conn.getY2()
															+ 45
															- (conn.getY1() + 60)
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								} else {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopParentIdx() == shapeLists
												.indexOf(shapeWhile)) {
											System.out
													.println("drag if isloopparent a girdi");
											conn.setX2(sx);
											conn.setY2(sy);
											conn.setBounds(conn.getX1() + 200,
													conn.getY1() + 20, sx
															- (conn.getX1())
															+ 61,
													sy - (conn.getY1()) + 26);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}
								}

							}
							if (shapeWhile.isLoopChild()) {
								if (isWhileOwnChild == true) {
									for (Connector conn : shapeLoopConnectors) {
										if (conn.getLoopChildIdx() == shapeLists
												.indexOf(shapeWhile)) {
											System.out
													.println("drag if isloopchild a girdi");
											conn.setX1(sx);
											conn.setY1(sy);
											conn.setBounds(
													conn.getX1() + 220,
													conn.getY1() + 20 - 30,
													conn.getX2()
															- (conn.getX1())
															+ 1,
													conn.getY2() + 45
															- (conn.getY1())
															+ 1);

											add(conn, BorderLayout.CENTER);
											conn.repaint();
											isConnectorActive = false;

										}
									}

								}

								for (Connector conn : shapeLoopConnectors) {
									if (conn.getLoopChildIdx() == shapeLists
											.indexOf(shapeWhile)) {
										System.out
												.println("drag if isloopchild a girdi");
										conn.setX2(sx);
										conn.setY2(sy);

										conn.setBounds(conn.getX1() + 200,
												conn.getY1() + 61,
												sx - (conn.getX1()) - 200 + 1,
												sy - (conn.getY1()) - 15 + 1);
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}

							if (shapeWhile.isParent()) {
								// connectorStartPoint.x = sx;
								// connectorStartPoint.y = sy;
								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeWhile));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeWhile)) {
										conn.setX1(sx + 15);
										conn.setY1(sy - 10);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														+ 15 - (conn.getX1())
														+ 101, conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							repaint();
						}
					};

					shapeWhile.addMouseMotionListener(motionListener);
					shapeLists.add(shapeWhile);
					connectors.add(connectionName);
					break;
				case "if":
					If shapeIf = new If(100, 100);
					shapeIf.setBounds(100, 100, 244, 81);
					add(shapeIf, BorderLayout.CENTER);
					repaint();
					ifCount = 0;
					trueCount = 0;
					falseCount = 0;

					mListener = new MouseAdapter() {
						@Override
						public void mousePressed(MouseEvent e) {
							// TODO Auto-generated method stub
							x = e.getX();
							y = e.getY();
							sx = shapeIf.getX();
							sy = shapeIf.getY();
							if (isConnectorActive) {
								index = shapeLists.indexOf(shapeIf);
								if (SwingUtilities.isRightMouseButton(e)) {

									JDialog.setDefaultLookAndFeelDecorated(true);
									Object[] selectionValues = { "True",
											"False" };
									Object selection = JOptionPane
											.showInputDialog(
													null,
													"Hangi  kola bileþen eklemek istersin ? ",
													"If Kol Seçimi",
													JOptionPane.QUESTION_MESSAGE,
													null, selectionValues,
													"True");

									if (selection.equals("True")) {
										System.out.println("true aktif oldu ");
										shapeIf.setTrue(true);
										shapeIf.setIfParent(true);
										ifParent = index;
										trueCount++;
										connectorTrueStartPoint.x = e
												.getComponent().getX();
										connectorTrueStartPoint.y = e
												.getComponent().getY();

									}
									if (selection.equals("False")) {
										System.out.println("false aktif oldu ");
										shapeIf.setFalse(true);
										shapeIf.setIfChild(true);
										ifChild = index;
										falseCount++;
										connectorFalseStartPoint.x = e
												.getComponent().getX();
										connectorFalseStartPoint.y = e
												.getComponent().getY();
									}
									ifCount++;
								} else {
									connectorCount++;
								}
								// else bloguna alýnabilir

								if (connectorCount == 1) {
									parent = index;
									shapeIf.setParent(true);
									connectionName = Integer.toString(parent);
									connectorStartPoint.x = e.getComponent()
											.getX() + 15;
									connectorStartPoint.y = e.getComponent()
											.getY() - 10;

									// connectorStartPoint = e.getPoint();
								}
								if (connectorCount == 2) {
									child = index;
									shapeIf.setChild(true);
									connectionName += Integer.toString(child);
									System.out.println("connection name : "
											+ connectionName);

									connectorEndPoint.x = e.getComponent()
											.getX();
									connectorEndPoint.y = e.getComponent()
											.getY();
									isConnectorActive = false;
									if (isHelperConnectorParent == true) {
										shapeIf.setHelperConnectorChild(true);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x,
												connectorEndPoint.y);

										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										shapeConnector
												.setLoopConnectorTypeString("helperConnector");
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 10,
														connectorStartPoint.y + 20,
														connectorEndPoint.x
																- (connectorStartPoint.x + 10)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 19));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();
										isConnectorActive = false;
										shapeConnectors.add(shapeConnector);
									} else {
										// System.out.println(connectorEndPoint);
										Connector shapeConnector = new Connector(
												connectorStartPoint.x,
												connectorStartPoint.y,
												connectorEndPoint.x + 15,
												connectorEndPoint.y);
										shapeConnector
												.setX1(connectorStartPoint.x);
										shapeConnector
												.setY1(connectorStartPoint.y);
										shapeConnector
												.setX2(connectorEndPoint.x + 15);
										shapeConnector
												.setY2(connectorEndPoint.y);
										shapeConnector.setParentIdx(parent);
										shapeConnector.setChildIdx(child);
										System.out.println("paretn : "
												+ shapeConnector.getParentIdx()
												+ " child : "
												+ shapeConnector.getChildIdx());
										shapeConnector
												.setBounds(
														connectorStartPoint.x + 100,
														connectorStartPoint.y + 91,
														connectorEndPoint.x
																+ 15
																- (connectorStartPoint.x)
																+ 101,
														connectorEndPoint.y
																- (connectorStartPoint.y + 89));
										add(shapeConnector, BorderLayout.CENTER);
										repaint();

										// isConnectorActive = false;
										// repaint();
										shapeConnectors.add(shapeConnector);

									}

								}
								System.out.println(e.getComponent().getClass()
										.toString());
								System.out.println(e.getComponent().getX()
										+ " " + e.getComponent().getY() + " "
										+ e.getComponent().getWidth() + " "
										+ e.getComponent().getHeight());
							}
						}

						@Override
						public void mouseClicked(MouseEvent e) {
							if (e.getClickCount() == 2 && !e.isConsumed()) {
								e.consume();
								detailFrame = new IfDetailFrame(shapeIf);
								detailFrame.setLocation(x, y);
								detailFrame = null;

							}

						}
					};

					shapeIf.addMouseListener(mListener);

					motionListener = new MouseMotionAdapter() {
						@Override
						public void mouseDragged(MouseEvent e) {
							// TODO Auto-generated method stub
							dragx = e.getX();
							dragy = e.getY();
							int farkx = dragx - x;
							int farky = dragy - y;

							sx += farkx;
							sy += farky;

							shapeIf.setX(sx);
							shapeIf.setY(sy);
							shapeIf.setBounds(sx, sy, 244, 81);
							shapeIf.repaint();
							if (shapeIf.isChild()) {
								for (Connector conn : shapeConnectors) {
									if (conn.getChildIdx() == shapeLists
											.indexOf(shapeIf)) {
										conn.setX2(sx + 15);
										conn.setY2(sy);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, sx + 15
														- (conn.getX1()) + 101,
												sy - (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										isConnectorActive = false;

									}
								}

							}
							if (shapeIf.isParent()) {

								for (Connector conn : shapeConnectors) {
									// System.out.println("parent start : "
									// + shapeLists.indexOf(shapeIf));
									if (conn.getParentIdx() == shapeLists
											.indexOf(shapeIf)) {
										conn.setX1(sx + 15);
										conn.setY1(sy - 10);
										conn.setBounds(conn.getX1() + 100,
												conn.getY1() + 91, conn.getX2()
														+ 15 - (conn.getX1())
														+ 101, conn.getY2()
														- (conn.getY1() + 89));
										add(conn, BorderLayout.CENTER);
										conn.repaint();
										// isConnectorActive = false;

									}
								}

							}
							if (shapeIf.isIfChild()) {
								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfChildIdx() == shapeLists
											.indexOf(shapeIf)) {
										if (shapeIf.isFalse()) {
											System.out.println("is false");
											conn.setX1(sx);
											conn.setY1(sy);
											conn.setBounds(
													conn.getX1() + 240,
													conn.getY1() + 40,
													(conn.getX2() + 101)
															- (conn.getX1() + 240)
															+ 1,
													conn.getY2()
															- (conn.getY1() + 40)
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
										}
									}
								}
							}
							if (shapeIf.isIfParent()) {

								for (Connector conn : shapeLoopConnectors) {

									if (conn.getIfParentIdx() == shapeLists
											.indexOf(shapeIf)) {
										if (shapeIf.isTrue()) {
											conn.setX1(sx);
											conn.setY1(sy);
											conn.setBounds(conn.getX2() + 99,
													conn.getY1() + 40,
													conn.getX1() - conn.getX2()
															- 98, conn.getY2()
															- (conn.getY1())
															+ 1);
											add(conn, BorderLayout.CENTER);
											conn.repaint();
											// isConnectorActive = false;

										}

									}
								}
							}

							repaint();
						}
					};

					shapeIf.addMouseMotionListener(motionListener);
					connectors.add(connectionName);
					shapeLists.add(shapeIf);
					break;
				default:
					break;
				}
			}
		};
		addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {
				// TODO Auto-generated method stub
				System.out.println("focus gitti");

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				System.out.println("focus geldi");

			}
		});

		butonConnector.addActionListener(listener);
		butonInput.addActionListener(listener);
		butonOutput.addActionListener(listener);
		butonStart.addActionListener(listener);
		butonEnd.addActionListener(listener);
		butonFor.addActionListener(listener);
		butonFunction.addActionListener(listener);
		butonIf.addActionListener(listener);
		butonWhile.addActionListener(listener);
		butonVariables.addActionListener(listener);
		butonHelperConnector.addActionListener(listener);
		butonShow.addActionListener(listener);
		butonGenerate.addActionListener(listener);
		butonPng.addActionListener(listener);

		add(butonConnector);
		add(butonStart);
		add(butonInput);
		add(butonFor);
		add(butonIf);
		add(butonWhile);
		add(butonFunction);
		add(butonOutput);
		add(butonEnd);
		add(butonVariables);
		add(butonHelperConnector);
		add(butonShow);
		add(butonGenerate);
		add(butonPng);

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}

	public GeneralPath drawConnector(int x1, int y1, int x2, int y2) {

		// --------------- input çizim --------------------
		GeneralPath line = new GeneralPath();
		line.moveTo(x1, y1);
		line.lineTo(x2, y2);// sol uuzun çizgi

		return line;

	}

	@Override
	public void paintComponents(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponents(g);
		// Graphics2D graphics2d = (Graphics2D) g;
		// for (Shape shape : connectors) {
		// if (shape != null) {
		// graphics2d.draw(shape);
		// }
		// }
		update(g);
	}
}
