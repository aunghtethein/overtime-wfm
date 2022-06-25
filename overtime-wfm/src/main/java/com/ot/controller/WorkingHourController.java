package com.ot.controller;

import com.ot.service.implementation.WorkingHourService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class WorkingHourController {
	@Autowired
	private WorkingHourService wokHourService;
	
	
	@GetMapping("/workinghour")
    public String index(Model model) {
        return "admin/FLU002";
    }
		
	@PostMapping("/workinghour")
	public String multipartFile(@RequestParam("workinghour") MultipartFile file, Model model) {
	
		
		try{
			
			wokHourService.store(file);
			model.addAttribute("message","file upload successfully");
			
		}
		catch(Exception e){
			model.addAttribute("message", "fail uplaod filename: "+ e.getMessage()+ file.getOriginalFilename());
		}
		return "redirect:/workinghour";
		
	}
	
	
	
	
}
