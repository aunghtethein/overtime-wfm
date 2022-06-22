package com.ot.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.ot.model.Overtime;
import com.ot.model.Project;
import com.ot.model.Staff;
import com.ot.model.Workflow;
import com.ot.model.WorkflowHistory;
import com.ot.repository.OverTimeRepo;
import com.ot.repository.ProjectRepo;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.repository.WorkFlowRepo;
import com.ot.util.AuthenticatedUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ReportController {
	
	@Autowired
	private AuthenticatedUser au;
	@Autowired
	private WorkFlowRepo workflowRepo;
	@Autowired
	private WorkFlowHistoryRepo whRepo;
	@Autowired
	private OverTimeRepo overtimeRepo;
	@Autowired
	private ProjectRepo projectRepo;
	
	@GetMapping("/report")
	public String myOvertime(Model model) {
		//Staff staff = au.getAuthenticatedUser();
		List<Overtime> list = overtimeRepo.findAll();
		model.addAttribute("otList", list);
		return "form/REP003";
	}
	
	@GetMapping("/reporttable/{id}")
	public String reporttable(@PathVariable int id,Model model,HttpSession session) {
		
		Overtime ovt=overtimeRepo.findById(id);
		model.addAttribute("overtime", ovt);
		session.setAttribute("staffName",ovt.getStaffs().getName() );
		return "form/REP003-01";
	}
/*	
	@GetMapping("/dailyChartReport")
	public String dailyChartReport(Model model) {
		
		Staff staff = au.getAuthenticatedUser();
			
		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findDailyReport(p.getId(), staff.getStaffId(), LocalDate.now());
			List<Double> dList = new ArrayList<>();
			for (Overtime o : oList) {
				double result = o.getOvertimeDetails().stream().mapToDouble(a -> a.getOtTotalHour()).sum();

				dList.add(result);
			}
			Double r = dList.stream().reduce(0.0, Double::sum);
			System.out.println(r);

			otList.add(r);
			pjNameList.add(p.getName());
		}

		model.addAttribute("name", pjNameList);
		model.addAttribute("ot", otList);
		
		return "form/REP003";
	}
	*/

}
