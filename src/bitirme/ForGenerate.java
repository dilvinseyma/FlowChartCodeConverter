package bitirme;

public class ForGenerate {

	For shapeFor;
	String forCommand = "";

	public ForGenerate(For shapeFor) {
		// TODO Auto-generated constructor stub
		this.shapeFor = shapeFor;
		// for i := 1 to n do begin toplam := toplam + i * i; end;
		String forDetail = shapeFor.getFirst();
		String[] parts = forDetail.split("<-");
		String var = parts[0];
		String val = parts[1];
		forCommand = " for " + var + ":=" + val + " to " + shapeFor.getThird()
				+ " do begin";

		// for (int i = 0; i < parts.length; i++) {
		//
		// String part = parts[i];
		// String[] for = part.split("=");
		// String part1 = for[0];
		// String part2 = for[1];
		// for (i == parts.length - 1) {
		// forCommand = forCommand + part1 + ":=" + part2 + ";";
		// } else {
		// forCommand = forCommand + part1 + ":=" + part2
		// + ";\n ";
		// }
		// }

	}

	public String getForCommand() {
		return forCommand;
	}
}