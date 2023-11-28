package a2_1901040240;

/**
 * @overview A class contains a single operation displayReport.
 */
public class PCReport {
	/**
	 * @effects return and display the text-based tabular report on objs
	 */
	public static String displayReport(PC[] objs) {
		StringBuilder strB = new StringBuilder();

		for (int i = 0; i < 99; i++) {
			strB.append("-");
		}
		strB.append("\n");

		for (int i = 0; i < 43; i++) {
			strB.append(" ");
		}
		strB.append("PCPROG REPORT\n");

		for (int i = 0; i < 99; i++) {
			strB.append("-");
		}
		strB.append("\n");

		int count = 1;
		for (PC pc : objs) {
			strB.append(String.format("%3.3s %20s %6.6s %15s %s\n", count, pc.getModel(), pc.getYear(),
					pc.getManufacturer(), pc.getComps().getElements()));
			count++;
		}

		for (int i = 0; i < 99; i++) {
			strB.append("-");
		}

		System.out.println(strB.toString());
		return strB.toString();
	}
}
