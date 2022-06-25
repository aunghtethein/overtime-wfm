package com.ot.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import com.ot.model.Overtime;
import com.ot.model.OvertimeStatus;
import com.ot.model.Position;
import com.ot.model.Staff;
import com.ot.model.Workflow;
import com.ot.model.WorkflowDto;
import com.ot.model.WorkflowHistory;
import com.ot.repository.StaffRepo;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.repository.WorkFlowRepo;
import com.ot.service.OvertimeService;
import com.ot.service.WorkflowHistoryService;
import com.ot.service.WorkflowService;
import com.ot.util.AuthenticatedUser;

@Controller
public class WorkflowController {

	@Autowired
	private WorkflowService workflowService;
	@Autowired
	private AuthenticatedUser au;
	@Autowired
	private WorkflowHistoryService workflowHistoryService;
	@Autowired
	private OvertimeService overtimeService;
	@Autowired
	private WorkFlowRepo workflowRepo;
	@Autowired
	private WorkFlowHistoryRepo whRepo;
	@Autowired
	private StaffRepo staffRepo;

	@GetMapping("/pending")
	public String pending(Model model) throws InterruptedException {
		Staff staff = au.getAuthenticatedUser();
		List<Workflow> pendingWfList = workflowRepo.findWorkflowByPending(staff.getStaffId());
		List<Overtime> pendingOtList = new ArrayList<Overtime>();
		for (Workflow wf : pendingWfList) {
			pendingOtList.add(wf.getOvertime());
		}
		model.addAttribute("workflow", new WorkflowDto());
		model.addAttribute("pendingOtList", pendingOtList);

		return "form/REQ003-01";
	}

	@PostMapping("/decision")
	public String decisionProcess(WorkflowDto dto, Model model) {

		Staff staff = au.getAuthenticatedUser();
		Position p = staff.getPositions();

		Overtime ot = overtimeService.findById(dto.getOtId());
		WorkflowHistory h = new WorkflowHistory();
		
		if (dto.getOtStatus().equals(OvertimeStatus.APPROVE)) {
			if (p.getName().equals("Project Manager")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDeptNext());
				Staff s = staffRepo.findByStaffId(ot.getDeptNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.PENDING);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDeptNext());
				h.setReceiverName(s.getName());
				h.setOvertimeStatus(OvertimeStatus.APPROVE);
				
			} else if (p.getName().equals("Dept Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDivNext());
				Staff s = staffRepo.findByStaffId(ot.getDivNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.PENDING);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDivNext());
				h.setReceiverName(s.getName());
				h.setOvertimeStatus(OvertimeStatus.APPROVE);
				
			} else if(p.getName().equals("Division Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getHrNext());
				Staff s = staffRepo.findByStaffId(ot.getHrNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.PENDING);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getHrNext());
				h.setReceiverName(s.getName());
				h.setOvertimeStatus(OvertimeStatus.APPROVE);
				

			}else {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.ACCEPT);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getStaffs().getStaffId());
				h.setReceiverName(s.getName());
				h.setOvertimeStatus(OvertimeStatus.ACCEPT);
				ot.setOtStatus(OvertimeStatus.APPROVE);
				
			}
			
			ot.setWf(staff.getStaffId());
			
			overtimeService.save(ot);
			
			
			h.setCreatedDate(LocalDate.now());
			h.setSender(staff.getStaffId());
			h.setSenderName(staff.getName());
			h.setRemark(dto.getRemark());
			h.setWorkflowId(dto.getId());
			h.setOvertime(ot);
			
			workflowHistoryService.save(h);

		} else if (dto.getOtStatus().equals(OvertimeStatus.REVISE)) {
			if (dto.getRemark().equals("")) {

				List<Workflow> pendingWfList = workflowRepo.findWorkflowByPending(staff.getStaffId());

				List<Overtime> pendingOtList = new ArrayList<Overtime>();
				for (Workflow wf : pendingWfList) {
					pendingOtList.add(wf.getOvertime());
				}
				model.addAttribute("error", "Remark is required for revise!!");
				model.addAttribute("workflow", new WorkflowDto());
				model.addAttribute("pendingOtList", pendingOtList);

				return "form/REQ003-01";
			}

			if (p.getName().equals("Project Manager")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getStaffs().getStaffId());
				h.setReceiverName(s.getName());
			} else if (p.getName().equals("Dept Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getPmNext());
				Staff s = staffRepo.findByStaffId(ot.getPmNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getPmNext());
				h.setReceiverName(s.getName());
			} else if(p.getName().equals("Division Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDeptNext());
				Staff s = staffRepo.findByStaffId(ot.getDeptNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDeptNext());
				h.setReceiverName(s.getName());
			}else {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDivNext());
				Staff s = staffRepo.findByStaffId(ot.getDivNext());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDivNext());
				h.setReceiverName(s.getName());
			}
			
			ot.setWf(staff.getStaffId());
			ot.setOtStatus(OvertimeStatus.REVISE);
			overtimeService.save(ot);
			
			h.setCreatedDate(LocalDate.now());
			h.setSender(staff.getStaffId());
			h.setSenderName(staff.getName());
			h.setRemark(dto.getRemark());
			h.setOvertimeStatus(OvertimeStatus.REVISE);
			h.setWorkflowId(dto.getId());
			h.setOvertime(ot);

			
			workflowHistoryService.save(h);
		} else {

			if (dto.getRemark().equals("")) {

				List<Workflow> pendingWfList = workflowRepo.findWorkflowByPending(staff.getStaffId());

				List<Overtime> pendingOtList = new ArrayList<Overtime>();
				for (Workflow wf : pendingWfList) {
					pendingOtList.add(wf.getOvertime());
				}
				model.addAttribute("error", "Remark is required for reject!!");
				model.addAttribute("workflow", new WorkflowDto());
				model.addAttribute("pendingOtList", pendingOtList);

				return "form/REQ003-01";
			}
			if (p.getName().equals("Project Manager")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REJECT);
				dto.setCreatedDate(LocalDate.now());
			} else if (p.getName().equals("Dept Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REJECT);
				dto.setCreatedDate(LocalDate.now());
			} else  if(p.getName().equals("Division Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REJECT);
				dto.setCreatedDate(LocalDate.now());
			}else {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff s = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(s.getName());
				dto.setOtStatus(OvertimeStatus.REJECT);
				dto.setCreatedDate(LocalDate.now());
			}
			
			ot.setWf(staff.getStaffId());
			ot.setOtStatus(OvertimeStatus.REJECT);
			overtimeService.save(ot);
			
			h.setReceiver(ot.getStaffs().getStaffId());
			h.setReceiverName(ot.getStaffs().getName());
			h.setCreatedDate(LocalDate.now());
			h.setSender(staff.getStaffId());
			h.setSenderName(staff.getName());
			h.setRemark(dto.getRemark());
			h.setOvertimeStatus(OvertimeStatus.REJECT);
			h.setWorkflowId(dto.getId());
			h.setOvertime(ot);

			workflowHistoryService.save(h);

		}

		Workflow wf = workflowRepo.findWorkflowByOvertimeId(dto.getOtId());

		wf.setSender(dto.getSender());
		wf.setSenderName(dto.getSenderName());
		wf.setReceiver(dto.getReceiver());
		wf.setReceiverName(dto.getReceiverName());
		wf.setCreatedDate(dto.getCreatedDate());
		wf.setOtStatus(dto.getOtStatus());
		wf.setRemark(dto.getRemark());
		workflowService.save(wf);

		return "redirect:/pending";
	}
	
	@GetMapping("/revise")
	public String revise(Model model) {
		Staff staff = au.getAuthenticatedUser();
		List<Workflow> reviseWfList = workflowRepo.findWorkflowByRevise(staff.getStaffId());

		List<Overtime> reviseOtList = new ArrayList<Overtime>();
		for (Workflow wf : reviseWfList) {
			reviseOtList.add(wf.getOvertime());
		}

		model.addAttribute("workflow", new WorkflowDto());
		model.addAttribute("reviseOtList", reviseOtList);

		return "form/REQ003-02";

	}

	@PostMapping("/revise")
	public String reviseProcess(WorkflowDto dto, Model model) {

		Staff staff = au.getAuthenticatedUser();
		Position p = staff.getPositions();

		Overtime ot = overtimeService.findById(dto.getOtId());

		if (dto.getOtStatus().equals(OvertimeStatus.REVISE)) {
			WorkflowHistory h = new WorkflowHistory();
			if (p.getName().equals("Project Manager")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getStaffs().getStaffId());
				Staff receiver = staffRepo.findByStaffId(ot.getStaffs().getStaffId());
				dto.setReceiverName(receiver.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getStaffs().getStaffId());
				h.setReceiverName(receiver.getName());
			} else if (p.getName().equals("Dept Head")) {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getPmNext());
				Staff receiver = staffRepo.findByStaffId(ot.getPmNext());
				dto.setReceiverName(receiver.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getPmNext());
				h.setReceiverName(receiver.getName());
			} else if(p.getName().equals("Division Head")){
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDeptNext());
				Staff receiver = staffRepo.findByStaffId(ot.getDeptNext());
				dto.setReceiverName(receiver.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDeptNext());
				h.setReceiverName(receiver.getName());
			}else {
				dto.setSender(staff.getStaffId());
				dto.setSenderName(staff.getName());
				dto.setReceiver(ot.getDivNext());
				Staff receiver = staffRepo.findByStaffId(ot.getDeptNext());
				dto.setReceiverName(receiver.getName());
				dto.setOtStatus(OvertimeStatus.REVISE);
				dto.setCreatedDate(LocalDate.now());
				h.setReceiver(ot.getDivNext());
				h.setReceiverName(receiver.getName());
			}

			h.setCreatedDate(LocalDate.now());
			h.setSender(staff.getStaffId());
			h.setSenderName(staff.getName());
			h.setRemark(dto.getRemark());
			h.setOvertimeStatus(OvertimeStatus.REVISE);
			h.setWorkflowId(dto.getId());
			h.setOvertime(ot);
			
			ot.setWf(staff.getStaffId());
			ot.setOtStatus(OvertimeStatus.REVISE);
			overtimeService.save(ot);

			workflowHistoryService.save(h);

		}

		Workflow wf = workflowRepo.findWorkflowByOvertimeId(dto.getOtId());

		wf.setSender(dto.getSender());
		wf.setSender(dto.getSenderName());
		wf.setReceiver(dto.getReceiver());
		wf.setReceiverName(dto.getReceiverName());
		wf.setCreatedDate(dto.getCreatedDate());
		wf.setOtStatus(dto.getOtStatus());
		wf.setRemark(dto.getRemark());
		workflowService.save(wf);
		return "redirect:/revise";
	}

	@GetMapping("/reject")
	public String reject(Model model) {
		Staff staff = au.getAuthenticatedUser();
		List<WorkflowHistory> rejectList =whRepo.findWorkflowByReject(staff.getStaffId());
		System.out.println("approve" + rejectList);

		List<Overtime> rejectOtList = new ArrayList<Overtime>();
		for (WorkflowHistory wf : rejectList) {
			rejectOtList.add(wf.getOvertime());
		}
		model.addAttribute("rejectOtList", rejectOtList);

		return "form/REQ003-03";

	}
	
	@GetMapping("/approve")
	public String approve(Model model) {
		Staff staff = au.getAuthenticatedUser();
		List<WorkflowHistory> approveWhList =whRepo.findWorkflowByApproved(staff.getStaffId());
		System.out.println("approve" + approveWhList);

		List<Overtime> approveOtList = new ArrayList<Overtime>();
		for (WorkflowHistory wf : approveWhList) {
			approveOtList.add(wf.getOvertime());
		}

		
		model.addAttribute("approveOtList", approveOtList);

		return "form/REQ003-04";
	}



}
