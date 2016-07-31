package bitirme;

public class OutputGenerate {
	Output shapeOutput;
	String outputCommand;

	public OutputGenerate(Output shapeOutput) {
		// TODO Auto-generated constructor stub
		this.shapeOutput = shapeOutput;

		String outputDetail = shapeOutput.getOutputDetail();
		// eger output iiçnde deðþken varsa writeln içeriði deðiþir bunun
		// kontrolunu yap
		outputCommand = "WriteLn('" + outputDetail + "');";
	}

	public String getOutputCommand() {
		return outputCommand;
	}
}