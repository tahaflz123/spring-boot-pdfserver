package com.pdfbase.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.pdfbase.entity.File;
import com.pdfbase.service.FileService;

@RestController
@RequestMapping("/file")
public class FileController {

	private final FileService fileService;

	@Autowired
	public FileController(FileService fileService) {
		this.fileService = fileService;
	}
	
	@PostMapping("/upload")
	public Long uploadFile(@PathParam("name") String name,@RequestParam("file") MultipartFile file) {
		return fileService.uploadFile(name, file);
	}
	
	@PostMapping("/search")
	public List<File> searchFiles(@PathParam("name") String name){
		return fileService.searchFiles(name);
	}
	
	@GetMapping("/download")
	public void downloadFile(@PathParam("id") Long id, HttpServletResponse response) throws IOException {
		fileService.downloadFile(id,response);
	}
	
}
