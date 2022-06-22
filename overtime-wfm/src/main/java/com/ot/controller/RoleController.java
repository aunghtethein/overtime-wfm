package com.ot.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ot.dto.RoleBean;
import com.ot.model.Role;
import com.ot.service.PrivilegeService;
import com.ot.service.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private PrivilegeService priService;

	@GetMapping("/role")
	public String role(Model model) {
		model.addAttribute("button", 0);
		model.addAttribute("priList", priService.findAll());
		model.addAttribute("list", roleService.findAll());
		model.addAttribute("roleAdd", new RoleBean());
		return "admin/ADM001-ROL";
	}

	@PostMapping("/role/create")
	public String saveRole(@RequestParam("id") int id, @Valid @ModelAttribute("roleAdd") RoleBean roleBean,
			BindingResult result, Model model) {
		model.addAttribute("button", id);
		if (result.hasErrors()) {
			model.addAttribute("priList", priService.findAll());
			model.addAttribute("list", roleService.findAll());
			model.addAttribute("roleAdd", roleBean);
			return "admin/ADM001-ROL";
		}
		Role role = new Role();
		role.setId(id);
		role.setName(roleBean.getName());
		role.setPrivileges(roleBean.getPrivileges());

		List<Role> rL = roleService.findAll();
		if (id == 0) {
			for (Role p : rL) {
				if (role.getName().equals(p.getName())) {
					model.addAttribute("error", "* Role Name Already Exist!!");
					model.addAttribute("list", rL);
					model.addAttribute("priList", priService.findAll());
					model.addAttribute("roleAdd", role);
					return "admin/ADM001-ROL";
				}
			}
		} else {
			Role rId = roleService.findById(id);
			for (Role p : rL) {
				if (role.getName().equals(rId.getName())) {
					roleService.save(role);
					return "redirect:/role";
				} else if (role.getName().equals(p.getName())) {
					model.addAttribute("error", "* Role Name Already Exist!!");
					model.addAttribute("list", rL);
					model.addAttribute("priList", priService.findAll());
					model.addAttribute("roleAdd", role);
					return "admin/ADM001-ROL";
				}
			}
		}
		roleService.save(role);
		return "redirect:/role";
	}

	@GetMapping("/role/edit/{id}")
	public String editRoleForm(@PathVariable("id") int id, Model model) {
		model.addAttribute("button", id);
		model.addAttribute("roleAdd", roleService.findById(id));
		model.addAttribute("priList", priService.findAll());
		model.addAttribute("list", roleService.findAll());

		return "admin/ADM001-ROL";
	}

	@GetMapping("/role/delete/{id}")
	public String deleteRole(@PathVariable int id) {
		Role role = roleService.findById(id);
		roleService.delete(role);
		return "redirect:/role";
	}
}
