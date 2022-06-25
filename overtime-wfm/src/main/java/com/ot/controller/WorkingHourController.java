package com.ot.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.ot.model.Overtime;
import com.ot.model.OvertimeDetails;
import com.ot.model.WH;
import com.ot.repository.OverTimeDetailsRepo;
import com.ot.repository.OverTimeRepo;
import com.ot.repository.WkhRepo;
import com.ot.service.implementation.WorkingHourService;
import com.ot.util.DateConverter;

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
	@Autowired
	private OverTimeRepo overTimeRepo;
	@Autowired
	private OverTimeDetailsRepo overTimeDetailsRepo;
	@Autowired
	private WkhRepo workingHourRepo;
	
	@GetMapping("/workinghour")
    public String index(Model model) {
        return "admin/FLU002";
    }
		
	@PostMapping("/workinghour")
	public String multipartFile(@RequestParam("workinghour") MultipartFile file, Model model) {
	
		
		try{
			
			wokHourService.store(file);
			model.addAttribute("message","file upload successfully");
			List<WH> whStaffIdList = new ArrayList<>();
			List<Overtime> overtimeList = overTimeRepo.findAll();
			for(Overtime o : overtimeList) {
				for(OvertimeDetails d : o.getOvertimeDetails()) {
					if(d.getActualWh() == 0) {
						Date startDate = DateConverter.convertStartDateToUtilDate(d.getStartDate());
						Date endDate = DateConverter.convertEndDateToUtilDate(d.getEndDate());
						
						List<WH> whList = workingHourRepo.findByDateBetween(startDate, endDate);
						System.out.println("whlist::::::::" + whList);
						
						for(WH wh : whList) {
							if(wh.getStaffId().equals(o.getStaffs().getStaffId())) {
								whStaffIdList.add(wh);
							}
						}
						double result =whStaffIdList.stream().mapToDouble(a -> a.getActualHour()).sum();
						d.setActualWh(result);
						whStaffIdList.removeAll(whStaffIdList);
						overTimeDetailsRepo.save(d);
					}
				}
			}
			
		}
		catch(Exception e){
			model.addAttribute("message", "fail uplaod filename: "+ e.getMessage()+ file.getOriginalFilename());
		}
		return "redirect:/workinghour";
		
	}
	
	
	
	
}
