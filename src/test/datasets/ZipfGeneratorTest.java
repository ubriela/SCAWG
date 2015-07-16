package test.datasets;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import org.geocrowd.datasets.synthetic.ZipfGenerator;
import org.junit.Test;


public class ZipfGeneratorTest {

	@Test
	public void testZipfIncDistinctValues() {
		HashSet<Double> values = ZipfGenerator.zipfDecValues(10, true);
		Set sortedValues = new TreeSet(values);
		Iterator<Double> it = sortedValues.iterator();
		System.out.println("Size: " + sortedValues.size());
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}

}
