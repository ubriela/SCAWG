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

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Cell.
 * 
 * @author Leyla
 */
public class Cell {
	
	/** The task list. */
	private ArrayList<Integer> taskList;
	
	/** The density. */
	private int density;

	/**
	 * Instantiates a new cell.
	 */
	public Cell() {
		taskList = new ArrayList<Integer>();
	}

	/**
	 * Instantiates a new cell.
	 * 
	 * @param d
	 *            the d
	 */
	public Cell(int d) {
		taskList = new ArrayList<Integer>();
		density = d;
	}

	/**
	 * Adds the task.
	 * 
	 * @param t
	 *            the t
	 */
	public void addTask(Integer t) {
		taskList.add(t);
	}

	/**
	 * Gets the density.
	 * 
	 * @return the density
	 */
	public int getDensity() {
		return density;
	}

	/**
	 * Gets the task list.
	 * 
	 * @return the task list
	 */
	public ArrayList getTaskList() {
		return taskList;
	}

	/**
	 * Sets the density.
	 * 
	 * @param d
	 *            the new density
	 */
	public void setDensity(int d) {
		density = d;
	}

}