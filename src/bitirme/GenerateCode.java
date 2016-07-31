package bitirme;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GenerateCode {

	ArrayList<Connector> shapeConnectors, shapeLoopConnectors;
	ArrayList<JPanel> shapeLists;
	JPanel panel, panelLoop;
	InputGenerate inputGenerate;
	OutputGenerate outputGenerate;
	VariableGenerate variableGenerate;
	FunctionGenerate functionGenerate;
	IfGenerate ifGenerate;
	boolean isInFor = false, isInWhile = false, isInTrue = false;
	int idxFor = 0, idxWhile = 0, idxIf = 0, idxInTrue = 0, idxInFalse = 0;
	String programName = "";

	public GenerateCode(ArrayList<JPanel> shapeLists,
			ArrayList<Connector> shapeConnectors,
			ArrayList<Connector> shapeLoopConnectors) {

		programName = JOptionPane.showInputDialog("Program ismini giriniz : ");
		long startTime = System.currentTimeMillis();
		try {

			File file = new File("code.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);

			System.out.println("program " + programName + ";");
			bw.write("program " + programName + ";");
			bw.newLine();

			System.out.println(VariablesFrame.varString);
			bw.write("var");
			bw.newLine();
			bw.write("" + VariablesFrame.varString + "");
			bw.newLine();
			this.shapeConnectors = shapeConnectors;
			this.shapeLoopConnectors = shapeLoopConnectors;
			this.shapeLists = shapeLists;
			// for (Connector cnnConnector : shapeConnectors) {
			// System.out.println("parentidx: " + cnnConnector.getParentIdx());
			// System.out.println("childidx: " + cnnConnector.getChildIdx());
			// System.out.println("loop parent idx: "
			// + cnnConnector.getLoopParentIdx());
			// System.out.println("loop child idx: "
			// + cnnConnector.getLoopChildIdx());
			// System.out.println("ifparentidx: " +
			// cnnConnector.getIfParentIdx());
			// System.out.println("ifchildidx: " +
			// cnnConnector.getIfChildIdx());
			// System.out.println("helpercon trueparentidx: "
			// + cnnConnector.getHelperConnectorTrueParentIdx());
			// System.out.println("helpercon truechildidx: "
			// + cnnConnector.getHelperConnectorTrueChildIdx());
			// System.out.println("helpercon falseparentidx: "
			// + cnnConnector.getHelperConnectorFalseParentIdx());
			// System.out.println("helpercon falsechildidx: "
			// + cnnConnector.getHelperConnectorFalseChildIdx());
			// }
			// for (Connector cnnConnector : shapeLoopConnectors) {
			// System.out.println("parentidx: " + cnnConnector.getParentIdx());
			// System.out.println("childidx: " + cnnConnector.getChildIdx());
			// System.out.println("loop parent idx: "
			// + cnnConnector.getLoopParentIdx());
			// System.out.println("loop child idx: "
			// + cnnConnector.getLoopChildIdx());
			// System.out.println("ifparentidx: " +
			// cnnConnector.getIfParentIdx());
			// System.out.println("ifchildidx: " +
			// cnnConnector.getIfChildIdx());
			// System.out.println("helpercon trueparentidx: "
			// + cnnConnector.getHelperConnectorTrueParentIdx());
			// System.out.println("helpercon truechildidx: "
			// + cnnConnector.getHelperConnectorTrueChildIdx());
			// System.out.println("helpercon falseparentidx: "
			// + cnnConnector.getHelperConnectorFalseParentIdx());
			// System.out.println("helpercon falsechildidx: "
			// + cnnConnector.getHelperConnectorFalseChildIdx());
			// }
			for (Connector connector : shapeConnectors) {

				int idxParent = connector.getParentIdx();

				if (idxParent == idxFor && isInFor) {
					System.out.println("  end;");
					bw.write("  end;");
					bw.newLine();
					isInFor = false;
					idxFor = 0;

				}
				if (idxParent == idxWhile && isInWhile) {
					System.out.println("  end;");
					bw.write("  end;");
					bw.newLine();
					isInWhile = false;
					idxWhile = 0;

				}
				panel = shapeLists.get(idxParent);
				if (panel.getClass().toString().equals("class bitirme.Start")) {
					System.out.println("begin");
					bw.write("begin");
					bw.newLine();
				}

				int idxChild = connector.getChildIdx();
				if (idxChild != idxInTrue && idxChild != idxInFalse) {
					panel = shapeLists.get(idxChild);

					switch (panel.getClass().toString()) {
					case "class bitirme.Input":
						InputGenerate inputGenerate = new InputGenerate(
								(Input) panel);
						System.out.println(" "
								+ inputGenerate.getInputCommand());
						bw.write(" " + inputGenerate.getInputCommand());
						bw.newLine();
						break;
					case "class bitirme.Output":
						OutputGenerate outputGenerate = new OutputGenerate(
								(Output) panel);
						System.out.println(" "
								+ outputGenerate.getOutputCommand());
						bw.write(" " + outputGenerate.getOutputCommand());
						bw.newLine();
						break;
					case "class bitirme.Variables":
						VariableGenerate variableGenerate = new VariableGenerate(
								(Variables) panel);
						System.out.println(" "
								+ variableGenerate.getVariableCommand());
						bw.write(" " + variableGenerate.getVariableCommand());
						bw.newLine();
						break;
					case "class bitirme.Function":
						FunctionGenerate functionGenerate = new FunctionGenerate(
								(Function) panel);
						System.out.println(" "
								+ functionGenerate.getFunctionCommand());
						bw.write(" " + functionGenerate.getFunctionCommand());
						bw.newLine();
						break;
					case "class bitirme.End":
						System.out.println("end");
						bw.write("end");
						bw.newLine();
						break;
					case "class bitirme.If":
						IfGenerate ifGenerate = new IfGenerate((If) panel);
						System.out.println(" " + ifGenerate.getIfCommand());
						bw.write(" " + ifGenerate.getIfCommand());
						bw.newLine();
						for (Connector cnnConnector : shapeLoopConnectors) {
							if (cnnConnector.getIfParentIdx() == idxChild) {
								int idxLoop = cnnConnector.getIfChildIdx();
								panelLoop = shapeLists.get(idxLoop);
								isInTrue = true;
								idxIf = idxChild;
								switch (panelLoop.getClass().toString()) {
								case "class bitirme.Input":
									inputGenerate = new InputGenerate(
											(Input) panelLoop);
									System.out.println(" "
											+ inputGenerate.getInputCommand());
									bw.write(" "
											+ inputGenerate.getInputCommand());
									bw.newLine();
									break;
								case "class bitirme.Output":
									outputGenerate = new OutputGenerate(
											(Output) panelLoop);
									System.out
											.println(" "
													+ outputGenerate
															.getOutputCommand());
									bw.write(" "
											+ outputGenerate.getOutputCommand());
									bw.newLine();

									break;
								case "class bitirme.Variables":
									variableGenerate = new VariableGenerate(
											(Variables) panelLoop);
									System.out.println(" "
											+ variableGenerate
													.getVariableCommand());
									bw.write(" "
											+ variableGenerate
													.getVariableCommand());
									bw.newLine();
									break;
								case "class bitirme.Function":
									functionGenerate = new FunctionGenerate(
											(Function) panelLoop);
									System.out.println(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.write(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.newLine();
									break;

								case "class bitirme.If":
									ifGenerate = new IfGenerate((If) panelLoop);
									System.out.println(" "
											+ ifGenerate.getIfCommand());
									bw.write(" " + ifGenerate.getIfCommand());
									bw.newLine();
									break;

								case "class bitirme.While":
									WhileGenerate whileGenerate = new WhileGenerate(
											(While) panel);
									System.out.println(" "
											+ whileGenerate.getWhileCommand());
									bw.write(" "
											+ whileGenerate.getWhileCommand());
									bw.newLine();

									break;

								default:
									break;

								}
								for (Connector conn : shapeConnectors) {
									if (conn.getParentIdx() == idxLoop) {

										idxInTrue = conn.getChildIdx();
										panelLoop = shapeLists.get(idxInTrue);
										switch (panelLoop.getClass().toString()) {
										case "class bitirme.Input":
											inputGenerate = new InputGenerate(
													(Input) panelLoop);
											System.out.println(" "
													+ inputGenerate
															.getInputCommand());
											bw.write(" "
													+ inputGenerate
															.getInputCommand());
											bw.newLine();

											break;
										case "class bitirme.Output":
											outputGenerate = new OutputGenerate(
													(Output) panelLoop);
											System.out
													.println(" "
															+ outputGenerate
																	.getOutputCommand());
											bw.write(" "
													+ outputGenerate
															.getOutputCommand());
											bw.newLine();

											break;
										case "class bitirme.Variables":
											variableGenerate = new VariableGenerate(
													(Variables) panelLoop);
											System.out
													.println(" "
															+ variableGenerate
																	.getVariableCommand());
											bw.write(" "
													+ variableGenerate
															.getVariableCommand());
											bw.newLine();
											break;
										case "class bitirme.Function":
											functionGenerate = new FunctionGenerate(
													(Function) panelLoop);
											System.out
													.println(" "
															+ functionGenerate
																	.getFunctionCommand());
											bw.write(" "
													+ functionGenerate
															.getFunctionCommand());
											bw.newLine();
											break;

										case "class bitirme.If":
											ifGenerate = new IfGenerate(
													(If) panelLoop);
											System.out
													.println(" "
															+ ifGenerate
																	.getIfCommand());
											bw.write(" "
													+ ifGenerate.getIfCommand());
											bw.newLine();
											break;

										case "class bitirme.While":
											WhileGenerate whileGenerate = new WhileGenerate(
													(While) panel);
											System.out.println(" "
													+ whileGenerate
															.getWhileCommand());
											bw.write(" "
													+ whileGenerate
															.getWhileCommand());
											bw.newLine();
											break;

										default:
											break;

										}

									}
								}

							}
							if (cnnConnector.getIfChildIdx() == idxChild) {
								System.out.println(" end\n else begin");

								bw.write(" end\n else begin");
								bw.newLine();

								int idxLoop = cnnConnector.getIfParentIdx();
								panelLoop = shapeLists.get(idxLoop);
								isInTrue = true;
								idxIf = idxChild;
								switch (panelLoop.getClass().toString()) {
								case "class bitirme.Input":
									inputGenerate = new InputGenerate(
											(Input) panelLoop);
									System.out.println(" "
											+ inputGenerate.getInputCommand());
									bw.write(" "
											+ inputGenerate.getInputCommand());
									bw.newLine();
									break;
								case "class bitirme.Output":
									outputGenerate = new OutputGenerate(
											(Output) panelLoop);
									System.out
											.println(" "
													+ outputGenerate
															.getOutputCommand());
									bw.write(" "
											+ outputGenerate.getOutputCommand());
									bw.newLine();

									break;
								case "class bitirme.Variables":
									variableGenerate = new VariableGenerate(
											(Variables) panelLoop);
									System.out.println(" "
											+ variableGenerate
													.getVariableCommand());
									bw.write(" "
											+ variableGenerate
													.getVariableCommand());
									bw.newLine();
									break;
								case "class bitirme.Function":
									functionGenerate = new FunctionGenerate(
											(Function) panelLoop);
									System.out.println(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.write(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.newLine();
									break;

								case "class bitirme.If":
									ifGenerate = new IfGenerate((If) panelLoop);
									System.out.println(" "
											+ ifGenerate.getIfCommand());
									bw.write(" " + ifGenerate.getIfCommand());
									bw.newLine();
									break;

								case "class bitirme.While":
									WhileGenerate whileGenerate = new WhileGenerate(
											(While) panel);
									System.out.println(" "
											+ whileGenerate.getWhileCommand());
									bw.write(" "
											+ whileGenerate.getWhileCommand());
									bw.newLine();

									break;

								default:
									break;

								}
								for (Connector conn : shapeConnectors) {
									if (conn.getParentIdx() == idxLoop) {

										idxInFalse = conn.getChildIdx();
										panelLoop = shapeLists.get(idxInFalse);
										switch (panelLoop.getClass().toString()) {
										case "class bitirme.Input":
											inputGenerate = new InputGenerate(
													(Input) panelLoop);
											System.out.println(" "
													+ inputGenerate
															.getInputCommand());
											bw.write(" "
													+ inputGenerate
															.getInputCommand());
											bw.newLine();

											break;
										case "class bitirme.Output":
											outputGenerate = new OutputGenerate(
													(Output) panelLoop);
											System.out
													.println(" "
															+ outputGenerate
																	.getOutputCommand());

											bw.write(" "
													+ outputGenerate
															.getOutputCommand());
											bw.newLine();

											break;
										case "class bitirme.Variables":
											variableGenerate = new VariableGenerate(
													(Variables) panelLoop);
											System.out
													.println(" "
															+ variableGenerate
																	.getVariableCommand());
											bw.write(" "
													+ variableGenerate
															.getVariableCommand());
											bw.newLine();
											break;
										case "class bitirme.Function":
											functionGenerate = new FunctionGenerate(
													(Function) panelLoop);
											System.out
													.println(" "
															+ functionGenerate
																	.getFunctionCommand());
											bw.write(" "
													+ functionGenerate
															.getFunctionCommand());
											bw.newLine();
											break;

										case "class bitirme.If":
											ifGenerate = new IfGenerate(
													(If) panelLoop);
											System.out
													.println(" "
															+ ifGenerate
																	.getIfCommand());
											bw.write(" "
													+ ifGenerate.getIfCommand());
											bw.newLine();
											break;

										case "class bitirme.While":
											WhileGenerate whileGenerate = new WhileGenerate(
													(While) panel);
											System.out.println(" "
													+ whileGenerate
															.getWhileCommand());
											bw.write(" "
													+ whileGenerate
															.getWhileCommand());
											bw.newLine();

											break;

										default:
											break;

										}

									}
								}
							}
						}
						System.out.println(" end;");
						bw.write(" end;");
						bw.newLine();
						break;
					case "class bitirme.HelperConnector":
						System.out.println(" end");
						bw.write(" end");
						bw.newLine();

						break;
					case "class bitirme.For":
						// System.out.println("-----------------forLoop");
						ForGenerate forGenerate = new ForGenerate((For) panel);
						System.out.println(" " + forGenerate.getForCommand());
						bw.write(" " + forGenerate.getForCommand());
						bw.newLine();

						for (Connector cnnConnector : shapeLoopConnectors) {
							if (cnnConnector.getLoopParentIdx() == idxChild) {
								int idxLoop = cnnConnector.getLoopChildIdx();
								panelLoop = shapeLists.get(idxLoop);
								isInFor = true;
								idxFor = idxChild;
								switch (panelLoop.getClass().toString()) {
								case "class bitirme.Input":
									inputGenerate = new InputGenerate(
											(Input) panelLoop);
									System.out.println(" "
											+ inputGenerate.getInputCommand());
									bw.write(" "
											+ inputGenerate.getInputCommand());
									bw.newLine();
									break;
								case "class bitirme.Output":
									outputGenerate = new OutputGenerate(
											(Output) panelLoop);
									System.out
											.println(" "
													+ outputGenerate
															.getOutputCommand());
									bw.write(" "
											+ outputGenerate.getOutputCommand());
									bw.newLine();

									break;
								case "class bitirme.Variables":
									variableGenerate = new VariableGenerate(
											(Variables) panelLoop);
									System.out.println(" "
											+ variableGenerate
													.getVariableCommand());
									bw.write(" "
											+ variableGenerate
													.getVariableCommand());
									bw.newLine();
									break;
								case "class bitirme.Function":
									functionGenerate = new FunctionGenerate(
											(Function) panelLoop);
									System.out.println(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.write(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.newLine();
									break;

								case "class bitirme.If":
									ifGenerate = new IfGenerate((If) panelLoop);
									System.out.println(" "
											+ ifGenerate.getIfCommand());
									bw.write(" " + ifGenerate.getIfCommand());
									bw.newLine();
									break;

								case "class bitirme.While":
									WhileGenerate whileGenerate = new WhileGenerate(
											(While) panel);
									System.out.println(" "
											+ whileGenerate.getWhileCommand());
									bw.write(" "
											+ whileGenerate.getWhileCommand());
									bw.newLine();
									break;

								default:
									break;

								}
							}
						}

						// System.out.println("  end");

						break;
					case "class bitirme.While":
						WhileGenerate whileGenerate = new WhileGenerate(
								(While) panel);
						System.out.println(" "
								+ whileGenerate.getWhileCommand());
						bw.write(" " + whileGenerate.getWhileCommand());
						bw.newLine();
						for (Connector cnnConnector : shapeLoopConnectors) {
							if (cnnConnector.getLoopParentIdx() == idxChild) {
								int idxLoop = cnnConnector.getLoopChildIdx();
								panelLoop = shapeLists.get(idxLoop);
								isInWhile = true;
								idxWhile = idxChild;
								switch (panelLoop.getClass().toString()) {
								case "class bitirme.Input":
									inputGenerate = new InputGenerate(
											(Input) panelLoop);
									System.out.println(" "
											+ inputGenerate.getInputCommand());
									bw.write(" "
											+ inputGenerate.getInputCommand());
									bw.newLine();
									break;
								case "class bitirme.Output":
									outputGenerate = new OutputGenerate(
											(Output) panelLoop);
									System.out
											.println(" "
													+ outputGenerate
															.getOutputCommand());
									bw.write(" "
											+ outputGenerate.getOutputCommand());
									bw.newLine();

									break;
								case "class bitirme.Variables":
									variableGenerate = new VariableGenerate(
											(Variables) panelLoop);
									System.out.println(" "
											+ variableGenerate
													.getVariableCommand());
									bw.write(" "
											+ variableGenerate
													.getVariableCommand());
									bw.newLine();
									break;
								case "class bitirme.Function":
									functionGenerate = new FunctionGenerate(
											(Function) panelLoop);
									System.out.println(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.write(" "
											+ functionGenerate
													.getFunctionCommand());
									bw.newLine();
									break;

								case "class bitirme.If":
									ifGenerate = new IfGenerate((If) panelLoop);
									System.out.println(" "
											+ ifGenerate.getIfCommand());
									bw.write(" " + ifGenerate.getIfCommand());
									bw.newLine();
									break;
								//
								// case "class bitirme.While":
								// WhileGenerate whileGenerate = new
								// WhileGenerate(
								// (While) panel);
								// System.out.println(" "
								// + whileGenerate.getWhileCommand());
								//
								// break;

								default:
									break;

								}
							}
						}

						break;

					default:
						break;

					}
				}
			}
			bw.close();
			Runtime.getRuntime().exec("notepad code.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("exec time  " + (endTime - startTime));
	}
}

// System.out.println("-------------------parent : "
// + connector.getParentIdx() + " child : "
// + connector.getChildIdx());
//
// int idxParent = connector.getParentIdx();
// panel = shapeLists.get(idxParent);
//
// switch (panel.getClass().toString()) {
// case "class bitirme.Input":
// InputGenerate inputGenerate = new InputGenerate((Input) panel);
// System.out.println("" + inputGenerate.getInputCommand());
// break;
// case "class bitirme.Output":
// OutputGenerate outputGenerate = new OutputGenerate(
// (Output) panel);
// System.out.println("" + outputGenerate.getOutputCommand());
// break;
// case "class bitirme.Variables":
// VariableGenerate variableGenerate = new VariableGenerate(
// (Variables) panel);
// System.out.println("" + variableGenerate.getVariableCommand());
// break;
// case "class bitirme.Function":
// FunctionGenerate functionGenerate = new FunctionGenerate(
// (Function) panel);
// System.out.println("" + functionGenerate.getFunctionCommand());
// break;
//
// default:
// break;
// }

// if (panel.getClass().toString().equals("class bitirme.For")) {
// System.out.println("forLoop");
// for (Connector cnnConnector : shapeLoopConnectors) {
// System.out.println("-------------------parent : "
// + cnnConnector.getLoopParentIdx() + " child : "
// + cnnConnector.getLoopChildIdx());
// }
// System.out.println("endforloop");
// }
