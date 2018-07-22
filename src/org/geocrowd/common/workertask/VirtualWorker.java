/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.geocrowd.common.workertask;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;


/**
 *
 * @author luan
 */
public class VirtualWorker extends GenericWorker implements
		Comparable<VirtualWorker> {

	private List<Integer> workerIds = new LinkedList<Integer>();

	public VirtualWorker(List<Integer> list) {
		this.workerIds = list;
	}

	public VirtualWorker(Set<Integer> r) {
		workerIds = new LinkedList<Integer>(r);
		Collections.sort(workerIds);
	}

	public List<Integer> getWorkerIds() {
		return workerIds;
	}

	public void setWorkerIds(LinkedList<Integer> workerIds) {
		this.workerIds = workerIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((workerIds == null) ? 0 : workerIds.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VirtualWorker other = (VirtualWorker) obj;
		if (workerIds == null) {
			if (other.workerIds != null)
				return false;
		} else if (!workerIds.equals(other.workerIds))
			return false;
		return true;
	}

	@Override
	public int compareTo(VirtualWorker o) {
		
		/* the virtual workers with larger size at head */
		if (workerIds.size() > o.workerIds.size())
			return -1;
		else if (workerIds.size() < o.workerIds.size())
			return 1;
		else {
			List<Integer> w1 = workerIds;
			List<Integer> w2 = o.getWorkerIds();
			
			int max = Math.min(w1.size() - 1, w2.size() - 1);
			for (int i = 0; i <= max; i++) {
				if (w1.get(i) > w2.get(i))
					return -1;
				else if (w1.get(i) < w2.get(i))
					return 1;
				else if (i < max)
					continue;
				else if (i == w1.size() - 1 && i == w2.size() - 1)
					return 0;
				else if (i == w1.size() - 1)
					return 1;
				else if (i == w2.size() - 1)
					return -1;
			}
			return 0;
		}
	}
}