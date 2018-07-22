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


/**
 *
 * @author dkh
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Random;

import org.geocrowd.common.workertask.GenericWorker;
import org.geocrowd.common.utils.Utils;
import org.geocrowd.YelpConstants;
import org.geocrowd.synthetic.Parser;
import org.geocrowd.dtype.Point;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessDataSet.
 */
public class YelpProcessor {

    /**
	 * The min lat.
	 * 
	 */
    public static double minLat = Double.MAX_VALUE;
    
    /** The max lat. */
    public static double maxLat = (-1) * Double.MAX_VALUE;
    
    /** The min long. */
    public static double minLong = Double.MAX_VALUE;
    
    /** The max long. */
    public static double maxLong = (-1) * Double.MAX_VALUE;
    
    /** The Business_ location. */
    static Hashtable<String, Hashtable<String, Double>> Business_Location = new Hashtable<>();
    
    /** The Review. */
    static Hashtable<String, Hashtable<Integer, String>> Review = new Hashtable<>();
    
    /** The Review_ date. */
    static Hashtable<Integer, Hashtable<String, Hashtable<Integer, String>>> Review_Date = new Hashtable<>();
    
    /** The Business_ categories. */
    static Hashtable<String, Hashtable<Integer, String>> Business_Categories = new Hashtable<>();
    
    /** The User_ categories. */
    static Hashtable<String, ArrayList> User_Categories = new Hashtable<>();
    
    /** The User_ review count. */
    static Hashtable<String, Long> User_ReviewCount = new Hashtable<>();
    
    /** The parser. */
    static JSONParser parser = new JSONParser();
    // int count = 0;
    /** The Expertise. */
    static ArrayList Expertise = new ArrayList();
    
    /** The type. */
    static String type = "none";
    
    /** The total_expertise_user. */
    static int total_expertise_user = 0;

    /**
	 * Access_ business.
	 */
    public static void Access_Business() {
        try {
            FileReader f = new FileReader(YelpConstants.business);
            BufferedReader in = new BufferedReader(f);
            while (in.ready()) {

                String line = in.readLine();
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String bus_id = jsonObject.get("business_id").toString();
                double lng = (double) jsonObject.get("longitude");
                double lat = (double) jsonObject.get("latitude");
                if (lat < minLat) {
                    minLat = lat;
                }
                if (lat > maxLat) {
                    maxLat = lat;
                }
                if (lng < minLong) {
                    minLong = lng;
                }
                if (lng > maxLong) {
                    maxLong = lng;
                }
                Hashtable<String, Double> longlat = new Hashtable<>();
                longlat.put("lng", lng);
                longlat.put("lat", lat);
                Business_Location.put(bus_id, longlat);

                JSONArray categories = (JSONArray) jsonObject.get("categories");

                if (categories != null && categories.size() > 0) {
                    Hashtable<Integer, String> temp_business = new Hashtable<>();
                    temp_business.put(0, categories.get(0).toString());
                    Business_Categories.put(bus_id, temp_business);
                    if (!Expertise.contains(categories.get(0))) {
                        Expertise.add(categories.get(0));
                    }
                    if (categories.size() > 1) {
                        for (int i = 1; i < categories.size(); i++) {
                            Business_Categories.get(bus_id).put(i,
                                    categories.get(i).toString());
                            if (!Expertise.contains(categories.get(i))) {
                                Expertise.add(categories.get(i));
                            }
                        }
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
	 * Access_ review.
	 */
    public static void Access_Review() {
    	StringBuffer sb = new StringBuffer();
    	int file_idx = 0;
    	int line_idx = 0;
        try {
            FileReader f = new FileReader(YelpConstants.review);
            BufferedReader in = new BufferedReader(f);
            while (in.ready()) {

                String line = in.readLine();
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String user = jsonObject.get("user_id").toString();
                String business = jsonObject.get("business_id").toString();
                int time_instance = DayIt(jsonObject.get("date").toString());
                
                Double lat = Business_Location.get(business).get("lat");
                Double lng = Business_Location.get(business).get("lng");
                if (line_idx++ % 50000 == 0)
                	System.out.println(line_idx);
                sb.append(user + "\t" + business + "\t" + lat + "\t" + lng + "\n");

                if (line_idx % 500000 == 0) {
                	Utils.writefile2(sb.toString(), YelpConstants.yelp_checkin + file_idx, true);
                	file_idx += 1;
                	sb.delete(0, sb.length());
                }
                if (line_idx > 0)
                	continue;
                if (Review.keySet().contains(user)) {
                    int t = Review.get(user).size();
                    Review.get(user).put(t, business);
                } else {
                    Hashtable<Integer, String> temp_business = new Hashtable<>();
                    temp_business.put(0, business);
                    Review.put(user, temp_business);
                }
                // SPLIT WORKER BY TIME INSTANCE
                if (Review_Date.containsKey(time_instance)) {
                    if (Review_Date.get(time_instance).containsKey(user)) {
                        int t = Review_Date.get(time_instance).get(user).size();
                        Review_Date.get(time_instance).get(user).put(t, business);
                    } else {
                        Hashtable<Integer, String> temp_business = new Hashtable<>();
                        temp_business.put(0, business);
                        Review_Date.get(time_instance).put(user, temp_business);

                    }
                } else {
                    Hashtable<Integer, String> temp_business = new Hashtable<>();
                    temp_business.put(0, business);
                    Hashtable<String, Hashtable<Integer, String>> temp_user = new Hashtable<>();
                    temp_user.put(user, temp_business);
                    Review_Date.put(time_instance, temp_user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println("Total user reviewed: " + Review.size());

    }
    
    

    /**
	 * Access_ user.
	 */
    public static void Access_User() {
        try {
            FileReader f = new FileReader(YelpConstants.user);
            BufferedReader in = new BufferedReader(f);
            while (in.ready()) {

                String line = in.readLine();
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String user = jsonObject.get("user_id").toString();
                long review = (long) jsonObject.get("review_count");
                if (review > YelpConstants.MaxReview) {
                    review = YelpConstants.MaxReview;
                }
                User_ReviewCount.put(user, review);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    /**
	 * Compute location density.
	 * 
	 * @return the hashtable
	 */
    public static Hashtable<Integer, Hashtable<Integer, Integer>> computeLocationDensity() {
        Hashtable<Integer, Hashtable<Integer, Integer>> Density;
        Density = new Hashtable<>();
        Iterator Business = Business_Location.keySet().iterator();
        while (Business.hasNext()) {
            String BusinessID = Business.next().toString();
            Double lat = Business_Location.get(BusinessID).get("lat");
            Double lng = Business_Location.get(BusinessID).get("lng");
            int row = getRowIdx(lat);
            int col = getColIdx(lng);
            if (Density.containsKey(row)) {
                if (Density.get(row).containsKey(col)) {
                    Density.get(row).put(col, Density.get(row).get(col) + 1);
                } else {
                    Density.get(row).put(col, 1);
                }
            } else {
                Hashtable<Integer, Integer> rows = new Hashtable<Integer, Integer>();
                rows.put(col, 1);
                Density.put(row, rows);
            }

        }

        return Density;

    }

    /**
	 * Curtail_ review_ file.
	 */
    public static void Curtail_Review_File() {
        Hashtable<String, Hashtable<Integer, String>> Review = new Hashtable<>();
        JSONParser parser = new JSONParser();
        int c = 0;

        try {
            FileReader f = new FileReader(YelpConstants.review);
            BufferedReader in = new BufferedReader(f);
            StringBuffer sb = new StringBuffer();
            while (in.ready()) {
                if (c % 1000 == 0) {
                    System.out.println("Done. # of reviews is:" + c);
                }
                String line = in.readLine();
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;
                String user = jsonObject.get("user_id").toString();
                String business = jsonObject.get("business_id").toString();
                String datereview = jsonObject.get("date").toString();
                JSONObject obj_towrite = new JSONObject();
                obj_towrite.put("business_id", business);
                obj_towrite.put("user_id", user);
                obj_towrite.put("date", datereview);
                sb.append(obj_towrite.toJSONString());
                sb.append('\n');
                c++;
            }
            Utils.writefile2(sb.toString(), YelpConstants.curtail_review, false);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
	 * Date it.
	 * 
	 * @param t
	 *            the t
	 * @return the int
	 */
    public static int DateIt(String t) {
        int to_return = 0;
        String[] temp = t.split("-");
        int[] number = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            number[i] = Integer.parseInt(temp[i]);
        }
        
        switch (number[0]) {
            case 2005:
                to_return = 0;
                break;
            default:
                to_return = (int) (YelpConstants.TimeInstance * (number[2] + number[1]*30 + (number[0] - 2006) * 356)/((2015 - 2006) * 356));
                break;
        }
        return to_return;
    }
    
    public static int DayIt(String t) {
        int to_return = 0;
        String[] temp = t.split("-");
        int[] number = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            number[i] = Integer.parseInt(temp[i]);
        }
        
        switch (number[0]) {
            case 2005:
                to_return = 0;
                break;
            default:
                to_return = (int) ((number[2] + number[1]*30 + (number[0] - 2006) * 356));
                break;
        }
        return to_return;
    }

    /**
	 * Gets the col idx.
	 * 
	 * @param lng
	 *            the lng
	 * @return the col idx
	 */
    public static int getColIdx(double lng) {
        return (int) ((lng - minLong) / YelpConstants.realResolution);
    }

    /**
	 * Gets the row idx.
	 * 
	 * @param lat
	 *            the lat
	 * @return the row idx
	 */
    public static int getRowIdx(double lat) {
        return (int) ((lat - minLat) / YelpConstants.realResolution);
    }

    /**
	 * Pre process task.
	 */
    public static void PreProcessTask() {
        JSONParser parser = new JSONParser();
        // int count = 0;
        ArrayList Expertise = new ArrayList();
        String type = "none";
        Expertise.add(type);
        int q = 0;
        int k = 1;
        // int c = 0;

        try {
            FileReader f = new FileReader(YelpConstants.business);
            BufferedReader in = new BufferedReader(f);
            while (in.ready()) {
                if (k == 500) {
                    q++;
                    k = 1;
                }
                k++;

                String line = in.readLine();
                Object obj = parser.parse(line);
                JSONObject jsonObject = (JSONObject) obj;

                // get review count of a business
                long rc = (long) jsonObject.get("review_count");

                double entropy = 0;

                // get business longitude
                double lng = (double) jsonObject.get("longitude");
                // get business lattitude
                double lat = (double) jsonObject.get("latitude");
                // get business categories
                JSONArray categories = (JSONArray) jsonObject.get("categories");
                type = "none";
                Random generator = new Random();
                if (categories.size() > 0) {
                    int i = generator.nextInt(categories.size());
                    type = categories.get(i).toString();
                    if (!Expertise.contains(type)) {
                        Expertise.add(type);
                    }
                }
                // print out business details
                // Utils.writefile(lat + "," + lng + "," + 1 + "," + q + "," +
                // entropy + "," + Expertise.indexOf(type), q);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
    
    /**
	 * Save_ statistic.
	 */
    public static void save_Statistic() {
        int total_expertise = Expertise.size();
        int total_user_review = Review.size();
        Iterator users = Review.keySet().iterator();
        int sum_exp = 0;
        int sum_review = 0;
        while (users.hasNext()) {
            String u_id = (String) users.next();
            sum_exp += User_Categories.get(u_id).size();
            sum_review += Review.get(u_id).size();
        }
        int avg_review = sum_review / total_user_review;
        int avg_exp_per_user = sum_exp / User_Categories.size();
        StringBuilder sb = new StringBuilder();

        sb.append("Total expertise: " + total_expertise);
        sb.append("\nTotal user with expertise: " + total_expertise_user);

        sb.append("\nTotal user reviewed: " + total_user_review);
        sb.append("\nAvg expertise per user: " + avg_exp_per_user);
        sb.append("\nAvg rating per user: " + avg_review);
        sb.append("\nTotal Business: " + Business_Location.size());
        sb.append("\nTotal Task requires Expertise: " + Business_Categories.size());
        Utils.writefile2(sb.toString(), YelpConstants.SaveStatistic, false);


    }
    
    
    /**
	 * Save boundary.
	 */
    public static void saveBoundary() {
        Utils.writefile2(minLat + "," + minLong + "," + maxLat + "," + maxLong,
                YelpConstants.boundary, false);
    }

    /**
	 * Save business_ task.
	 */
    public static void saveBusiness_Task() {
        Hashtable<Integer, Hashtable<Integer, Integer>> Density = computeLocationDensity();
        Iterator businesses = Business_Categories.keySet().iterator();
        int c = 0;
        int time = 0;
        StringBuilder sb = new StringBuilder();
        while (businesses.hasNext()) {
            if (c >= YelpConstants.TaskPerFile) {
                System.out.println("Task instance: " + time);
                Utils.writefile2(sb.toString(), YelpConstants.SaveTask + time
                        + YelpConstants.suffix, false);
                c = 0;
                time++;
                sb.delete(0, sb.length());
            }
            String BusinessID = businesses.next().toString();
            Double lat = Business_Location.get(BusinessID).get("lat");
            Double lng = Business_Location.get(BusinessID).get("lng");
            int row = getRowIdx(lat);
            int col = getColIdx(lng);
            int dens = Density.get(row).get(col);
            Random generator = new Random();
            int chosen = generator.nextInt(Business_Categories.get(BusinessID)
                    .size());
            String TaskType = Business_Categories.get(BusinessID).get(chosen);
            int type_chosen = Expertise.indexOf(TaskType);
            sb.append(lat + "," + lng + "," + time + "," + dens + ","
                    + type_chosen);
            sb.append('\n');
            c++;
        }
    }

    /**
	 * Save location density.
	 * 
	 * @param Density
	 *            the density
	 */
    public static void saveLocationDensity(
            Hashtable<Integer, Hashtable<Integer, Integer>> Density) {

        StringBuffer sb = new StringBuffer();
        Iterator row_it = Density.keySet().iterator();
        while (row_it.hasNext()) {
            int row = (Integer) row_it.next();
            Iterator col_it = Density.get(row).keySet().iterator();
            while (col_it.hasNext()) {
                int col = (Integer) col_it.next();
                sb.append(row + "," + col + "," + Density.get(row).get(col));
                sb.append('\n');
            }
        }
        Utils.writefile2(sb.toString(), YelpConstants.entropy, false);
    }

    /**
	 * Save task workers.
	 */
    public static void saveTaskWorkers() {
    	// Tasks
        Iterator Business = Business_Location.keySet().iterator();
        StringBuffer sb = new StringBuffer();
		
        while (Business.hasNext()) {
            String BusinessID = Business.next().toString();
            Double lat = Business_Location.get(BusinessID).get("lat");
            Double lng = Business_Location.get(BusinessID).get("lng");
            sb.append(lat + "\t" + lng + "\n");
        }
        
		FileWriter writer;
		try {
			Path pathToFile = Paths.get(YelpConstants.tasks_loc);
			Files.createDirectories(pathToFile.getParent());
			
			writer = new FileWriter(YelpConstants.tasks_loc);
			BufferedWriter out = new BufferedWriter(writer);
			out = new BufferedWriter(writer);
			out.write(sb.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Workers
		
		
    }

    /**
	 * Save user_ worker.
	 */
    public static void saveUser_Worker() {

        Random gen = new Random();
        int c = 0;
        int file_i = 0;
        Iterator users = Review.keySet().iterator();
        StringBuilder sb = new StringBuilder();

        while (users.hasNext()) {
        	String u_id = (String) users.next();
            StringBuilder sb_temp = new StringBuilder();
            if (c >= YelpConstants.WorkerPerFile || !users.hasNext()) {
                System.out.println("Worker instance: " + file_i);
                Utils.writefile2(sb.toString(), YelpConstants.SaveWorker + file_i
                        + YelpConstants.suffix, false);
                c = 0;
                file_i++;
                sb.delete(0, sb.length());
            }

            
            ArrayList tempal = new ArrayList();
            if (!User_Categories.keySet().contains(u_id)) {
                User_Categories.put(u_id, tempal);
            }
            Iterator businesses = Review.get(u_id).keySet().iterator();
            int i = gen.nextInt(Review.get(u_id).size());
            double minLatitude = Double.MAX_VALUE;
            double maxLatitude = (-1) * Double.MAX_VALUE;
            double minLongitude = Double.MAX_VALUE;
            double maxLongitude = (-1) * Double.MAX_VALUE;
            while (businesses.hasNext()) {
                int col = (Integer) businesses.next();
                String x = Review.get(u_id).get(col).toString();
                if (Business_Location.get(x) == null)
                	continue;
                double temp_lat = Business_Location.get(x).get("lat");
                
                double temp_lng = Business_Location.get(
                        Review.get(u_id).get(col).toString()).get("lng");

                if (temp_lat < minLatitude) {
                    minLatitude = temp_lat;
                }
                if (temp_lat > maxLatitude) {
                    maxLatitude = temp_lat;
                }
                if (temp_lng < minLongitude) {
                    minLongitude = temp_lng;
                }
                if (temp_lng > maxLongitude) {
                    maxLongitude = temp_lng;
                }

                // System.out.print (Review.get(u_id).get(col).toString());

                if (Business_Categories.keySet().contains(
                        Review.get(u_id).get(col).toString())) {
                    for (int j = 0; j < Business_Categories.get(
                            Review.get(u_id).get(col).toString()).size(); j++) {
                        String expertise = Business_Categories
                                .get(Review.get(u_id).get(col).toString())
                                .get(j).toString();
                        if (!User_Categories.get(u_id).contains(expertise)) {
                            User_Categories.get(u_id).add(expertise);
                        }
                    }
                }

            }

            double lat = (minLatitude + maxLatitude) / 2;
            double lon = (minLongitude + maxLongitude) / 2;
            sb_temp.append(u_id + "," + lat + "," + lon);

            if (User_ReviewCount.containsKey(u_id)) {
                sb_temp.append("," + User_ReviewCount.get(u_id));
            } else {
                sb_temp.append("," + Review.get(u_id).size());
            }

            sb_temp.append(",[" + minLatitude + "," + minLongitude + ","
                    + maxLatitude + "," + maxLongitude + "]");

            if (User_Categories.get(u_id).size() != 0) {

                sb_temp.append(",[");
                for (int j = 0; j < User_Categories.get(u_id).size(); j++) {
                    if (j > 0 && j < User_Categories.get(u_id).size()) {
                        sb_temp.append(',');
                    }
                    sb_temp.append(String.valueOf(Expertise
                            .indexOf(User_Categories.get(u_id).get(j))));
                }

                sb_temp.append("]\n");

                sb.append(sb_temp);

                c++;
                total_expertise_user++;
            } else {
                // System.out.println(u_id + "-one empty here");
            }
        }
        //System.out.println(c);

    }
  
    /**
	 * Compute mean contribution distance.
	 * 
	 * @param filename
	 *            the filename
	 */
    public static void saveWorkersMCD(String filename) {
    	Iterator users = Review.keySet().iterator();
        StringBuffer sb = new StringBuffer();
        
        while(users.hasNext()) {
        	String u_id = (String) users.next();
            
            Iterator businesses = Review.get(u_id).keySet().iterator();
            ArrayList<Point> points = new ArrayList<Point>();
            while (businesses.hasNext()) {
                int col = (Integer) businesses.next();
                String x = Review.get(u_id).get(col).toString();
                if (Business_Location.get(x) == null)
                	continue;
                double lat = Business_Location.get(x).get("lat");
                double lng = Business_Location.get(
                        Review.get(u_id).get(col).toString()).get("lng");
                points.add(new Point(lat, lng));
            }
            
            // compute MCD
            double mcd = org.geocrowd.common.utils.Utils.MCD(points.get(0), points);
            sb.append(mcd + "\n");
        }
        
		FileWriter writer;
		try {
			Path pathToFile = Paths.get(filename);
			Files.createDirectories(pathToFile.getParent());
			
			writer = new FileWriter(filename);
			BufferedWriter out = new BufferedWriter(writer);
			out.write(sb.toString());
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }

    /**
	 * Split_ worker_by_time.
	 */
    public static void split_Worker_by_time2() {

        Random gen = new Random();
        Iterator time_instance = Review_Date.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (time_instance.hasNext()) {
            sb.delete(0, sb.length());
            int instance = (Integer) time_instance.next();
            //System.out.println(instance);
            Iterator users = Review_Date.get(instance).keySet().iterator();
            while (users.hasNext()) {
                StringBuilder sb_temp = new StringBuilder();
                String u_id = (String) users.next();
                Iterator businesses = Review_Date.get(instance).get(u_id).keySet().iterator();
                double minLatitude = Double.MAX_VALUE;
                double maxLatitude = (-1) * Double.MAX_VALUE;
                double minLongitude = Double.MAX_VALUE;
                double maxLongitude = (-1) * Double.MAX_VALUE;
                while (businesses.hasNext()) {
                    int col = (Integer) businesses.next();
                    //System.out.println(col + "\t" + Review_Date.get(instance).get(u_id).get(col));
                    
                    if (!Business_Location.containsKey(
                            Review_Date.get(instance).get(u_id).get(col))) {
                    	// there might be the case some business in reviews but not in business
                    	System.out.println("This business does not exist in Business_Location: " + Review_Date.get(instance).get(u_id).get(col));
                    	continue;
                    }
                    
                    double temp_lat = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lat");
                    double temp_lng = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lng");

                    if (temp_lat < minLatitude) {
                        minLatitude = temp_lat;
                    }
                    if (temp_lat > maxLatitude) {
                        maxLatitude = temp_lat;
                    }
                    if (temp_lng < minLongitude) {
                        minLongitude = temp_lng;
                    }
                    if (temp_lng > maxLongitude) {
                        maxLongitude = temp_lng;
                    }

                    // System.out.print (Review.get(u_id).get(col).toString());

                    if (Business_Categories.keySet().contains(
                            Review_Date.get(instance).get(u_id).get(col).toString())) {
                        for (int j = 0; j < Business_Categories.get(
                                Review_Date.get(instance).get(u_id).get(col).toString()).size(); j++) {
                            String expertise = Business_Categories
                                    .get(Review_Date.get(instance).get(u_id).get(col).toString())
                                    .get(j).toString();
                            if (!User_Categories.get(u_id).contains(expertise)) {
                                User_Categories.get(u_id).add(expertise);
                            }
                        }
                    }

                }

                double lat = (minLatitude + maxLatitude) / 2;
                double lon = (minLongitude + maxLongitude) / 2;
                sb_temp.append(u_id + "," + lat + "," + lon);


                sb_temp.append("," + Review_Date.get(instance).get(u_id).size());


                sb_temp.append(",[" + minLatitude + "," + minLongitude + ","
                        + maxLatitude + "," + maxLongitude + "]");

                if (User_Categories.get(u_id).size() != 0) {

                    sb_temp.append(",[");
                    for (int j = 0; j < User_Categories.get(u_id).size(); j++) {
                        if (j > 0 && j < User_Categories.get(u_id).size()) {
                            sb_temp.append(',');
                        }
                        sb_temp.append(String.valueOf(Expertise
                                .indexOf(User_Categories.get(u_id).get(j))));
                    }

                    sb_temp.append("]\n");

                    sb.append(sb_temp);

                    //  total_expertise_user++;
                } else {
                    // System.out.println(u_id + "-one empty here");
                }
            }
            Utils.writefile2(sb.toString(), YelpConstants.SplitWorkerByTime + String.format("%04d", instance) + YelpConstants.suffix, false);
        }


    }
    
    /**
	 * Split_ worker_by_time.
	 */
    public static void split_Worker_by_time() {

        Random gen = new Random();
        Iterator time_instance = Review_Date.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (time_instance.hasNext()) {
            sb.delete(0, sb.length());
            int instance = (Integer) time_instance.next();
//            System.out.println(instance);
            Iterator users = Review_Date.get(instance).keySet().iterator();
            while (users.hasNext()) {
                StringBuilder sb_temp = new StringBuilder();
                String u_id = (String) users.next();
                Iterator businesses = Review_Date.get(instance).get(u_id).keySet().iterator();
                double minLatitude = Double.MAX_VALUE;
                double maxLatitude = (-1) * Double.MAX_VALUE;
                double minLongitude = Double.MAX_VALUE;
                double maxLongitude = (-1) * Double.MAX_VALUE;
                while (businesses.hasNext()) {
                    int col = (Integer) businesses.next();
//                    System.out.println(col + "\t" + Review_Date.get(instance).get(u_id).get(col));
                    
                    if (!Business_Location.containsKey(
                            Review_Date.get(instance).get(u_id).get(col))) {
                    	// there might be the case some business in reviews but not in business
                    	System.out.println("This business does not exist in Business_Location: " + Review_Date.get(instance).get(u_id).get(col));
                    	continue;
                    }
                    
//                    if (Business_Location.contains(
//                            Review_Date.get(instance).get(u_id).get(col)))
//                    	continue;	// there might be the case some business in reviews but not in business
                    
                    double temp_lat = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lat");
                    double temp_lng = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lng");

                    if (temp_lat < minLatitude) {
                        minLatitude = temp_lat;
                    }
                    if (temp_lat > maxLatitude) {
                        maxLatitude = temp_lat;
                    }
                    if (temp_lng < minLongitude) {
                        minLongitude = temp_lng;
                    }
                    if (temp_lng > maxLongitude) {
                        maxLongitude = temp_lng;
                    }

                    // System.out.print (Review.get(u_id).get(col).toString());

                    if (Business_Categories.keySet().contains(
                            Review_Date.get(instance).get(u_id).get(col).toString())) {
                        for (int j = 0; j < Business_Categories.get(
                                Review_Date.get(instance).get(u_id).get(col).toString()).size(); j++) {
                            String expertise = Business_Categories
                                    .get(Review_Date.get(instance).get(u_id).get(col).toString())
                                    .get(j).toString();
                            if (!User_Categories.get(u_id).contains(expertise)) {
                                User_Categories.get(u_id).add(expertise);
                            }
                        }
                    }

                }

                double lat = (minLatitude + maxLatitude) / 2;
                double lon = (minLongitude + maxLongitude) / 2;
                sb_temp.append(u_id + "," + lat + "," + lon);


                sb_temp.append("," + Review_Date.get(instance).get(u_id).size());


                sb_temp.append(",[" + minLatitude + "," + minLongitude + ","
                        + maxLatitude + "," + maxLongitude + "]");

                if (User_Categories.get(u_id).size() != 0) {

                    sb_temp.append(",[");
                    for (int j = 0; j < User_Categories.get(u_id).size(); j++) {
                        if (j > 0 && j < User_Categories.get(u_id).size()) {
                            sb_temp.append(',');
                        }
                        sb_temp.append(String.valueOf(Expertise
                                .indexOf(User_Categories.get(u_id).get(j))));
                    }

                    sb_temp.append("]\n");

                    sb.append(sb_temp);

                    //  total_expertise_user++;
                } else {
                    // System.out.println(u_id + "-one empty here");
                }
            }
            Utils.writefile2(sb.toString(), YelpConstants.SplitWorkerByTime + instance + YelpConstants.suffix, false);
        }


    }
    
    /**
	 * Split_ worker_by_time.
	 */
    public static void split_Worker_by_time3() {

        Random gen = new Random();
        Iterator time_instance = Review_Date.keySet().iterator();
        StringBuilder sb = new StringBuilder();
        while (time_instance.hasNext()) {
            sb.delete(0, sb.length());
            int instance = (Integer) time_instance.next();
            
            Iterator users = Review_Date.get(instance).keySet().iterator();
            System.out.println(instance + "\t" + Review_Date.get(instance).keySet().size());
            while (users.hasNext()) {
                StringBuilder sb_temp = new StringBuilder();
                String u_id = (String) users.next();
                Iterator businesses = Review_Date.get(instance).get(u_id).keySet().iterator();
                double minLatitude = Double.MAX_VALUE;
                double maxLatitude = (-1) * Double.MAX_VALUE;
                double minLongitude = Double.MAX_VALUE;
                double maxLongitude = (-1) * Double.MAX_VALUE;
                while (businesses.hasNext()) {
                    int col = (Integer) businesses.next();
//                    System.out.println(col + "\t" + Review_Date.get(instance).get(u_id).get(col));
                    
                    if (!Business_Location.containsKey(
                            Review_Date.get(instance).get(u_id).get(col))) {
                    	// there might be the case some business in reviews but not in business
//                    	System.out.println("This business does not exist in Business_Location: " + Review_Date.get(instance).get(u_id).get(col));
                    	continue;
                    }
                    
//                    if (Business_Location.contains(
//                            Review_Date.get(instance).get(u_id).get(col)))
//                    	continue;	// there might be the case some business in reviews but not in business
                    
                    double temp_lat = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lat");
                    double temp_lng = Business_Location.get(
                            Review_Date.get(instance).get(u_id).get(col).toString()).get("lng");

                    if (temp_lat < minLatitude) {
                        minLatitude = temp_lat;
                    }
                    if (temp_lat > maxLatitude) {
                        maxLatitude = temp_lat;
                    }
                    if (temp_lng < minLongitude) {
                        minLongitude = temp_lng;
                    }
                    if (temp_lng > maxLongitude) {
                        maxLongitude = temp_lng;
                    }

                    // System.out.print (Review.get(u_id).get(col).toString());

                    if (Business_Categories.keySet().contains(
                            Review_Date.get(instance).get(u_id).get(col).toString())) {
                        for (int j = 0; j < Business_Categories.get(
                                Review_Date.get(instance).get(u_id).get(col).toString()).size(); j++) {
                            String expertise = Business_Categories
                                    .get(Review_Date.get(instance).get(u_id).get(col).toString())
                                    .get(j).toString();
                            if (!User_Categories.get(u_id).contains(expertise)) {
                                User_Categories.get(u_id).add(expertise);
                            }
                        }
                    }

                }

                double lat = (minLatitude + maxLatitude) / 2;
                double lon = (minLongitude + maxLongitude) / 2;
                sb_temp.append(u_id + "," + lat + "," + lon);


                sb_temp.append("," + Review_Date.get(instance).get(u_id).size());


                sb_temp.append(",[" + minLatitude + "," + minLongitude + ","
                        + maxLatitude + "," + maxLongitude + "]");

                if (User_Categories.get(u_id).size() != 0) {

                    sb_temp.append(",[");
                    for (int j = 0; j < User_Categories.get(u_id).size(); j++) {
                        if (j > 0 && j < User_Categories.get(u_id).size()) {
                            sb_temp.append(',');
                        }
                        sb_temp.append(String.valueOf(Expertise
                                .indexOf(User_Categories.get(u_id).get(j))));
                    }

                    sb_temp.append("]\n");

                    sb.append(sb_temp);

                    //  total_expertise_user++;
                } else {
                    // System.out.println(u_id + "-one empty here");
                }
            }
            Utils.writefile2(sb.toString(), YelpConstants.SplitWorkerByTime + instance + YelpConstants.suffix, false);
        }


    }

    /**
     * Used for dynamic private geocrowd project
     */
	public static void process_TI() {
		ArrayList<GenericWorker> workerList = new ArrayList<>();
		Parser.parseExpertWorkers(YelpConstants.YelpWorker, workerList);
		
		HashMap<String, GenericWorker> workers = new HashMap<String, GenericWorker>();
		for (GenericWorker w : workerList)
			workers.put(w.getId(), w);
		
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		for (int i = 0; i < YelpConstants.TimeInstance; i++) {
			sb1.delete(0, sb1.length());
			sb2.delete(0, sb2.length());
			ArrayList<GenericWorker> wl = new ArrayList<>();
			Parser.parseExpertWorkers(YelpConstants.SplitWorkerByTime + String.format("%04d", i) + YelpConstants.suffix, wl);
			
			for (GenericWorker w : wl) {
				if (workers.containsKey(w.getId()))
					workers.put(w.getId(), w);
				
				/**
				 * to remove [0,0] coordinates which may cause problems
				 */
				if (w.getLat() == 0 || w.getLng() == 0)
					continue;
				sb1.append(w.getLat() + "\t" + w.getLng() + "\n");
			}
			
			//	dump current workers to file
			for (GenericWorker w : workers.values()) {
				if (w.getLat() == 0 || w.getLng() == 0)
					continue;
				String loc = w.getLat() + "\t" + w.getLng() + "\n";
				sb2.append(loc);
			}
			
			sb1.setLength(sb1.length() - 1);
			sb2.setLength(sb2.length() - 1);
			
			Utils.writefile2(sb1.toString(), YelpConstants.SplitWorkerByTime1 + String.format("%04d", i) + YelpConstants.suffix, false);
			Utils.writefile2(sb2.toString(), YelpConstants.SplitWorkerByTime2 + String.format("%04d", i) + YelpConstants.suffix, false);
		}
	}
}