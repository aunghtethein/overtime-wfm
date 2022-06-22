package com.ot.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ot.model.Overtime;
import com.ot.model.OvertimeDetails;
import com.ot.model.OvertimeStatus;
import com.ot.model.Project;
import com.ot.model.Staff;
import com.ot.model.Workflow;
import com.ot.model.WorkflowHistory;
import com.ot.repository.OverTimeDetailsRepo;
import com.ot.repository.OverTimeRepo;
import com.ot.repository.ProjectRepo;
import com.ot.repository.StaffRepo;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.repository.WorkFlowRepo;
import com.ot.service.OvertimeService;
import com.ot.service.WorkflowService;
import com.ot.util.AuthenticatedUser;

@Controller
public class OvertimeController {

	@Autowired
	private OvertimeService overtimeService;
	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private ProjectRepo pjRepo;
	@Autowired
	private AuthenticatedUser au;
	@Autowired
	private OverTimeRepo otRepo;
	@Autowired
	private WorkFlowHistoryRepo workFlowHistoryRepo;
	
	
	

	List<OvertimeDetails> list = new ArrayList<>();

	@GetMapping("/overtime")
	public String setupOvertimeForm(Model model, HttpSession session) {

		Staff staff = au.getAuthenticatedUser();
		model.addAttribute("overtime", new Overtime());
		model.addAttribute("detailObj", new OvertimeDetails());
		model.addAttribute("details", list);
		session.setAttribute("staff", staff);
		model.addAttribute("projects", staff.getProjects());
		session.setAttribute("staffId", staff.getStaffId());
		session.setAttribute("staffName", staff.getName());


		return "form/OVT001";
	}

	@PostMapping("/overtime/create")
	public String saveOvertime(@Valid @ModelAttribute("overtime") Overtime overtime, BindingResult result, Model model,
			HttpSession session) {
		Staff staff = au.getAuthenticatedUser();
		model.addAttribute("overtime", overtime);
		model.addAttribute("detailObj", new OvertimeDetails());
		model.addAttribute("details", list);
		session.setAttribute("staff", staff);
		model.addAttribute("projects", staff.getProjects());
		session.setAttribute("staffId", staff.getStaffId());
		session.setAttribute("staffName", staff.getName());
		Project pj = (Project) session.getAttribute("project");
		
		//hhz
		if (pj == null && list.size()==0) {
			model.addAttribute("detailError", "Overtime Details is required!!");
			model.addAttribute("error", "Project ID is required!!");
			return "form/OVT001";
		}else if(pj == null && list.size()!=0) {
			model.addAttribute("error", "Project ID is required!!");
			return "form/OVT001";
		}else if(pj != null && list.size()==0) {
			model.addAttribute("detailError", "Overtime Details is required!!");
			return "form/OVT001";
		}
		
		// Staff staff = au.getAuthenticatedUser();
		overtime.setStaffs(staff);

		boolean od = list.stream().allMatch(a -> a.getOtTotalHour() <= 3);

		if (!od) {
			System.out.print("error");
			model.addAttribute("errorMessage", "Should'n pass over one day per 3 hours");
			model.addAttribute("overtime", overtime);
			model.addAttribute("detailObj", new OvertimeDetails());
			model.addAttribute("details", list);
			session.setAttribute("staff", staff);
			model.addAttribute("projects", staff.getProjects());
			session.setAttribute("staffId", staff.getStaffId());
			session.setAttribute("staffName", staff.getName());

			return "form/OVT001";
			
			//end hhz
		} else {
			overtime.setProjects(pj);
			overtime.setOvertimeDetails(list);
			overtime.setFormId("default");
			overtime.setSubmitted_date(LocalDate.now());
			overtime.setOtStatus(OvertimeStatus.PENDING);
			overtime.setWf(staff.getStaffId());
			overtimeService.save(overtime);

			Workflow wf = new Workflow();
			wf.setSender(staff.getStaffId());
			wf.setReceiver(overtime.getCurrentNext());
			wf.setCreatedDate(LocalDate.now());
			wf.setOtStatus(OvertimeStatus.PENDING);
			wf.setOvertime(overtime);
			workflowService.save(wf);

			list.clear();
		}

		return "redirect:/overtime";
	}

	// OverTime Search All
	@GetMapping("/myRecord")
	public String myOvertime(Model model) {
		Staff staff = au.getAuthenticatedUser();
		List<Overtime> list = otRepo.findOvertimebyStaffId(staff.getId());
		model.addAttribute("myotlist", list);
		return "form/OVT003";
	}
	
	@GetMapping("/myPendingOt")
	public String myPendingOt(Model model) {
		Staff staff = au.getAuthenticatedUser();
	
		List<Overtime> myPendingList=otRepo.findPendingOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myPending", myPendingList);
	
		return "form/OVT003";
	}
	
	@GetMapping("/myApproveOt")
	public String myApproveOt(Model model) {
		Staff staff = au.getAuthenticatedUser();
		
		List<Overtime> myApproveList=otRepo.findApproveOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myApprove", myApproveList);
		
		return "form/OVT003";
	}
	
	@GetMapping("/myRejectOt")
	public String myRejectOt(Model model) {
		Staff staff = au.getAuthenticatedUser();
		
		List<Overtime> myRejectList=otRepo.findRejectOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myReject", myRejectList);
		
		return "form/OVT003";
	}

	
	@GetMapping("/myReviseOt")
	public String myReviseOt(Model model,HttpSession session) {
		Staff staff = au.getAuthenticatedUser();
		
		List<Overtime> myReviseList=otRepo.findReviseOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myRevise", myReviseList);
	
		return "form/OVT003";
	}

	
	@GetMapping("/history/{id}")
	public String history(@PathVariable("id") int id, Model model) {
		System.out.println("D Id" + id);
		List<WorkflowHistory> wkhList=workFlowHistoryRepo.findWorkflowHistorybyOvertimeId(id);
		System.out.println("Ta Ku Ku "+wkhList);
		model.addAttribute("workflowhistory",wkhList);
		return "form/update";
	}
	
	
	
	@GetMapping("/reviseEdit/{id}")
	public String reviseEdit(@PathVariable Integer id, Model model) {
		Overtime overtime=otRepo.findById(id).orElse(null);
		model.addAttribute("reviseUpdate",overtime);
		for(OvertimeDetails d : overtime.getOvertimeDetails()) {
			model.addAttribute("reviseUpdateDetail", d);		
		}
		return "form/OVT002";
	}
	@PostMapping ("/reviseDetailUpdateSave/{id}")
	public String reviseDetailUpdateSave(@PathVariable Integer id,OvertimeDetails details,BindingResult result,Model  model) {
		
		if(result.hasErrors()) {
			details.setId(id);
			return "form/OVT002";
		}
		Staff staff = au.getAuthenticatedUser();
		LocalDate startDate = details.getStartDate();
		LocalDate endDate = details.getEndDate();
		long d = startDate.until(endDate, ChronoUnit.DAYS);

		LocalTime startTime = details.getStartTime();
		LocalTime endTime = details.getEndTime();

		long t = startTime.until(endTime, ChronoUnit.HOURS);
		if (startDate.equals(endDate)) {

//			OT = ((Monthly Basic Pay X 12 Months) ÷ 52 Weeks / 48 Hour) * 2
			
			double salary=staff.getSalary();
			double ot = salary * 12;
			double range=((ot)/52/48)*2;

			details.setOtTotalHour(t);
			details.setOtRange(Math.round(t * range)); 
			
		} else {
			double salary=staff.getSalary();
			double ot = salary * 12;
			double range=((ot)/52/48)*2;
			
			details.setOtTotalHour((t * d) + 1);
			double totalhour = ((t * d) + 1);
			details.setOtRange(Math.round(totalhour * range)); 
		}
		
		return "redirect:/reviseEdit";
	}

	// overtime detail get mapping
	@GetMapping("/overtime/details")
	public String detail(Model model) {
		model.addAttribute("detailObj", new OvertimeDetails());
		return "form/OVT001";
	}

	// overtime detail post mapping
	@PostMapping("/overtime/details")
	public String saveDetails(@Validated @ModelAttribute("detailObj") OvertimeDetails overtimerDetails,
			BindingResult rs, Model model, HttpSession session) {
		if (rs.hasErrors()) {
			Staff staff = au.getAuthenticatedUser();
			model.addAttribute("overtime", new Overtime());
			model.addAttribute("detailObj", overtimerDetails);
			model.addAttribute("details", list);
			session.setAttribute("staff", staff);
			model.addAttribute("projects", staff.getProjects());
			session.setAttribute("staffId", staff.getStaffId());
			session.setAttribute("staffName", staff.getName());
			return "form/OVT001";
		}
		Staff staff = au.getAuthenticatedUser();
		LocalDate startDate = overtimerDetails.getStartDate();
		LocalDate endDate = overtimerDetails.getEndDate();
		long d = startDate.until(endDate, ChronoUnit.DAYS);

		LocalTime startTime = overtimerDetails.getStartTime();
		LocalTime endTime = overtimerDetails.getEndTime();


		long t = startTime.until(endTime, ChronoUnit.HOURS);
		if (startDate.equals(endDate)) {

//			OT = ((Monthly Basic Pay X 12 Months) ÷ 52 Weeks / 48 Hour) * 2
			
			double salary=staff.getSalary();
			double ot = salary * 12;
			double range=((ot)/52/48)*2;

			overtimerDetails.setOtTotalHour(t);
			overtimerDetails.setOtRange(Math.round(t * range)); 
			
		} else {
			double salary=staff.getSalary();
			double ot = salary * 12;
			double range=((ot)/52/48)*2;
			
			overtimerDetails.setOtTotalHour((t * d) + 1);
			double totalhour = ((t * d) + 1);
			overtimerDetails.setOtRange(Math.round(totalhour * range)); 
		}
		

		list.add(overtimerDetails);
		return "redirect:/overtime";
	}

	// delete overtime detail in list
	@GetMapping("/overtime/{reason}")
	public String end(@PathVariable String reason, Model model) {
		list.removeIf(t -> t.getReason().equals(reason));
		return "redirect:/overtime";
	}

	@GetMapping("/form/option/{id}")
	public String getNextApproverByProject(@PathVariable("id") int id, Model model, HttpSession session) {

		List<Staff> staffList = staffRepo.findStaffByProjectId(id);

		Project pj = pjRepo.findProjectById(id);
		session.setAttribute("project", pj);
		Staff staff = au.getAuthenticatedUser();

		Staff pm = staffList.stream().filter(p -> p.getPositions().getName().equals("Project Manager")).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No data found"));

		Staff dept = staffList.stream().filter(de -> de.getPositions().getName().equals("Dept Head")).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No data found"));

		Staff div = staffList.stream().filter(di -> di.getPositions().getName().equals("Division Head")).findFirst()
				.orElseThrow(() -> new IllegalArgumentException("No data found"));

		Staff hr=staffRepo.findHrPositon();
		
		// todo // to think about hr here

		if (staff.getPositions().getName().equals("Project Manager")) {
			session.setAttribute("currentNext", dept);
			session.setAttribute("currentNextId", dept.getStaffId());

			session.setAttribute("deptNext", dept);
			session.setAttribute("deptNextId", dept.getStaffId());

			session.setAttribute("divNext", div);
			session.setAttribute("divNextId", div.getStaffId());

			session.setAttribute("projectName", pj.getName());
			session.setAttribute("projectId", pj.getProjectId());
			

		} else if (staff.getPositions().getName().equals("Dept Head")) {
			session.setAttribute("currentNext", div);
			session.setAttribute("currentNextId", div.getStaffId());

			session.setAttribute("divNext", div);
			session.setAttribute("divNextId", div.getStaffId());

			session.setAttribute("projectName", pj.getName());
			session.setAttribute("projectId", pj.getProjectId());

		} else {
			session.setAttribute("currentNext", pm);
			session.setAttribute("currentNextId", pm.getStaffId());

			session.setAttribute("pmNext", pm);
			session.setAttribute("pmNextId", pm.getStaffId());

			session.setAttribute("deptNext", dept);
			session.setAttribute("deptNextId", dept.getStaffId());

			session.setAttribute("divNext", div);
			session.setAttribute("divNextId", div.getStaffId());

			session.setAttribute("projectName", pj.getName());
			session.setAttribute("projectId", pj.getProjectId());
			
			
		}

		session.setAttribute("hrNext", hr);
		session.setAttribute("hrId",hr.getStaffId());
		
		
		return "redirect:/overtime";
	}

	
	// testing overtime detail in list
	@GetMapping("/overtime/edit/{reason}")
	public String edit(@PathVariable String reason, Model model) {

		List<OvertimeDetails> d = list.stream().filter(a -> a.getReason().equals(reason)).collect(Collectors.toList());
		for (OvertimeDetails o : d) {
			model.addAttribute("updateD", o);
			System.out.println("Reason " + o.getReason());
		}

		System.out.println("Helllo Inside update::::::::::::::::::::::::::::::::");
		return "form/OVT001";
	}

	// testing about detail list update
	@PostMapping("/overtime/update")
	public String updateRoleForm(@PathVariable String reason, OvertimeDetails detail, BindingResult result) {

		System.out.println("Inside post ::::");
		OvertimeDetails d = (OvertimeDetails) list.stream().filter(a -> a.getReason().equals(detail.getReason()))
				.collect(Collectors.toList());
		int index = list.indexOf(d);

		list.set(index, d);

		return "redirect:/overtime";
	}

}
