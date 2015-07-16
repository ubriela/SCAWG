/*******************************************************************************
* @ Year 2013
* This is the source code of the following papers. 
* 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla Kazemi, Cyrus Shahabi.
* 
* 
* Please contact the author Hien To, ubriela@gmail.com if you have any question.
*
* Contributors:
* Hien To - initial implementation
*******************************************************************************/
package org.geocrowd.datasets.dtype;

// TODO: Auto-generated Javadoc
/**
 * The Class Range.
 */
public class Range {
	
	/** The end. */
	private double start, end;

	/**
	 * Instantiates a new range.
	 * 
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 */
	public Range(double start, double end) {
		super();
		this.start = start;
		this.end = end;
	}

	/**
	 * Debug.
	 */
	public void debug() {
		// TODO Auto-generated method stub
		System.out.println("select lon from mcdonaldoned where lon >=" + start
				+ " and lon <=" + end + ";");
	}

	/**
	 * Delta.
	 * 
	 * @return the double
	 */
	public double delta() {
		return end - start;
	}
	
	/**
	 * Gets the end.
	 * 
	 * @return the end
	 */
	public double getEnd() {
		return end;
	}

	/**
	 * Gets the start.
	 * 
	 * @return the start
	 */
	public double getStart() {
		return start;
	}

}
