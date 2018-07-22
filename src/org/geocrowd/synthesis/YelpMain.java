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
package org.geocrowd.synthesis;



// TODO: Auto-generated Javadoc
/**
 * The Class Execute.
 * 
 * @author dkh
 */
public class YelpMain {

    /**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
    public static void main(String[] args) {
        // TODO code application logic here
//        ProcessDataSet.Curtail_Review_File();
//
        YelpProcessor.Access_Business();
//        YelpProcessor.Access_User();
        YelpProcessor.Access_Review();
        
        
//        
//
//        YelpProcessor.saveBusiness_Task();
//        YelpProcessor.saveUser_Worker();
        
//        ProcessDataSet.saveWorkersMCD("dataset/real/yelp/yelp_mcd.txt");
//        YelpProcessor.saveTaskWorkers();

//        ProcessDataSet.saveBoundary();

//        ProcessDataSet.saveLocationDensity(ProcessDataSet
//                .computeLocationDensity());
//        ProcessDataSet.save_Statistic();

//        YelpProcessor.split_Worker_by_time3();
        
        
//        ProcessDataSet.process_TI();

    }
}