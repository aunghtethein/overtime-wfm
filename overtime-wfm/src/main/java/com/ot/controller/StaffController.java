package com.ot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ot.dto.StaffBean;
import com.ot.model.Staff;
import com.ot.service.PositionService;
import com.ot.service.ProjectService;
import com.ot.service.RoleService;
import com.ot.service.StaffService;
import com.ot.service.TeamService;

@Controller
public class StaffController {
	@Autowired
	StaffService staffService;

	@Autowired
	ProjectService pjService;

	@Autowired
	TeamService temService;

	@Autowired
	PositionService posService;

	@Autowired
	RoleService roleService;

	@GetMapping("/adminStaff")
	public String staff(Model model) {
		model.addAttribute("button", 0);
		model.addAttribute("roleList", roleService.findAll());
		model.addAttribute("prjList", pjService.findAll());
		model.addAttribute("teamList", temService.findAll());
		model.addAttribute("posiList", posService.findAll());
		model.addAttribute("list", staffService.findAll());
		model.addAttribute("staffAdd", new StaffBean());
		System.out.println(staffService.findAll().get(0).getName());
		return "admin/ADM001-STF";
	}
	@GetMapping("/getstaff")
	public String getStaff(Model model) {
		
		List<Staff> staff=staffService.findAll();
		for(Staff s:staff) {
			System.out.println("id:::::"+s.getId());
			System.out.println("name:::::"+s.getName());
			System.out.println("projects:::::"+s.getProjects());
			System.out.println("positions:::::"+s.getPositions());
			System.out.println("teams:::::"+s.getTeams());
			
		}
		//model.addAttribute("staffList", staffService.findAll());
		
		return "getstaff";
	}

	@PostMapping("/adminStaff/add")
	public String staffAdd(@RequestParam("id") int id, @Valid @ModelAttribute("staffAdd") StaffBean staffBean,
			BindingResult bs, Model model) {
		List<Staff> sl = staffService.findAll();
		model.addAttribute("roleList", roleService.findAll());
		model.addAttribute("prjList", pjService.findAll());
		model.addAttribute("teamList", temService.findAll());
		model.addAttribute("posiList", posService.findAll());
		model.addAttribute("list", sl);
		model.addAttribute("staffAdd", staffBean);
		model.addAttribute("button", id);
		
		if (bs.hasErrors()) {	
			
			return "admin/ADM001-STF";
		}
		Staff staff = new Staff();
		staff.setId(id);
		staff.setStaffId(staffBean.getStaffId());
		staff.setName(staffBean.getName());
		staff.setSalary(staffBean.getSalary());
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String hashPassword =encoder.encode(staffBean.getPassword());
		staff.setPassword(hashPassword);
		staff.setPositions(staffBean.getPositions());
		staff.setProjects(staffBean.getProjects());
		staff.setRoles(staffBean.getRoles());
		staff.setTeams(staffBean.getTeams());
		
		if (id == 0) {
			for (Staff s : sl) {
				if (staff.getStaffId().equals(s.getStaffId())) {
					model.addAttribute("error", "* Staff ID Already Exist!!");
					return "admin/ADM001-STF";
				}
			}
		} else {
			Staff sId = staffService.findById(id);
			for (Staff s : sl) {
				if (staff.getStaffId().equals(sId.getStaffId())) {
					staffService.save(staff);
					return "redirect:/adminStaff";
				} else if (staff.getStaffId().equals(s.getStaffId())) {
					model.addAttribute("error", "* Staff ID Already Exist!!");
					return "admin/ADM001-STF";
				}
			}
		}
		staffService.save(staff);
		return "redirect:/adminStaff";
	}

	@GetMapping("/adminStaff/update")
	public String staffUpdate(@RequestParam("id") int id, Model model) {
		model.addAttribute("button", id);
		model.addAttribute("roleList", roleService.findAll());
		model.addAttribute("staffAdd", staffService.findById(id));
		model.addAttribute("list", staffService.findAll());
		model.addAttribute("prjList", pjService.findAll());
		model.addAttribute("teamList", temService.findAll());
		model.addAttribute("posiList", posService.findAll());
		return "admin/ADM001-STF";
	}

	@GetMapping("/adminStaff/delete")
	public String staffDelete(@RequestParam("id") int id) {
		Staff staff = staffService.findById(id);
		staffService.delete(staff);
		return "redirect:/adminStaff";
	}
}
