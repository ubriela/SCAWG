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


// TODO: Auto-generated Javadoc
/**
 * The Class SpecializedTask.
 * 
 * @author Hien To
 * 
 *         each task has a task type, e.g., rating a dish at a restaurant or
 *         taking a high quality picture
 */
public class ExpertTask extends GenericTask {

	/** The task category, e.g., taking pictures, rating restaurants. */
	private int category;

	/**
	 * Instantiates a new specialized task.
	 */
	public ExpertTask() {
		super();
	}

	/**
	 * Instantiates a new specialized task.
	 * 
	 * @param lt
	 *            the lt
	 * @param ln
	 *            the ln
	 * @param entry
	 *            the entry
	 * @param dens
	 *            the dens
	 * @param taskType
	 *            the task type
	 */
	public ExpertTask(double lt, double ln, int entry, double dens, int taskType) {
		super(lt, ln, entry, dens);
		category = taskType;
	}

	public ExpertTask(double lat, double lng) {
		super(lat, lng);
	}

	/**
	 * Gets the task type.
	 * 
	 * @return the task type
	 */
	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	@Override
	public String toString() {
		return super.toString() + ","
				+ getCategory();
	}
	
}
