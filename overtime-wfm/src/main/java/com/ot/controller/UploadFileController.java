package com.ot.controller;

import com.ot.service.implementation.FileService;
import com.ot.service.implementation.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class UploadFileController {
	@Autowired
	private FileService service;
	@Autowired
	private SalaryService salaryService;
	
	
	@GetMapping("/fileupload")
    public String index(Model model) {
	    return "admin/FLU001";
    }
		
	@PostMapping("/file")
	public String uploadMultipartFile(@RequestParam("uploadfile")MultipartFile file,Model model) {
		try {
			
			service.store(file);
		
			model.addAttribute("error", "File uploaded successfully!");
		}catch (Exception e) {
			model.addAttribute("error", "Fail! -> uploaded filename: " + file.getOriginalFilename());
			e.printStackTrace();
		}
		return "redirect:/fileupload";
	}
	
	
	
	@PostMapping("/salary")
	public String multipartFile(@RequestParam("uploadSalary") MultipartFile file, Model model) {
	
		try{
			salaryService.store(file);
			model.addAttribute("message","file upload successfully");
			
		}
		catch(Exception e){
			model.addAttribute("message", "fail uplaod filename: "+ e.getMessage()+ file.getOriginalFilename());
		}
		return "redirect:/fileupload";
		
	}
	
	
}
