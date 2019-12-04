/**
 * @author Tabassum Fabiha | tf2478
 * 
 * This method handles the methods required to obtain the statics for the TerminalObservation
 * such as COUNT, MIN, MAX, ISSAME, ISSORTED. 
 * 
 * An algorithm is used to compute if the values obtained are all sorted or not. We start
 * off with a sumOfDifferences of 0. For each value that we obtain we find the difference
 * between it and the next value and multiply it to the sumOfDifferences. If the arrangement
 * has been sorted so far and the the product is less than 0 then we know that we are no 
 * longer sorted. Say that the sumOfDifferences is negative or 0. That means that the values have
 * been increasing as more records have been added. If the next value was also in increasing
 * order, then the difference between that and the previous value would also be negative or
 * 0. Same goes for decreasing order. The sumOfDifferences in that case would be positive or 0
 * and the difference between this current value and the next value would be positive or
 * 0. When you multiply the sumOfDifferences and the newDifference in either case, if you
 * you should get a non-negative number. If you get a negative number then that means you
 * are no longer sorted.
 */
public class Observer {
	/**
	 * @param inString new long value
	 * @return if all the values so far have been sorted
	 * 
	 * If we have previously used this method, then we know all the values so far have
	 * been sorted only if they have been sortedSoFar and inString is the same as lastString.
	 * Else if we have not previously used this method, then we know that this is the only
	 * value so far and so sortedSoFar must be true.
	 */
	public boolean isSorted(String inString) {
		if (hasPrevUsed) {
			long newDifference = lastString.compareTo(inString);
			boolean sorted = (sumOfDifferences * newDifference) >= 0 && sortedSoFar;
			
			if (sorted)
				sumOfDifferences += newDifference;
			
			lastString = inString;
			sortedSoFar = sorted;
			return sorted;
		}
		else  {
			lastString = inString;
			hasPrevUsed = true;
			return true;
		}
	}
	
	/**
	 * @param inLong new long value
	 * @return if all the values so far have been sorted
	 * 
	 * If we have previously used this method, then we know all the values so far have
	 * been sorted only if they have been sortedSoFar and inLong is the same as lastLong.
	 * Else if we have not previously used this method, then we know that this is the only
	 * value so far and so sortedSoFar must be true.
	 */
	public boolean isSorted(long inLong) {
		if (hasPrevUsed) {
			long newDifference = lastLong - inLong;
			boolean sorted = (sumOfDifferences * newDifference) >= 0 && sortedSoFar;
			
			if (sorted)
				sumOfDifferences += newDifference;
			
			lastLong = inLong;
			sortedSoFar = sorted;
			return sorted;
		}
		else {
			lastLong = inLong;
			hasPrevUsed = true;
			return true;
		}
	}
	
	/**
	 * @param inString new String value
	 * @return if all the values so far have been the same
	 * 
	 * If we have previously used this method, then we know all the values so far 
	 * are the same only if they have been sameSoFar and inString is the same as lastString.
	 * Else if we have not previously used this method, then we know that this is the only
	 * value so far and so sameSoFar must be true.
	 */
	public boolean isSame(String inString) {
		boolean isSame;
		if (hasPrevUsed)
			isSame = lastString.compareTo(inString) == 0 && sameSoFar;	
		else {
			isSame = true;
			hasPrevUsed = true;
		}
		lastString = inString;
		sameSoFar = isSame;
		return isSame;
	}
	
	/**
	 * @param inLong new long value
	 * @return if all the values so far have been the same
	 * 
	 * If we have previously used this method, then we know all the values so far 
	 * are the same only if they have been sameSoFar and inLong is the same as lastLong.
	 * Else if we have not previously used this method, then we know that this is the only
	 * value so far and so sameSoFar must be true.
	 */
	public boolean isSame(long inLong) {
		boolean isSame;
		if (hasPrevUsed)
			isSame = lastLong == inLong && sameSoFar;	
		else {
			isSame = true;
			hasPrevUsed = true;
		}
		lastLong = inLong;
		sameSoFar = isSame;
		return isSame;
	}
	
	/**
	 * @param inString new String value
	 * 
	 * Compares inString to the max String we have so far. if inString is larger than our
	 * recorded max String, then it will replace the recorded max String. If there is
	 * no recorded max String, we assume this is the first time we are obtaining a record.
	 */
	public void compareToMax(String inString) {
		if (hasPrevUsed) {
			if (inString.compareTo(maxString) > 0)
				maxString = inString;
		}
		else {
			hasPrevUsed = true;
			maxString = inString;
		}
	}
	
	/**
	 * @param inString new String value
	 * 
	 * Compares inString to the min String we have so far. if inString is smaller than our
	 * recorded min String, then it will replace the recorded min String. If there is
	 * no recorded min String, we assume this is the first time we are obtaining a record.
	 */
	public void compareToMin(String inString) {
		if (hasPrevUsed) {
			if (inString.compareTo(minString) < 0)
				minString = inString;
		}
		else {
			hasPrevUsed = true;
			minString = inString;
		}
	}
	
	/**
	 * @param inLong new long value
	 * 
	 * Compares inLong to the max long we have so far. if inLong is larger than our
	 * recorded max value, then it will replace the recorded max value. If there is
	 * no recorded max value, we assume this is the first time we are obtaining a record.
	 */
	public void compareToMax(long inLong) {
		if (hasPrevUsed) {
			if (inLong - maxLong > 0)
				maxLong = inLong;
		}
		else {
			hasPrevUsed = true;
			maxLong = inLong;
		}
	}
	
	/**
	 * @param inLong new long value
	 * 
	 * Compares inLong to the min long we have so far. if inLong is smaller than our
	 * recorded min value, then it will replace the recorded min value. If there is
	 * no recorded min value, we assume this is the first time we are obtaining a record.
	 */
	public void compareToMin(long inLong) {
		if (hasPrevUsed) {
			if (inLong - minLong < 0)
				minLong = inLong;
		}
		else {
			hasPrevUsed = true;
			minLong = inLong;
		}
	}
	
	/**
	 * increases the count by one
	 */
	public void incrementCount() {
		count += 1;
	}
	
	/**
	 * @return the count of times a field has been recorded
	 */
	public int getCount() {
		return count;
	}
	
	/**
	 * @return if so far the values passed have been the same
	 */
	public boolean isSameSoFar() {
		return sameSoFar;
	}
	
	/**
	 * @return if so far the values passed have been sorted
	 */
	public boolean isSortedSoFar() {
		return sortedSoFar;
	}
	
	/**
	 * @return string with max value found so far
	 */
	public String getMaxString() {
		return maxString;
	}
	
	/**
	 * @return string with min value found so far
	 */
	public String getMinString() {
		return minString;
	}
	
	/**
	 * @return max long found so far
	 */
	public Long getMaxLong() {
		return maxLong;
	}
	
	/**
	 * @return min long found so far
	 */
	public Long getMinLong() {
		return minLong;
	}
	
	private String lastString;
	private long lastLong;
	
	private long sumOfDifferences = 0;
	private int count = 0;
	
	private boolean hasPrevUsed = false;
	private boolean sameSoFar = true;
	private boolean sortedSoFar = true;
	
	private String maxString;
	private String minString;
	private Long maxLong;
	private Long minLong;
}