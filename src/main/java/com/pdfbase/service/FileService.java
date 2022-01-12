package com.pdfbase.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.pdfbase.entity.File;
import com.pdfbase.repository.FileRepository;

@Service
public class FileService {

	public static final String FILES_PATH = "C:/Users/taha/Desktop/project-files/";

	
	private final FileRepository fileRepository;
	
	@Autowired
	public FileService(FileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}
	
	
	public Long uploadFile(String name, MultipartFile file) {
		File dbFile = new File();
		Random rand = new Random();
		rand.nextInt();
		String nameoffile = rand.nextInt(9999)+ "_" + file.getOriginalFilename();
		java.io.File f = new java.io.File(FILES_PATH + nameoffile);

		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(f);
			fos.write(file.getBytes());
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		dbFile.setPath(FILES_PATH + nameoffile);
		dbFile.setName(name);
		dbFile.setFilename(nameoffile);
		dbFile.setUploadDate(new Date());
		return this.fileRepository.save(dbFile).getId();
	}


	public List<File> searchFiles(String name) {
		List<File> files = fileRepository.findAllByNameLike(name);
		return files;
	}
	
	public void downloadFile(Long id,HttpServletResponse response) throws IOException{
		String dbFilePath = this.fileRepository.findFilePathById(id);
		java.io.File file = new java.io.File(dbFilePath);
		//Path path = Paths.get(dbFilePath);
		//System.err.println(path.toString());
		try {
		      // get your file as InputStream
			  response.getOutputStream();
			  response.setHeader("Content-Disposition", "attachment; filename="+file.getName());
		      InputStream is = new FileInputStream(file);
		      // copy it to response's OutputStream
		      IOUtils.copy(is, response.getOutputStream());
		      response.setContentType("application/pdf");
		      response.flushBuffer();
		    } catch (IOException ex) {
		      throw new RuntimeException("IOError writing file to output stream");
		    }
	}
	
	
}
