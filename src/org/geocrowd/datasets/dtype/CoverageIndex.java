package org.geocrowd.datasets.dtype;

public class CoverageIndex implements Comparable<CoverageIndex> {

	int cover  = 0;
	int index = 0;

	

	public CoverageIndex(int cover, int index) {
		super();
		this.cover = cover;
		this.index = index;
	}
	
	



	public int getCover() {
		return cover;
	}





	public int getIndex() {
		return index;
	}





	@Override
	public int compareTo(CoverageIndex o) {
		if (this.cover > o.cover)
			return 1;
		else if (this.cover < o.cover)
			return -1;
		else 
			return 0;
	}
}

