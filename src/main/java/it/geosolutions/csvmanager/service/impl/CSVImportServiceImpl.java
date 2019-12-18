package it.geosolutions.csvmanager.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import it.geosolutions.csvmanager.service.CSVImportService;

@Service
public class CSVImportServiceImpl implements CSVImportService {

	@Autowired
	private Environment env;

	@Override
	public String saveCSV(MultipartFile file, String path) {
		Path fullPath = Paths.get(env.getProperty("base.dir") + File.separator+path);
		InputStream is = null;
		FileOutputStream fout = null;
		try {
			is = file.getInputStream();
			fout = new FileOutputStream(fullPath.toString(), false);

			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) != -1) {
				fout.write(buf, 0, len);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (is != null)
					is.close();
				if (fout != null)
					fout.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return fullPath.toString();
	}

}
