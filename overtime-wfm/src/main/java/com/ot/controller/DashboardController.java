package com.ot.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ot.model.Overtime;
import com.ot.model.Project;
import com.ot.model.Staff;
import com.ot.model.Workflow;
import com.ot.model.WorkflowHistory;
import com.ot.repository.OverTimeRepo;
import com.ot.repository.ProjectRepo;
import com.ot.repository.StaffRepo;
import com.ot.repository.TeamRepo;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.repository.WorkFlowRepo;
import com.ot.util.AuthenticatedUser;

@Controller
public class DashboardController {

	@Autowired
	private AuthenticatedUser au;
	@Autowired
	private WorkFlowRepo workflowRepo;
	@Autowired
	private WorkFlowHistoryRepo workflowHistoryRepo;
	@Autowired
	private OverTimeRepo overtimeRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private TeamRepo teamRepo;

	@GetMapping("/home/dsh001")
	public String staffDSH001(Model model) {
		int staff = staffRepo.count(true);
		int team = teamRepo.count(true);
		int project = projectRepo.count(true);
		List<Project> list = projectRepo.tenProject();

		model.addAttribute("staff", staff);
		model.addAttribute("team", team);
		model.addAttribute("project", project);
		model.addAttribute("list", list);

		return "admin/DSH001";
	}

	@GetMapping("/home/dsh002")
	public String mainContent(Model model) {
		Count(model);
		return "form/DSH002";
	}

	@GetMapping("/home/dsh002/manageot")
	public String manageOt(Model model) {
		Staff staff = au.getAuthenticatedUser();

		List<Workflow> pendingWfList = workflowRepo.findWorkflowByPending(staff.getStaffId());
		model.addAttribute("pendingCount", pendingWfList.size());

		List<Workflow> reviseWfList = workflowRepo.findWorkflowByRevise(staff.getStaffId());
		model.addAttribute("reviseCount", reviseWfList.size());

		List<WorkflowHistory> rejectList = workflowHistoryRepo.findWorkflowByReject(staff.getStaffId());
		model.addAttribute("rejectCount", rejectList.size());

		List<WorkflowHistory> approveList = workflowHistoryRepo.findWorkflowByApproved(staff.getStaffId());
		model.addAttribute("approveCount", approveList.size());

		List<Overtime> myPendingList = overtimeRepo.findPendingOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myPendingCount", myPendingList.size());

		List<Overtime> myApproveList = overtimeRepo.findApproveOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myApproveCount", myApproveList.size());

		List<Overtime> myRejectList = overtimeRepo.findRejectOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myRejectCount", myRejectList.size());

		List<Overtime> myReviseList = overtimeRepo.findReviseOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myReviseCount", myReviseList.size());

		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		
		List<String> pjNameList = new ArrayList<>();
		List<Double> otList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findCurrent(p.getId(), staff.getStaffId());
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
		return "form/DSH002";
	}

	private void Count(Model model) {
		Staff staff = au.getAuthenticatedUser();

		List<Workflow> pendingWfList = workflowRepo.findWorkflowByPending(staff.getStaffId());
		model.addAttribute("pendingCount", pendingWfList.size());

		List<Workflow> reviseWfList = workflowRepo.findWorkflowByRevise(staff.getStaffId());
		model.addAttribute("reviseCount", reviseWfList.size());

		List<WorkflowHistory> rejectList = workflowHistoryRepo.findWorkflowByReject(staff.getStaffId());
		model.addAttribute("rejectCount", rejectList.size());

		List<WorkflowHistory> approveList = workflowHistoryRepo.findWorkflowByApproved(staff.getStaffId());
		model.addAttribute("approveCount", approveList.size());

		List<Overtime> myPendingList = overtimeRepo.findPendingOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myPendingCount", myPendingList.size());

		List<Overtime> myApproveList = overtimeRepo.findApproveOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myApproveCount", myApproveList.size());

		List<Overtime> myRejectList = overtimeRepo.findRejectOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myRejectCount", myRejectList.size());

		List<Overtime> myReviseList = overtimeRepo.findReviseOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myReviseCount", myReviseList.size());

		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findProjectId(p.getId(), staff.getStaffId());
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
	}

	@GetMapping("/home/dsh003")
	public String staffDashboard(Model model) {
		Staff staff = au.getAuthenticatedUser();

		List<Overtime> myPendingList = overtimeRepo.findPendingOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myPendingCount", myPendingList.size());

		List<Overtime> myApproveList = overtimeRepo.findApproveOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myApproveCount", myApproveList.size());

		List<Overtime> myRejectList = overtimeRepo.findRejectOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myRejectCount", myRejectList.size());

		List<Overtime> myReviseList = overtimeRepo.findReviseOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("myReviseCount", myReviseList.size());

		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findProjectId(p.getId(), staff.getStaffId());
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
		return "form/DSH003";
	}

}
