/**
 * *****************************************************************************
 * @ Year 2013 This is the source code of the following papers.
 * 
* 1) Geocrowd: A Server-Assigned Crowdsourcing Framework. Hien To, Leyla
 * Kazemi, Cyrus Shahabi.
 * 
*
 * Please contact the author Hien To, ubriela@gmail.com if you have any
 * question.
 * 
* Contributors: Hien To - initial implementation
******************************************************************************
 */
package org.geocrowd.common.crowd;

import java.util.Random;

import org.geocrowd.datasets.params.GeocrowdConstants;

// TODO: Auto-generated Javadoc
/**
 * The Class SensingTask.
 *
 * @author HT186011
 *
 * Each task has a region (e.g., circle whose center is task location), in which
 * any worker within the region can perform the task
 */
public class SensingTask extends GenericTask {

    /**
     * The radius.
     */
    private double radius;		// of the task region

    /**
     * Instantiates a new sensing task.
     *
     * @param radius the radius
     */
    public SensingTask(double radius) {
        this.radius = radius;
    }

    /**
     * Instantiates a new sensing task.
     *
     * @param lt the lt
     * @param ln the ln
     * @param entry the entry
     * @param ent the ent
     */
    public SensingTask(double lt, double ln, int entry, double ent) {
        super(lt, ln, entry, ent);
    }

    public SensingTask() {
		// TODO Auto-generated constructor stub
    	super();
	}

	public SensingTask(double lat, double lng) {
		super(lat, lng);
	}

	/**
     * Gets the radius.
     *
     * @return the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * Sets the radius.
     *
     * @param radius the new radius
     */
    public void setRadius(double radius) {
        this.radius = radius;
    }

	@Override
	public String toString() {
		return super.toString() + "," + getRadius();
	}
}
