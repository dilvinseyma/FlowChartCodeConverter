package bitirme;

public class VariableGenerate {

	Variables shapeVariable;
	String variableCommand = "";

	public VariableGenerate(Variables shapeVariable) {
		// TODO Auto-generated constructor stub
		this.shapeVariable = shapeVariable;

		String variableDetail = shapeVariable.getVariables();

		String[] parts = variableDetail.split(",");
		for (int i = 0; i < parts.length; i++) {

			String part = parts[i];
			String[] variable = part.split("=");
			String part1 = variable[0];
			String part2 = variable[1];
			if (i == parts.length - 1) {
				variableCommand = variableCommand + part1 + ":=" + part2 + ";";
			} else {
				variableCommand = variableCommand + part1 + ":=" + part2
						+ ";\n ";
			}
		}

	}

	public String getVariableCommand() {
		return variableCommand;
	}
}