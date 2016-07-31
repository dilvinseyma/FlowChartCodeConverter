package bitirme;

public class FunctionGenerate {
	Function shapeFunction;
	String functionCommand;

	public FunctionGenerate(Function shapeFunction) {
		// TODO Auto-generated constructor stub
		this.shapeFunction = shapeFunction;

		String functionDetail = shapeFunction.getFuncName();
		functionCommand = "" + functionDetail + ";";
	}

	public String getFunctionCommand() {
		return functionCommand;
	}
}
