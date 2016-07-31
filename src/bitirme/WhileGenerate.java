package bitirme;

public class WhileGenerate {

	While shapeWhile;
	String whileCommand = "";

	public WhileGenerate(While shapeWhile) {
		// TODO Auto-generated constructor stub
		this.shapeWhile = shapeWhile;

		String whileDetail = shapeWhile.getCondition();
		whileCommand = " while " + whileDetail + " do begin";

	}

	public String getWhileCommand() {
		return whileCommand;
	}
}