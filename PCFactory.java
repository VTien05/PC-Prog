package a2_1901040240;

/**
 * @overview factory method used to creat PCs and is a singleton.
 */
public class PCFactory {
	/** attribute: instance */
	private static PCFactory instance;

	/** constructor */
	private PCFactory() {

	}

	/** getter for atrribute instance */
	public static PCFactory getInstance() {
		if (instance == null) {
			instance = new PCFactory();
		}
		return instance;
	}

	/**
	 * @effects return new PC:<model,year,manufacturer,comps>
	 */
	public static PC createPC(String model, int year, String manufacturer, Set<String>comps) {
		getInstance();
		return new PC(model, year , manufacturer, comps);
	}
}
