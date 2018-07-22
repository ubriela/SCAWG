package org.geocrowd;

public class GowallaConstants {

	public static int MIN_TIME = 290;
	
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
}
