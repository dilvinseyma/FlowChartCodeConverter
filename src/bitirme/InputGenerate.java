package bitirme;

public class InputGenerate {
	Input shapeInput;
	String inputCommand;

	public InputGenerate(Input shapeInput) {
		// TODO Auto-generated constructor stub
		this.shapeInput = shapeInput;

		String inputDetail = shapeInput.getInputDetail();
		inputCommand = "ReadLn(" + inputDetail + ");";
	}

	public String getInputCommand() {
		return inputCommand;
	}
}
