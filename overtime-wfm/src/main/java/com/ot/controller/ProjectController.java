package com.ot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ot.dto.ProjectBean;
import com.ot.model.Project;
import com.ot.service.ProjectService;

@Controller
public class ProjectController {

	@Autowired
	ProjectService pjService;

	@GetMapping(value = "/project")
	public String project(Model model) {
		model.addAttribute("pjAdd", new ProjectBean());
		model.addAttribute("list", pjService.findAll());

		return "admin/ADM001-PRJ";
	}

	@PostMapping("/project/add")
	public String projectAdd(@Valid @ModelAttribute("pjAdd") ProjectBean pjBean, BindingResult bs, Model model) {

		List<Project> pjL = pjService.findAll();
		model.addAttribute("pjAdd", pjBean);
		model.addAttribute("list", pjL);

		if (bs.hasErrors()) {
			return "admin/ADM001-PRJ";
		}
		Project pj=new Project();
		
		pj.setName(pjBean.getName());
		pj.setProjectId(pjBean.getProjectId());
		
		for (Project p : pjL) {
			if (pj.getName().equals(p.getName()) && pj.getProjectId().equals(p.getProjectId())) {
				model.addAttribute("error", "* Project ID and Project Name Already Exist!!");
				return "admin/ADM001-PRJ";
			} else if (pj.getProjectId().equals(p.getProjectId())) {
				model.addAttribute("errorid", "* Project ID Already Exist!!");
				return "admin/ADM001-PRJ";
			} else if (pj.getName().equals(p.getName())) {
				model.addAttribute("errorname", "* Project Name Already Exist!!");
				return "admin/ADM001-PRJ";
			}
		}

		pjService.save(pj);
		return "redirect:/project";
	}

	@GetMapping("/project/update")
	public String projectUpdate(@RequestParam("id") int id, Model model) {
		model.addAttribute("button", 1);
		model.addAttribute("pjAdd", pjService.findById(id));
		model.addAttribute("list", pjService.findAll());
		return "admin/ADM001-PRJ";
	}

	@PostMapping("/project/update")
	public String projectUpdate(@RequestParam("id") int id, @Valid @ModelAttribute("pjAdd") ProjectBean pjBean,
			BindingResult bs, Model model) {

		List<Project> pjL = pjService.findAll();
		model.addAttribute("pjAdd", pjBean);
		model.addAttribute("list", pjL);
		model.addAttribute("button", 1);
		
		if (bs.hasErrors()) {
			return "admin/ADM001-PRJ";
		}
		Project pj=new Project();
		pj.setId(id);
		pj.setName(pjBean.getName());
		pj.setProjectId(pjBean.getProjectId());
		Project pjId = pjService.findById(id);
		for (Project p : pjL) {
			if (pj.getName().equals(pjId.getName())) {
				pjService.save(pj);
				return "redirect:/project";
			} else if (pj.getName().equals(p.getName())) {
				model.addAttribute("errorname", "* Project Name Already Exist!!");
				return "admin/ADM001-PRJ";
			}
		}

		pjService.save(pj);
		return "redirect:/project";
	}

	@GetMapping("/project/delete")
	public String projectDelete(@RequestParam("id") int id) {
		Project prj = pjService.findById(id);
		pjService.delete(prj);
		return "redirect:/project";
	}
}
