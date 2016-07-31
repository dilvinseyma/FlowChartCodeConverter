package bitirme;

public class IfGenerate {

	If shapeIf;
	String ifCommand = "";

	public IfGenerate(If shapeIf) {
		// TODO Auto-generated constructor stub
		this.shapeIf = shapeIf;

		String ifDetail = shapeIf.getCondition();
		ifCommand = " if " + ifDetail + " then begin";

	}

	public String getIfCommand() {
		return ifCommand;
	}
}