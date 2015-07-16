package test.datasets;

import static org.junit.Assert.*;

import org.geocrowd.datasets.synthetic.PointFileReader;
import org.junit.Test;


public class PointFileReaderTest {

	@Test
	public void testParse() {
		PointFileReader pointFileReader = new PointFileReader("./res/input.txt");
		pointFileReader.parse();
	}

}
