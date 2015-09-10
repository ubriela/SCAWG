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

import org.geocrowd.datasets.params.GeocrowdConstants;

// TODO: Auto-generated Javadoc
/**
 * Each worker has a working region and a set of expertise.
 * 
 * @author Leyla
 */
public class ExpertWorker extends RegionWorker {

	/** The expertise. */
	private HashSet<Integer> expertiseSet = new HashSet<>();

	// init expertise with one value
	/**
	 * Instantiates a new specialized worker.
	 * 
	 * @param value
	 *            the value
	 */
	public ExpertWorker(int value) {
		if (!expertiseSet.contains(value))
			expertiseSet.add(value);
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
	public ExpertWorker(String id, double lt, double ln, int maxT,
			WorkingRegion mbr) {
		super(id, lt, ln, maxT, mbr);
	}

	public ExpertWorker() {
		// TODO Auto-generated constructor stub
		super();
	}

	public ExpertWorker(double lat, double lng) {
		super(lat, lng);
	}

	/**
	 * Adds the expertise.
	 * 
	 * @param exp
	 *            the exp
	 */
	public void addExpertise(int exp) {
		expertiseSet.add(exp);
	}

	public HashSet<Integer> getExpertiseSet() {
		return expertiseSet;
	}

	/**
	 * 
	 * @return a list of expertise
	 */
	public String toStringExpertise() {
		StringBuffer sb = new StringBuffer();
		Iterator<Integer> it = expertiseSet.iterator();
		while (it.hasNext()) {
			sb.append(it.next());
			sb.append(',');
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * To str.
	 * 
	 * @return the string
	 */
	@Override
	public String toString() {
		if (expertiseSet != null)
			return super.toString() + GeocrowdConstants.delimiter_dataset + "[" + toStringExpertise() + "]";
		else
			return super.toString();
	}
}
