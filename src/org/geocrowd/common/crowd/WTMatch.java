package org.geocrowd.common.crowd;

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

// TODO: Auto-generated Javadoc
/**
 * The Class MatchPair.
 * 
 * @author HT186011
 * 
 *         A workerid is matched with a taskid
 */
public class WTMatch {
	
	/** The w. */
	int w;
	
	/** The t. */
	int t;
	
	/**
	 * Instantiates a new match pair.
	 * 
	 * @param w
	 *            the w
	 * @param t
	 *            the t
	 */
	public WTMatch(int w, int t) {
		super();
		this.w = w;
		this.t = t;
	}
	
	/**
	 * Gets the t.
	 * 
	 * @return the t
	 */
	public int getT() {
		return t;
	}
	
	/**
	 * Gets the w.
	 * 
	 * @return the w
	 */
	public int getW() {
		return w;
	}
}
