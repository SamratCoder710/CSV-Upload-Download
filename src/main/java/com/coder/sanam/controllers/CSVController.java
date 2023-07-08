package com.coder.sanam.controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.coder.sanam.services.CSVService;

@RequestMapping("/api/csv")
@Controller
public class CSVController {

	@Autowired
	CSVService csvService;

	@Value("${myapp.location.csv}")
	private String csvPath;

	@GetMapping("/upload")
	public ModelAndView uploadCSVFile() {
		ModelAndView model = new ModelAndView("Output");
		String csvLocation = (csvPath != null) ? csvPath : "";
		String result = csvService.uploadData(csvLocation);
		model.addObject("csvLoc", csvLocation);
		if (result.contains("Already Exist")) {
			model.addObject("customMsg", result);
			result = "Success";  
		}
		model.addObject("result", result);
		return model;
	}
	
	@RequestMapping("/download")
	public ResponseEntity<Resource> getCSVDownload() {
		String fileName = "Data.csv";
		InputStreamResource file = new InputStreamResource(csvService.load());
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment ; filename=" + fileName)
				.contentType(MediaType.parseMediaType("application/csv")).body(file);
	}

	

}
