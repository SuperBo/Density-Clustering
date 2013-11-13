package hcmut.clustering.utility;

import hcmut.clustering.model.Points;
import weka.core.converters.ConverterUtils.DataSource;

public class ReadData {
	
	public static Points readData(String fileName) throws Exception {
		DataSource source = new DataSource(fileName);
		return new Points(source.getDataSet());
	}
}
