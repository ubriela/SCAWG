package test.datasets;

import static org.junit.Assert.*;

import org.geocrowd.datasets.dtype.DataTypeEnum;
import org.geocrowd.datasets.synthetic.DataProvider;
import org.junit.Test;

public class DataProviderTest {

	@Test
	public final void testDataProvider() {
		DataProvider dp = new DataProvider("./res/dataset/oned/one_d_zipf_skew_10000.txt", DataTypeEnum.VALUE_LIST);
	}
}
