package bitirme;

public class OutputGenerate {
	Output shapeOutput;
	String outputCommand;

	public OutputGenerate(Output shapeOutput) {
		// TODO Auto-generated constructor stub
		this.shapeOutput = shapeOutput;

		String outputDetail = shapeOutput.getOutputDetail();
		// eger output ii�nde de��ken varsa writeln i�eri�i de�i�ir bunun
		// kontrolunu yap
		outputCommand = "WriteLn('" + outputDetail + "');";
	}

	public String getOutputCommand() {
		return outputCommand;
	}
}