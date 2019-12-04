/**
 * @author Tabassum Fabiha | tf2478
 *
 * This class holds all the choices the user will make about how they want their datafile
 * to be selected.
 */
public class TSVFilter {
	/**
	 * @param inWhichFile
	 */
	private TSVFilter(WhichFile inWhichFile) {
		fileName = inWhichFile.fileName;
		
		selectName = inWhichFile.selectName;
		selectOptionString = inWhichFile.selectOptionString;
		selectOptionLong = inWhichFile.selectOptionLong;
		
		terminateName = inWhichFile.terminateName;
		observation = inWhichFile.observation;
	}
	
	/**
	 * @override
	 * @return the information on all options picked by the user
	 */
	public String toString() {
		String retString = "TSVFilter\n" +
							"fileName: "+fileName+"\n";
		
		if (selectName.length() > 0) {
			retString += "selectName: "+selectName+"\n";
		
			if (selectOptionString.length() > 0)
				retString += "selectOptionString: "+selectOptionString+"\n";
			else
				retString += "selectOptionLong: "+selectOptionLong+"\n";
		}
		
		if (terminateName.length() > 0)
			retString += "terminateName: "+terminateName+"\n";
		retString += "observation: "+observation+"\n";
		
		return retString;
	}
	
	/**
	 * Inner class that helps build an instance of the outer class.
	 * 
	 * 
	 */
	public static class WhichFile {
		
		/**
		 * @param inFileName name of datafile
		 * 
		 * Sets up variables to baseline requirements.
		 */
		public WhichFile(String inFileName) {
			fileName = inFileName;
			selectName = "";
			selectOptionString = "";
			terminateName = "";
			observation = TerminalObservation.NONE;
		}
		
		/**
		 * @param inName column to select on
		 * @param inOptionString value of the column to select on
		 * @return reference to the class
		 */
		public WhichFile select(String inName, String inOptionString) {
			selectName = inName;
			selectOptionString = inOptionString;
			return this;
		}
		
		/**
		 * @param inName column to select on
		 * @param inOptionLong value of the column to select on
		 * @return reference to the class
		 */
		public WhichFile select(String inName, long inOptionLong) {
			selectName = inName;
			selectOptionLong = inOptionLong;
			return this;
		}
		
		/**
		 * @param inName column to observe
		 * @param inObservation what observation to observe
		 * @return reference to the class
		 */
		public WhichFile terminate(String inName, TerminalObservation inObservation) {
			terminateName = inName;
			observation = inObservation;
			return this;
		}
		
		/**
		 * @return new instance of the TSVFilter class given the filters specified
		 */
		public TSVFilter done() {
			return new TSVFilter(this);
		}
		
		private String fileName;
		
		private String selectName;
		private String selectOptionString;
		private long selectOptionLong;
		
		private String terminateName;
		private TerminalObservation observation;
	}
	
	public final String fileName;
	
	public final String selectName;
	public final String selectOptionString;
	public final long selectOptionLong;
	
	public final String terminateName;
	public final TerminalObservation observation;
}