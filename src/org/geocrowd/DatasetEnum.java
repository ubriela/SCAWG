/*******************************************************************************
 * @ Year 2013
 * This is the source code of the following papers.
 *
 * Hien To, Mohammad Asghari, Dingxiong Deng, Cyrus Shahabi. SCAWG: A Toolbox for Generating Synthetic Workload for
 * Spatial Crowdsourcing. In proceeding of International Workshop on Benchmarks for Ubiquitous Crowdsourcing:
 * Metrics, Methodologies, and Datasets (CROWDBENCH 2016), Sydney, Australia, March 14-18, 2016.
 * 
 * Please contact the author Hien To, ubriela@gmail.com if you have any question.
 *
 * Contributors:
 * Hien To - initial implementation
 *******************************************************************************/
package org.geocrowd;

/**
 * List of datasets supported.
 */
public enum DatasetEnum {

	/**
	 * Real-world datasets.
	 */
	GOWALLA, 		// http://snap.stanford.edu/data/loc-gowalla.html
	YELP, 			// https://www.yelp.com/dataset/challenge
	FOURSQUARE, 	// https://foursquare.com/
	MPING,			// https://mping.nssl.noaa.gov/
	SCALED_IRAIN, 	// Scaling small iRain dataset to larger dataset.

	/**
	 * Pure synthetic datasets.
	 */
	SKEWED,
	UNIFORM,

	FAKE_SMALL_TEST // The small dataset used to test the correctness of the program.
}