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
package org.geocrowd.common.entropy;

// TODO: Auto-generated Javadoc
/**
 * The Class EntropyRecord.
 * 
 * @author Leyla & Hien To
 * 
 *         location entropy for each grid cell
 */
public class EntropyRecord {

	/** The coord. */
	private Coord coord;
	
	/** The entropy. */
	private double entropy;

	/** The user count. */
	private int userCount; // number of people chosen as workers; starts with 0
							// and is density as max

	/**
	 * Instantiates a new entropy record.
	 */
	public EntropyRecord() {
	}

	/**
	 * Instantiates a new entropy record.
	 * 
	 * @param d
	 *            the d
	 * @param coord
	 *            the coord
	 */
	public EntropyRecord(double d, Coord coord) {
		entropy = d;
		this.coord = coord;
		userCount = 0;
	}

	/**
	 * Gets the coord.
	 * 
	 * @return the coord
	 */
	public Coord getCoord() {
		return coord;
	}

	/**
	 * Gets the entropy.
	 * 
	 * @return the entropy
	 */
	public double getEntropy() {
		return entropy;
	}

	/**
	 * Gets the worker no.
	 * 
	 * @return the worker no
	 */
	public int getUserCount() {
		return userCount;
	}

	/**
	 * Inc worker no.
	 */
	public void incUserCount() {
		userCount++;
	}

	/**
	 * Sets the coord.
	 * 
	 * @param coord
	 *            the new coord
	 */
	public void setCoord(Coord coord) {
		this.coord = coord;
	}

	/**
	 * Sets the entropy.
	 * 
	 * @param d
	 *            the new entropy
	 */
	public void setEntropy(int d) {
		entropy = d;
	}
}