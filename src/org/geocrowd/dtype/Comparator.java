package org.geocrowd.dtype;

/**
 * this class is used for java generic purpose
 * 
 * @author HT186010
 * 
 * @param <Tx>
 * @param <Ty>
 */
public class Comparator<Tx, Ty> {
	public final int compare(final Tx x1, final Tx x2) {
		// TODO Auto-generated method stub
		if ((double)(Double) x1 < (double)(Double) x2)
			return -1;
		else if ((double)(Double) x1 == (double)(Double) x2)
			return 0;
		else
			return 1;
	}

	public Tx min(Tx x1, Tx x2) {
		if ((double)(Double) x1 < (double)(Double) x2)
			return x1;
		else
			return x2;
	}

	public Tx max(Tx x1, Tx x2) {
		if ((double)(Double) x1 < (double)(Double) x2)
			return x2;
		else
			return x1;
	}

	public Ty min_y(Ty y1, Ty y2) {
		if ((double)(Double) y1 < (double)(Double) y2)
			return y1;
		else
			return y2;
	}

	public Ty max_y(Ty y1, Ty y2) {
		if ((double)(Double) y1 < (double)(Double) y2)
			return y2;
		else
			return y1;
	}

	public final int compare_y(final Ty y1, final Ty y2) {
		// TODO Auto-generated method stub
		if ((double)(Double) y1 < (double)(Double) y2)
			return -1;
		else if ((double)(Double) y1 == (double)(Double) y2)
			return 0;
		else
			return 1;
	}
}
