package com.coder.sanam.helpers;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import com.coder.sanam.models.Data;

import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CSVHelper {

	public static ByteArrayInputStream writeDataToCSV(List<Data> dataSet) {
		final CSVFormat csvFormat = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);
		
		try (ByteArrayOutputStream out = new ByteArrayOutputStream();
			CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), csvFormat);){
			for(Data data: dataSet) {
				List<String> element = Arrays.asList(String.valueOf(data.getId()),
						data.getDescription(),
						data.getFees(),
						String.valueOf(data.getIsPublished()));
				
				csvPrinter.printRecord(element);
			}
			csvPrinter.flush();
			return new ByteArrayInputStream(out.toByteArray());
			
		}catch(IOException e) {
			throw new RuntimeException("Error while writing to csv.."+e.getMessage());
		}
	}

	public static List<Data> uploadDatatoDatabase(String path) {
		List<Data> dataPack = new ArrayList<Data>(); 
		try(FileReader reader = new FileReader(path);
			CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT)){
			for(CSVRecord csvRecord:csvParser) {
				String[] values = csvRecord.values();
				Data data = new Data(values[1],values[2],Boolean.valueOf(values[3]));
				dataPack.add(data);
			}
			
			return dataPack;
		}catch(IOException e) {
			throw new RuntimeException(e.getMessage()); 
		}
		
	}
	


}
