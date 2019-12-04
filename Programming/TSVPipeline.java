import java.io.*;

/**
 * @author Tabassum Fabiha | tf2478
 * 
 * This class goes through the process of looking through the given file in the filter,
 * finding the specific records that the user wants to select and outputting it to an
 * output file, and computing some statistics on it if specified.
 * 
 * Notes on Corner Cases:
 * 
 * If the user wishes to select a column that does not exist, then the output file
 * consists of only the headers, since no records with that column will exist.
 * 
 * If the user wishes to terminate on a column that does not exist, then their request
 * will be ignored and no statistic will be delivered at the end.
 * 
 * If a record does not align with the header, then that record will be skipped over
 * for both the selection and terminate processes.
 * 
 * If the user asks for a min or a max and none exist, then the report is NONE.
 * 
 * If the datafile or the headers in the datafile could not properly be fetched then
 * none of the records are checked and the doit() method automatically exits.
 */
public class TSVPipeline {
	
	/**
	 * @param inFilter an instance of the filter class
	 * 
	 * This constructor sets myFilter to the parameter passed, and then tries to create
	 * a new file to write to.
	 */
	public TSVPipeline(TSVFilter inFilter) {
		myFilter = inFilter;
		
		try {
			File dir = new File(dirPath);
			dir.mkdirs();
		
			File file = new File(dir, "output.tsv");
			file.createNewFile();

			writingFile = new FileWriter(file);
		}
		catch (IOException e) {
			
		}
	}
	
	/**
	 * This method goes through the process of doing what's required. It first accesses
	 * the file and obtains the header. Then it iterates through the file record by record
	 * and acts on each record. It ends by closing the files that were being read and
	 * written in and presenting observations if there are any.
	 */
	public void doit() {
		try {
			if ( accessFile() ) {
				getHeader();
				
				String recordLine = readingFile.readLine();
		    	while (recordLine != null) {
		    		actOnRecord( recordLine );
		    		recordLine = readingFile.readLine();
		    	}
		    	
		    	readingFile.close();
		    	writingFile.close();
		    	
		    	presentObservations();
			}
		}
		catch (Exception e) {
			
		}
	}
	
	/**
	 * @return if we have been able to access the file
	 * 
	 * This file attempts to open the file given in myFilter and tells the user if it was
	 * opened or not.
	 */
	private boolean accessFile() {
		try {
			File dir = new File(dirPath);
			dir.mkdirs();
			File file = new File(dir, myFilter.fileName);
			
		    FileReader reader = new FileReader(file);
	    	readingFile = new BufferedReader(reader);
	    	
	    	System.out.println("Obtained access to the file.");
	    	return true;
		}
		catch (Exception e) {
			System.out.println("Could not file the file.");
			return false;
		}
	}
	
	/**
	 * @throws Exception if the first two lines are not in agreement
	 * 
	 * This method obtains the first two lines and manipulates them to record them
	 * into headerNames and headerTypes. If the two lines do not align then the user
	 * is told that and an exception is thrown, otherwise the user is told that the
	 * two lines aligned.
	 */
	private void getHeader() throws Exception {
		try {
			String headerNamesLine;
			String headerTypesLine;
			
			// read the first two lines of the input tsv file
			try {
				headerNamesLine = readingFile.readLine();
				headerTypesLine = readingFile.readLine();
				System.out.println("Obtained the first two lines");
			}
			catch (Exception e) {
				System.out.println("Could not obtain the first two lines");
				throw new Exception();
			}
			
			headerNames = headerNamesLine.split("\t");
				
			String[] headerTypeStringified = headerTypesLine.split("\t");
			headerTypes = new Type[headerTypeStringified.length];
			
			for (int i = 0; i < headerTypeStringified.length; i++) {
				if (headerTypeStringified[i].equals("long"))
					headerTypes[i] = Type.LONG;
				else
					headerTypes[i] = Type.STRING;
			}
			
			if (headerTypes.length != headerNames.length) {
				System.out.println("The first two lines are not in agreement.");
				throw new Exception();
			}
			System.out.println("The first two lines are in agreement.");
	
			writingFile.write(headerNamesLine + "\n");
			writingFile.write(headerTypesLine + "\n");
			
		}
		catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * @param recordLine a record from the file
	 * 
	 * This method acts on a record by obtaining the different elements it contains
	 * and matching it to the different headerNames and their types. If all the
	 * elements do not properly align then the method exits. Else, it checks to see if
	 * this record has been selected based on it's values. If it has been selected,
	 * the record is written to the output file and its checked to see if any statistics
	 * can be obtained from it.
	 */
	private void actOnRecord(String recordLine) {
		String[] records = recordLine.split("\t");
		
		if (records.length != headerTypes.length)
			return;
		
		// check to make sure the values all match corresponding types
		for (int i = 0; i < headerTypes.length; i++) {
			if (headerTypes[i] == Type.LONG) {
				try {
					Long.parseLong( records[i] );
				}
				catch (Exception e) {
					return;
				}
			}
		}
		
		// see if this record is selected
		if (isSelected(records)) {
			try {
				writingFile.write(recordLine + "\n");
				
				terminate(records);
			} 
			catch (IOException e) {}
		}
	}
	
	/**
	 * @param records all the elements in a record
	 * @return if the line provided has been selected
	 * 
	 * This method first checks if myFilter specified a specific header to select.
	 * If it hasn't then we automatically assume that this record has been selected.
	 * If it has then we find the index of the selected name. If the selected name is
	 * not contained in our header then we say that this record has not been selected.
	 * If the selected name is contained in our header then we return if this record
	 * contains the value we are looking for.
	 */
	private boolean isSelected(String[] records) {
		if (myFilter.selectName == "")
			return true;
		
		int index = -1;
		for (int i = 0; i < headerNames.length; i++) {
			if (headerNames[i].equals( myFilter.selectName )) {
				index = i;
				break;
			}
		}
		
		if (index < 0)
			return false;
		
		try {
			if (headerTypes[index] == Type.LONG && Long.parseLong(records[index]) == myFilter.selectOptionLong )
				return true;
			else if (records[index].equals( myFilter.selectOptionString ))
				return true;
		}
		catch (Exception e) {}
		
		return false;
	}
	
	/**
	 * @param records all the elements in a selected record
	 * 
	 * This method checks the terminalObservation specified by myFilter and calls
	 * the appropriate Observer method to record the information. If there is no 
	 * observation to record then the method does nothing.
	 */
	private void terminate(String[] records) {
		
		int index = -1;
		for (int i = 0; i < headerNames.length; i++) {
			if (headerNames[i].equals( myFilter.terminateName )) {
				index = i;
				break;
			}
		}
		
		if (myFilter.observation == TerminalObservation.NONE || index < 0)
			return;
		
		if (myFilter.observation == TerminalObservation.COUNT )
			observer.incrementCount();
		
		else if (myFilter.observation == TerminalObservation.ISSAME && 
				 observer.isSameSoFar()) {
			
			if (headerTypes[index] == Type.LONG)
				observer.isSame( Long.parseLong( records[index] ) );
			else
				observer.isSame( records[index] );
		}
		else if (myFilter.observation == TerminalObservation.ISSORTED && 
				 observer.isSortedSoFar()) {
			
			if (headerTypes[index] == Type.LONG)
				observer.isSorted( Long.parseLong( records[index] ) );
			else
				observer.isSorted( records[index] );
		}
		else if (myFilter.observation == TerminalObservation.MAX) {
			
			if (headerTypes[index] == Type.LONG)
				observer.compareToMax( Long.parseLong( records[index] ) );
			else
				observer.compareToMax( records[index] );
		}
		else if (myFilter.observation == TerminalObservation.MIN) {
			
			if (headerTypes[index] == Type.LONG)
				observer.compareToMin( Long.parseLong( records[index] ) );
			else
				observer.compareToMin( records[index] );
		}
		

	}
	
	/**
	 * This method prints out to the user the observation that was requested.
	 */
	private void presentObservations() {
		
		int index = -1;
		for (int i = 0; i < headerNames.length; i++) {
			if (headerNames[i].equals( myFilter.terminateName )) {
				index = i;
				break;
			}
		}
		
		if (myFilter.observation == TerminalObservation.NONE || index < 0)
			return;
		
		if (myFilter.observation == TerminalObservation.COUNT)
			System.out.println("COUNT: " + observer.getCount() );
		
		else if (myFilter.observation == TerminalObservation.ISSAME)
			System.out.println("ISSAME: " + observer.isSameSoFar());
		
		else if (myFilter.observation == TerminalObservation.ISSORTED)
			System.out.println("ISSORTED: " + observer.isSortedSoFar());
		
		else if (myFilter.observation == TerminalObservation.MAX) {
			
			if (headerTypes[index] == Type.LONG) {
				if (observer.getMaxLong() == null)
					System.out.println("MAX: NONE");
				else
					System.out.println("MAX: " + observer.getMaxLong());
			}
			else {
				if (observer.getMaxString() == null)
					System.out.println("MAX: NONE");
				else
					System.out.println("MAX: " + observer.getMaxString());
			}
		}
		
		else if (myFilter.observation == TerminalObservation.MIN) {
			
			if (headerTypes[index] == Type.LONG) {
				if (observer.getMaxLong() == null)
					System.out.println("MIN: NONE");
				else
					System.out.println("MIN: " + observer.getMinLong());
			}
			else {
				if (observer.getMaxString() == null)
					System.out.println("MIN: NONE");
				else
					System.out.println("MIN: " + observer.getMinString());
			}
		}
	}
	
	private TSVFilter myFilter;
	private String[] headerNames;
	private Type[] headerTypes;
	
	private BufferedReader readingFile;
	private FileWriter writingFile;
	
	private Observer observer = new Observer();
	
	private String dirPath = "./";
}