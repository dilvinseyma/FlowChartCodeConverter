package bitirme;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class VariablesFrame extends JFrame implements ActionListener {

	String[] variablesStrings = { "Integer", "Boolean", "String", "Double",
			"Float", "Char" };
	JComboBox cmbVariables = new JComboBox(variablesStrings);
	String variableType = "", variableName = "";
	JTextArea variablesTextArea;
	JComboBox cBox;

	int count = 0;
	private JPanel contentPane;
	private JTable table;

	static String varString = " ";
	String intVarsString = "", doubleVarsString = "", floatVarsString = "",
			charVarsString = "", stringVarsString = "", booleanVarsString = "";

	Object title[] = new Object[] { "deðiþkenin ismi", "deðiþkenin tipi" };
	DefaultTableModel dm = new DefaultTableModel();

	Vector<String> data = new Vector<String>();
	Object[] row = new Object[2];
	ArrayList<String> varNames = new ArrayList<>();
	ArrayList<String> varTypes = new ArrayList<>();

	public VariablesFrame() {

		this.setLocationRelativeTo(null);
		this.setSize(500, 570);
		this.setTitle("Variables");

		JLabel nameLabel = new JLabel("Deðiþken adý : ");
		variablesTextArea = new JTextArea(1, 10);
		cmbVariables.setSelectedIndex(1);
		cmbVariables.setPreferredSize(new Dimension(120, 20));
		cmbVariables.setBounds(10, 10, 80, 80);
		cmbVariables.addActionListener(this);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 28, 396, 173);
		contentPane.add(scrollPane);

		table = new JTable();
		scrollPane.setViewportView(table);

		dm.setColumnIdentifiers(title);
		table.setModel(dm);

		JButton save = new JButton("  Kaydet ");
		save.setBounds(30, 30, 100, 50);
		save.setActionCommand("kaydet");
		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (!(variableName.equals("") && variableType.equals(""))) {
					row[0] = variableName;
					row[1] = variableType;
					dm.addRow(row);
				}

			}
		});
		JButton okay = new JButton("  Tamam ");
		okay.setBounds(30, 60, 100, 50);
		okay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				setVisible(false);
				for (int i = 0; i < varTypes.size(); i++) {
					if (varTypes.get(i).equals("Integer")) {
						intVarsString = intVarsString + varNames.get(i) + ",";
					}
					if (varTypes.get(i).equals("String")) {
						stringVarsString = stringVarsString + varNames.get(i)
								+ ",";
					}
					if (varTypes.get(i).equals("Boolean")) {
						booleanVarsString = booleanVarsString + varNames.get(i)
								+ ",";
					}
					if (varTypes.get(i).equals("Char")) {
						charVarsString = charVarsString + varNames.get(i) + ",";
					}
					if (varTypes.get(i).equals("Float")) {
						floatVarsString = floatVarsString + varNames.get(i)
								+ ",";
					}
					if (varTypes.get(i).equals("Double")) {
						doubleVarsString = doubleVarsString + varNames.get(i)
								+ ",";
					}

				}
				if (!(intVarsString.equals(""))) {
					varString = varString
							+ intVarsString.substring(0,
									intVarsString.length() - 1)
							+ " : integer;\n  ";
				}
				if (!(stringVarsString.equals(""))) {
					varString = varString
							+ stringVarsString.substring(0,
									stringVarsString.length() - 1)
							+ " : string;\n  ";
				}
				if (!(booleanVarsString.equals(""))) {
					varString = varString
							+ booleanVarsString.substring(0,
									booleanVarsString.length() - 1)
							+ " : boolean;\n  ";
				}
				if (!(doubleVarsString.equals(""))) {
					varString = varString
							+ doubleVarsString.substring(0,
									doubleVarsString.length() - 1)
							+ " : double;\n  ";
				}
				if (!(floatVarsString.equals(""))) {
					varString = varString
							+ floatVarsString.substring(0,
									floatVarsString.length() - 1)
							+ " : double;\n  ";
				}
				if (!(charVarsString.equals(""))) {
					varString = varString
							+ charVarsString.substring(0,
									charVarsString.length() - 1)
							+ " : char;\n  ";
				}
				// System.out.print(varString);
			}
		});

		add(nameLabel);
		add(variablesTextArea);
		add(cmbVariables);
		add(save);
		add(okay);

		setVisible(true);
		setLayout(new FlowLayout());
	}

	public void setVarString(String varString) {
		this.varString = varString;
	}

	public String getVarString() {
		return varString;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == cmbVariables) {
			cBox = (JComboBox) e.getSource();
			variableType = (String) cBox.getSelectedItem();
			variableName = variablesTextArea.getText();
			System.out.println("tip : " + variableType + " isim "
					+ variableName);

			varTypes.add(variableType);
			varNames.add(variableName);

		}

	}
}
