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
package org.geocrowd;

// TODO: Auto-generated Javadoc
/**
 * The Class Constants.
 */
public class GeocrowdConstants {

	public static int s = 1; // the value of the exponent characterizing the zipf
							// distribution
	public static final double MU = 1.0; // maximum utility
	public static String UTILITY_FUNCTION = "zipf";
	public static int ZIPF_STEPS = 100;

	/** The Task no. */
	public static int TaskNo = 1000; // 200; // number of tasks

	public static int WorkerNo = 0; // minimum worker count
	public static int MIN_TIME = 290;
	// public static int W = 0; // current workload

	/** The time instance. */
	public static int TIME_INSTANCE = 280;
	// public static final int MAX_TIME_INSTANCE = 500;

	/** The Task duration. */
	public static int TaskDuration = 5;// 20000; //duration of all tasks before
										// they expire are fixed to 1000ms
	/** The diameter. */
	public static double radius = 5.0; // task circle diameter
	/** enable random k */
	public static boolean IS_RANDOM_K = false;

	/** required number of responses */
	public static int K = 1;

	/**
	 * Only choose worker covers at least k tasks.
	 */
	public static int M = 4;

	// ------------------------------------------------------------

	// shared parameters
	/** The Task type no. */
	public static double TaskTypeNo = 0; // number of task types/expertise

	/** The Max tasks per worker. */
	public static int MaxTasksPerWorker = 20; // maximum # of tasks that a
	// worker want to get

	/** The Max range perc. */
	public static double MaxRangePerc = 0.05; // maximum range of an mbr is 5%
	// of the entire x or y
	// dimensionF

	// small dataset
	/** The small task file name prefix. */
	public static String smallTaskFileNamePrefix = "dataset/small/maxcover/task/tasks";

	/** The small worker file path. */
	public static String smallWorkerFilePath = "dataset/small/worker/locations";

	/** The small worker file name prefix. */
	public static String smallWorkerFileNamePrefix = "dataset/small/maxcover/worker/workers";

	/** The small resolution. */
	public static int smallResolution = 1;

	// real dataset yelp
	/** The yelp resolution. */
	public static int yelpResolution = 1000;

	/** The yelp entropy file name. */
	public static String yelpEntropyFileName = "dataset/real/yelp/yelp_entropy.txt";

	/** The yelp task file name prefix. */
	public static String yelpTaskFileNamePrefix = "dataset/real/yelp/task/yelp_tasks";

	/** The yelp worker file name prefix. */
	public static String yelpWorkerFileNamePrefix = "dataset/real/yelp/worker/yelp_workers";

	// real dataset gowalla
	/** The gowalla resolution. */
	public static int gowallaResolution = 50000; // 0.00002 is approximately
													// 30x30 metres
	/** The gowalla file name. */
	public static String gowallaFileName = "dataset/real/gowalla/gowalla_totalCheckins.txt";

	/** The gowalla file name_ ca. */
	public static String gowallaFileName_CA = "dataset/real/gowalla/gowalla_CA";

	/** The gowalla file name_ sa. */
	public static String gowallaFileName_SA = "dataset/real/gowalla/gowalla_totalCheckins_SA.txt";

	/** The gowalla entropy file name. */
	public static String gowallaEntropyFileName = "dataset/real/gowalla/gowalla_entropy.txt";

	/** The gowalla task file name prefix. */
	public static String gowallaTaskFileNamePrefix = "dataset/real/gowalla/task/gowalla_tasks";

	/** The gowalla worker file name prefix. */
	public static String gowallaWorkerFileNamePrefix = "dataset/real/gowalla/worker/gowalla_workers";

	public static String gowallaFileName_CA_loc = "dataset/real/gowalla/gowalla_CA.dat";

	/** Foursquare */
	public static String foursquareTaskFileNamePrefix = "dataset/real/foursquare/task/foursquare_tasks";

	public static String foursquareWorkerFileNamePrefix = "dataset/real/foursquare/worker/foursquare_workers";

	// synthetic dataset
	/** The skewed resolution. */
	public static int skewedResolution = 500;

	/** The uni resolution. */
	public static int uniResolution = 100;

	/** The worker file path. */
	public static String inputWorkerFilePath = "./res/dataset/worker/workers";

	/** The matlab task file path. */
	public static String inputTaskFilePath = "./res/dataset/task/tasks";
}