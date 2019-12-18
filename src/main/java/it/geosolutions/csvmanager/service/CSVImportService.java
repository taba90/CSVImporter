package it.geosolutions.csvmanager.service;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface CSVImportService {
	
	public String saveCSV (MultipartFile file, String path);

}
