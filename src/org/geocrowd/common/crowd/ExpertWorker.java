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
package org.geocrowd.common.crowd;


import java.util.HashSet;
import java.util.Iterator;

// TODO: Auto-generated Javadoc
/**
 * Each worker has a working region and a set of expertise.
 * 
 * @author Leyla
 */
public class ExpertWorker extends RegionWorker {

	/** The expertise. */
	private HashSet<Integer> expertise = new HashSet<>();
	
	// init expertise with one value
	/**
	 * Instantiates a new specialized worker.
	 * 
	 * @param value
	 *            the value
	 */
	public ExpertWorker(int value) {
		if (!expertise.contains(value))
			expertise.add(value);
	}
	
	
	/**
	 * Instantiates a new specialized worker.
	 * 
	 * @param id
	 *            the id
	 * @param lt
	 *            the lt
	 * @param ln
	 *            the ln
	 * @param maxT
	 *            the max t
	 * @param mbr
	 *            the mbr
	 */
	public ExpertWorker(String id, double lt, double ln, int maxT, WorkingRegion mbr) {
		super(id, lt, ln, maxT, mbr);
	}

	public ExpertWorker() {
		// TODO Auto-generated constructor stub
		super();
	}


	/**
	 * Adds the expertise.
	 * 
	 * @param exp
	 *            the exp
	 */
	public void addExpertise(int exp) {
		expertise.add(exp);
	}

	/**
	 * Checks if is exact match.
	 * 
	 * @param t
	 *            the t
	 * @return true, if is exact match
	 */
	public boolean isExactMatch(ExpertTask t) {
		if (expertise.contains(t.getCategory()))
			return true;
		return false;
	}

	/**
	 * To str.
	 * 
	 * @return the string
	 */
	public String toStr() {
		String str = getId() + "," + getLat() + "," + getLng() + ","
				+ getCapacity() + ",[" + getMbr().getMinLat() + "," + getMbr().getMinLng()
				+ "," + getMbr().getMaxLat() + "," + getMbr().getMaxLng() + "],[" + toString() + "]";
		return str;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Iterator<Integer> it = expertise.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			sb.append(',');
		}
		return sb.substring(0, sb.length() - 1);
	}
}
