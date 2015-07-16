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
 * The Class Coord.
 * 
 * @author Leyla
 * 
 *         Coordinate of a grid cell
 */
public class Coord {

	/** The row id. */
	private int rowId;
	
	/** The col id. */
	private int colId;

	/**
	 * Instantiates a new coord.
	 * 
	 * @param r
	 *            the r
	 * @param c
	 *            the c
	 */
	public Coord(int r, int c) {
		rowId = r;
		colId = c;
	}

	/**
	 * Gets the col id.
	 * 
	 * @return the col id
	 */
	public int getColId() {
		return colId;
	}

	/**
	 * Gets the row id.
	 * 
	 * @return the row id
	 */
	public int getRowId() {
		return rowId;
	}

}
