package it.geosolutions.csvmanager.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import it.geosolutions.csvmanager.service.CSVImportService;

@RestController
@CrossOrigin("*")
@RequestMapping("/upload")
public class CSVImporterRestController {
	
	@Autowired
	private CSVImportService importService;
	
	
	@PutMapping(value="/{objectKey}", consumes=MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> importCSV (HttpServletRequest request, @RequestBody MultipartFile file, @PathVariable String objectKey){
		String path =importService.saveCSV(file ,objectKey);
			return ResponseEntity.accepted().body(path);
	}

}
