package org.geocrowd.synthetic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.geocrowd.common.distribution.TaskRequirementEnum;
import org.geocrowd.common.distribution.TaskTypeEnum;
import org.geocrowd.common.distribution.WorkerTypeEnum;
import org.geocrowd.common.workertask.ExpertTask;
import org.geocrowd.common.workertask.ExpertWorker;
import org.geocrowd.common.workertask.GenericTask;
import org.geocrowd.common.workertask.GenericWorker;
import org.geocrowd.common.workertask.SensingTask;
import org.geocrowd.common.workertask.SensingWorker;
import org.geocrowd.common.workertask.TaskFactory;
import org.geocrowd.common.workertask.WorkerFactory;
import org.geocrowd.common.workertask.WorkingRegion;
import org.geocrowd.GeocrowdConstants;
import org.geocrowd.GeocrowdSensingConstants;

public class Parser {

	public static int parseExpertWorkers(String fileName,
			ArrayList<GenericWorker> workerList) {
		int cnt = 0;
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);

			while (in.ready()) {
				String line = in.readLine();
				line = line.replace("];[", ";");
				String[] parts = line.split(";");
				parts[0] = parts[0].replace(";[", ";");
				String[] parts1 = parts[0].split(";");

				String[] coords = parts1[0].split(GeocrowdConstants.delimiter_dataset);

				String[] boundary = parts1[1].split(GeocrowdConstants.delimiter_dataset);

				double mbr_minLat = Double.parseDouble(boundary[0]);
				double mbr_minLng = Double.parseDouble(boundary[1]);
				double mbr_maxLat = Double.parseDouble(boundary[2]);
				double mbr_maxLng = Double.parseDouble(boundary[3]);
				WorkingRegion mbr = new WorkingRegion(mbr_minLat, mbr_minLng,
						mbr_maxLat, mbr_maxLng);

				ExpertWorker w = (ExpertWorker) WorkerFactory
						.getWorker(WorkerTypeEnum.EXPERT, Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
				w.setId(coords[0]);
				w.setCapacity(Integer.parseInt(coords[3]));
				w.setMbr(mbr);

				String experts = parts[1].substring(0, parts[1].length() - 1);
				String[] exps = experts.split(",");
				for (int i = 0; i < exps.length; i++) {
					w.addExpertise(Integer.parseInt(exps[i]));
				}

				workerList.add(w);
				cnt++;
			}

			in.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return cnt;
	}

	public static int parseExpertTasks(String fileName,
			ArrayList<GenericTask> taskList) {
		int listCount = taskList.size();
		int cnt = 0;
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);
			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(GeocrowdConstants.delimiter_dataset);
				ExpertTask t = (ExpertTask) TaskFactory.getTask(
                    TaskTypeEnum.EXPERT_TASK, Double.parseDouble(parts[0]),
                    Double.parseDouble(parts[1]));
				t.setArrivalTime(Integer.parseInt(parts[2]));
				t.setEntropy(Double.parseDouble(parts[3]));
				t.setCategory(Integer.parseInt(parts[4]));

				taskList.add(listCount, t);
				listCount++;
				cnt++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	
	/**
	 * Not in used 
	 */
	public static int parseSensingTasks(String fileName,
			ArrayList<GenericTask> taskList) {
		int cnt = 0;
		int listCount = taskList.size();
		TaskRequirementGenerator trGen = new TaskRequirementGenerator(
				GeocrowdConstants.MAX_TASK_REQUIREMENT);
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);
			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(GeocrowdConstants.delimiter_dataset);
				if ("".equals(line.trim()) || parts.length < 4)
					continue;
				SensingTask t = (SensingTask) TaskFactory.getTask(
					TaskTypeEnum.SENSING_TASK, Double.parseDouble(parts[0]),
					Double.parseDouble(parts[1]));
				t.setArrivalTime(Integer.parseInt(parts[2]));
				t.setEntropy(Double.parseDouble(parts[3]));
				t.setRadius(GeocrowdSensingConstants.TASK_RADIUS);
				t.setRequirement(trGen
						.nextWorkerRequirement(TaskRequirementEnum.CONSTANT));
				taskList.add(listCount, t);
				listCount++;
				cnt++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	public static int parseSensingTasks(String fileName, int startTime,
			ArrayList<GenericTask> taskList) {
		int cnt = 0;
		int listCount = taskList.size();
		TaskRequirementGenerator trGen = new TaskRequirementGenerator(
				GeocrowdConstants.MAX_TASK_REQUIREMENT);
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);
			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(GeocrowdConstants.delimiter_dataset);
				if ("".equals(line.trim()) || parts.length < 4)
					continue;
				SensingTask t = (SensingTask) TaskFactory.getTask(
					TaskTypeEnum.SENSING_TASK, Double.parseDouble(parts[0]),
					Double.parseDouble(parts[1]));
				t.setArrivalTime(Integer.parseInt(parts[2]) - startTime);
				t.setEntropy(Double.parseDouble(parts[3]));
				t.setRadius(GeocrowdSensingConstants.TASK_RADIUS);
				t.setRequirement(trGen
						.nextWorkerRequirement(TaskRequirementEnum.CONSTANT));
				taskList.add(listCount, t);
				listCount++;
				cnt++;
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cnt;
	}

	public static int parseGenericWorkers(String fileName,
			ArrayList<GenericWorker> workerList) {
		workerList.clear(); // luan comment for test
		int cnt = 0;
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);

			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(GeocrowdConstants.delimiter_dataset);
				GenericWorker w = WorkerFactory.getWorker(WorkerTypeEnum.GENERIC_WORKER, Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
				w.setId(parts[0]);
				w.setCapacity(Integer.parseInt(parts[3]));
				
				workerList.add(w);
				cnt++;
			}

			in.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return cnt;
	}

	/**
	 * Not in used 
	 */
	public static int parseSensingWorkers(String fileName,
			ArrayList<GenericWorker> workerList, int timeInstance) {
		int cnt = 0;
		try {
			FileReader reader = new FileReader(fileName);
			BufferedReader in = new BufferedReader(reader);

			while (in.ready()) {
				String line = in.readLine();
				String[] parts = line.split(GeocrowdConstants.delimiter_dataset);
				
				SensingWorker w = (SensingWorker) WorkerFactory
						.getWorker(WorkerTypeEnum.SENSING_WORKER, Double.parseDouble(parts[1]), Double.parseDouble(parts[2]));
				w.setId(parts[0]);
				w.setCapacity(Integer.parseInt(parts[3]));
				w.setOnlineTime(timeInstance);

				workerList.add(w);
				cnt++;
			}

			in.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return cnt;
	}

	public static int readNumberOfTasks(String fileName)
			throws FileNotFoundException, IOException {
		BufferedReader bfr = new BufferedReader(new FileReader(fileName));
		int count = 0;
		String line;
		while ((line = bfr.readLine()) != null) {
			count++;
		}
		return count;

	}

}
