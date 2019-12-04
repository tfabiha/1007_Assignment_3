/**
 * @author Tabassum Fabiha | tf2478
 * 
 * The following has been tested:
 * 	Opening a file that exists
 * 	Opening a file that does not exist
 * 	The first two lines aligning
 * 	The first two lines not aligning
 * 	Records aligning to the headers specified
 * 	Records not aligning to the headers specified
 * 	Selecting a specific String in a column specified as a String
 * 	Selecting a specific String in a column specified as a long
 * 	Selecting a specific long in a column specified as a long
 *  Terminating on a column that does not exist
 *  Terminating without selecting a specific value in a column
 *  Selecting a value in a column without terminating
 *  Terminating and selecting a value in a column
 *  Sorting
 *  	15, 16, 15 				returns false
 *  	15, 15, 15 				returns true
 *  	15, 16, 17 				returns true
 *  	17, 16, 15 				returns true
 *  	14, 15, 15, 16, 17, 17 	returns true
 *  	14, 15, 15, 16, 17, 9 	returns false
 *  
 *  	The only difference between sorting between longs and Strings is the comparison
 *  	and the variables referred to. Therefore the String sorting comparison also holds.
 *  	
 *  Sameness
 *  	15, 15, 15				returns true
 *  	15, 16, 17				returns false
 *  	15, 16, 16, 16, 16		returns false
 *  
 *  	The only difference between sameness between longs and Strings is the comparison
 *  	and the variables referred to. Therefore the String sorting sameness also holds.
 *  
 *  Obtaining counts of a column when asked
 *  Obtaining minimum values of a column when asked
 *  Obtaining maximum values of a column when asked
 *  If no minimum or maximum values, NONE is output
 */
public class Runner {
	public static void main(String[] args) {
		TSVFilter myTSVFilter = new TSVFilter
				.WhichFile("hi.tsv")
				.select("age", 2)
				.terminate("age", TerminalObservation.COUNT)
				.done();
		
		System.out.println(myTSVFilter);
		new TSVPipeline(myTSVFilter).doit();
	}
}