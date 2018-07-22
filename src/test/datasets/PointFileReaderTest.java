package test.datasets;

import org.geocrowd.synthetic.PointFileReader;
import org.junit.Test;


public class PointFileReaderTest {

	@Test
	public void testParse() {
		PointFileReader pointFileReader = new PointFileReader("./res/input.txt");
		pointFileReader.parse();
	}

}
