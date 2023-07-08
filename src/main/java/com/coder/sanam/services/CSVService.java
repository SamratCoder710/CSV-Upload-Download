package com.coder.sanam.services;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.coder.sanam.helpers.CSVHelper;
import com.coder.sanam.models.Data;
import com.coder.sanam.repositories.CSVRepository;

@Service
public class CSVService {

	@Autowired
	CSVRepository csvRepository;

	public ByteArrayInputStream load() {
		List<Data> data = csvRepository.findAll();
		ByteArrayInputStream in = CSVHelper.writeDataToCSV(data);
		return in;
	}

	public String uploadData(String path) {
		StringBuilder customResult = new StringBuilder("Already Exist: Course(s)");
		try {
			List<Data> dataElements = CSVHelper.uploadDatatoDatabase(path);
			for(Data element : dataElements ) {
				if(csvRepository.findByDescription(element.getDescription()).isPresent()) {
					customResult.append("\n-").append(element.getDescription());
					continue;
				}
				csvRepository.save(element);
			}
			if(!customResult.toString().equalsIgnoreCase("Already Exist: Course(s)")) {
				return customResult.toString();
			}
			
		} catch (Exception e) {
			return e.getMessage();
		}
		return "Success";
	}

}
