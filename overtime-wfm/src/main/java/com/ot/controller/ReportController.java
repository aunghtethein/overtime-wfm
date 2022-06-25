package com.ot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ot.dto.ReportDto;
import com.ot.model.Overtime;
import com.ot.model.OvertimeDetails;
import com.ot.model.Project;
import com.ot.model.Staff;
import com.ot.model.WorkflowHistory;
import com.ot.repository.OverTimeRepo;
import com.ot.repository.ProjectRepo;
import com.ot.repository.StaffRepo;
import com.ot.repository.WorkFlowHistoryRepo;
import com.ot.util.AuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

@Controller
public class ReportController {

	@Autowired
	private AuthenticatedUser au;
	@Autowired
	private WorkFlowHistoryRepo whRepo;
	@Autowired
	private OverTimeRepo overtimeRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private StaffRepo staffRepo;

	@GetMapping("/staff/report")
	public String myOvertime(Model model) {
		Staff staff = au.getAuthenticatedUser();

		List<Overtime> list = overtimeRepo.findApproveOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("otList", list);
		
		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otRangeList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findApprovedProjectId(p.getId(), staff.getStaffId());
			List<Double> dList = new ArrayList<>();
			for (Overtime o : oList) {
				double result = o.getOvertimeDetails().stream().mapToDouble(a -> a.getOtRange()).sum();

				dList.add(result);
			}
			Double r = dList.stream().reduce(0.0, Double::sum);
			System.out.println(r);

			otRangeList.add(r);
			pjNameList.add(p.getName());
		}

		model.addAttribute("name", pjNameList);
		model.addAttribute("ot", otRangeList);
		
		return "form/REP003-01";
	}
	@GetMapping("/staff/print/{id}")
	public String staffPrint(@PathVariable int id, Model model, HttpSession session) {

		Overtime ovt = overtimeRepo.findById(id);
		model.addAttribute("overtime", ovt);
	
		session.setAttribute("oid", ovt.getId());
		List<WorkflowHistory> wfList = whRepo.findPmWfhByOvertimeId(ovt.getId());

		List<WorkflowHistory> pmwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getPmNext()))
				.collect(Collectors.toList());
		model.addAttribute("pmwf", pmwf);

		List<WorkflowHistory> deptwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDeptNext()))
				.collect(Collectors.toList());
		model.addAttribute("deptwf", deptwf);

		List<WorkflowHistory> divwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDivNext()))
				.collect(Collectors.toList());
		model.addAttribute("divwf", divwf);

		Staff pmStaff = staffRepo.findByStaffId(ovt.getPmNext());
		Staff deptStaff = staffRepo.findByStaffId(ovt.getDeptNext());
		Staff divStaff = staffRepo.findByStaffId(ovt.getDivNext());
		session.setAttribute("pmName", pmStaff.getName());
		session.setAttribute("deptName", deptStaff.getName());
		session.setAttribute("divName", divStaff.getName());
		session.setAttribute("staffName", ovt.getStaffs().getName());
		
			
		
		//noted
		session.setAttribute("staffPrint", wfList);
		return "form/REP004";
	}
	
	@GetMapping("/approver/report")
	public String myOvertimeReport(Model model) {
		Staff staff = au.getAuthenticatedUser();

		List<Overtime> list = overtimeRepo.findApproverOvertimeByStaffId(staff.getStaffId());
		model.addAttribute("otList", list);
		
		
		
		List<Project> pjList = projectRepo.findProjectByStaffId(staff.getStaffId());
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otRangeList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findCurrent(p.getId(),staff.getStaffId());
			

			List<Double> dList = new ArrayList<>();
			for (Overtime o : oList) {
				double result = o.getOvertimeDetails().stream().mapToDouble(a -> a.getOtRange()).sum();

				dList.add(result);
			}
			Double r = dList.stream().reduce(0.0, Double::sum);
			System.out.println(r);

			otRangeList.add(r);
			pjNameList.add(p.getName());
		}

		model.addAttribute("name", pjNameList);
		model.addAttribute("ot", otRangeList);
		

		return "form/REP003-02";
	}
	
	@GetMapping("/approver/print/{id}")
	public String approverPrint(@PathVariable int id, Model model, HttpSession session) {

		Overtime ovt = overtimeRepo.findById(id);
		model.addAttribute("overtime", ovt);
		session.setAttribute("oid", ovt.getId());
		List<WorkflowHistory> wfList = whRepo.findPmWfhByOvertimeId(ovt.getId());

		List<WorkflowHistory> pmwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getPmNext()))
				.collect(Collectors.toList());
		model.addAttribute("pmwf", pmwf);

		List<WorkflowHistory> deptwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDeptNext()))
				.collect(Collectors.toList());
		model.addAttribute("deptwf", deptwf);

		List<WorkflowHistory> divwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDivNext()))
				.collect(Collectors.toList());
		model.addAttribute("divwf", divwf);

		Staff pmStaff = staffRepo.findByStaffId(ovt.getPmNext());
		Staff deptStaff = staffRepo.findByStaffId(ovt.getDeptNext());
		Staff divStaff = staffRepo.findByStaffId(ovt.getDivNext());
		session.setAttribute("pmName", pmStaff.getName());
		session.setAttribute("deptName", deptStaff.getName());
		session.setAttribute("divName", divStaff.getName());
		session.setAttribute("staffName", ovt.getStaffs().getName());
		
		//noted
		session.setAttribute("approverPrint", wfList);
		return "form/REP004";
	}
	
	
	
	@GetMapping("/hr/report")
	public String myOvertimeHrReport(Model model) {
		List<Overtime> list = overtimeRepo.findAllOvertimeByApproved();
		model.addAttribute("otList", list);
		
		List<Project> pjList = projectRepo.findAll();
		System.out.println("pjlist " + pjList);
		List<String> pjNameList = new ArrayList<>();
		List<Double> otRangeList = new ArrayList<>();

		for (Project p : pjList) {
			System.out.println(p);
			List<Overtime> oList = overtimeRepo.findAllApprovedProjectId(p.getId());
			List<Double> dList = new ArrayList<>();
			for (Overtime o : oList) {
				double result = o.getOvertimeDetails().stream().mapToDouble(a -> a.getOtRange()).sum();

				dList.add(result);
			}
			Double r = dList.stream().reduce(0.0, Double::sum);
			System.out.println(r);

			otRangeList.add(r);
			pjNameList.add(p.getName());
		}

		model.addAttribute("name", pjNameList);
		model.addAttribute("ot", otRangeList);
		

		return "form/REP003-03";
	}
	
	
	@GetMapping("/hr/print/{id}")
	public String hrPrint(@PathVariable int id, Model model, HttpSession session) {

		Overtime ovt = overtimeRepo.findById(id);
		model.addAttribute("overtime", ovt);
		session.setAttribute("oid", ovt.getId());
		List<WorkflowHistory> wfList = whRepo.findPmWfhByOvertimeId(ovt.getId());

		List<WorkflowHistory> pmwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getPmNext()))
				.collect(Collectors.toList());
		model.addAttribute("pmwf", pmwf);

		List<WorkflowHistory> deptwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDeptNext()))
				.collect(Collectors.toList());
		model.addAttribute("deptwf", deptwf);

		List<WorkflowHistory> divwf = wfList.stream().filter(a -> a.getSender().equals(ovt.getDivNext()))
				.collect(Collectors.toList());
		model.addAttribute("divwf", divwf);

		Staff pmStaff = staffRepo.findByStaffId(ovt.getPmNext());
		Staff deptStaff = staffRepo.findByStaffId(ovt.getDeptNext());
		Staff divStaff = staffRepo.findByStaffId(ovt.getDivNext());
		session.setAttribute("pmName", pmStaff.getName());
		session.setAttribute("deptName", deptStaff.getName());
		session.setAttribute("divName", divStaff.getName());
		session.setAttribute("staffName", ovt.getStaffs().getName());
		session.setAttribute("hrPrint", wfList);
		return "form/REP004";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	@GetMapping("/export/{id}")
	public void exportPdf(@PathVariable int id, HttpServletRequest request,HttpServletResponse response, HttpSession session) throws IOException {
		Overtime ovt = overtimeRepo.findById(id);
		
		ReportDto dto = new ReportDto();
		dto.setId(ovt.getId());
		dto.setName(ovt.getStaffs().getName());
		for(OvertimeDetails od :ovt.getOvertimeDetails()) {
			dto.setReason(od.getReason());
			dto.setStartDate(od.getStartDate());
			dto.setEndDate(od.getEndDate());
			dto.setStartTime(od.getStartTime());
			dto.setEndTime(od.getEndTime());
			dto.setOtTotalHour(od.getOtTotalHour());
			dto.setDayType(od.getDayType());
			dto.setOtRange(od.getOtRange());
		}
	
		
		
		
		List<ReportDto> dtoList = new ArrayList<>();
		dtoList.add(dto);
		
		System.out.println(dtoList);
		
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("report", "Report List");
		try {
			JRBeanCollectionDataSource source = 
					new JRBeanCollectionDataSource(dtoList);
			
			JasperReport jasperReport =
					JasperCompileManager.compileReport("src/main/resources/sth.jrxml");
			JasperPrint print = 
					JasperFillManager.fillReport(jasperReport, parameters, source);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename=myreport.pdf");
			JRPdfExporter exporterPdf = new JRPdfExporter();
			exporterPdf.setParameter(JRPdfExporterParameter.JASPER_PRINT, print);
			exporterPdf.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, response.getOutputStream());
			exporterPdf.exportReport();

		} catch (JRException e) {
			e.printStackTrace();
		}
	
	}

}
