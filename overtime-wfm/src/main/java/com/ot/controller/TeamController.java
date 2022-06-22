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

import com.ot.dto.TeamBean;
import com.ot.model.Team;
import com.ot.service.TeamService;

@Controller
public class TeamController {

	@Autowired
	TeamService teamService;

	@GetMapping("/team")
	public String team(Model model) {
		model.addAttribute("button", 0);
		model.addAttribute("list", teamService.findAll());
		model.addAttribute("teamAdd", new TeamBean());
		return "admin/ADM001-TEM";
	}

	@PostMapping("/team/add")
	public String teamAdd(@RequestParam("id") int id, @Valid @ModelAttribute("teamAdd") TeamBean teamBean, BindingResult bs,
			Model model) {
		model.addAttribute("button", id);
		if (bs.hasErrors()) {
			
			model.addAttribute("list", teamService.findAll());
			model.addAttribute("teamAdd", teamBean);
			return "admin/ADM001-TEM";
		}
		Team team=new Team();
		team.setId(id);
		team.setName(teamBean.getName());
		List<Team> tem = teamService.findAll();
		if (id == 0) {

			for (Team t : tem) {
				if (team.getName().equals(t.getName())) {

					model.addAttribute("error", "* Team Name Already Exist!!");
					model.addAttribute("list", tem);
					model.addAttribute("teamAdd", team);
					return "admin/ADM001-TEM";
				}
			}

		} else {

			Team teamId = teamService.findById(id);
			for (Team t : tem) {

				if (team.getName().equals(teamId.getName())) {
					teamService.save(team);
					return "redirect:/team";

				} else if (team.getName().equals(t.getName())) {

					model.addAttribute("error", "* Team Name Already Exist!!");
					model.addAttribute("list", tem);
					model.addAttribute("teamAdd", team);
					return "admin/ADM001-TEM";
				}

			}

		}
		teamService.save(team);
		return "redirect:/team";
	}

	@GetMapping("/team/update")
	public String teamUpdate(@RequestParam("id") int id, Model model) {
		model.addAttribute("button", id);
		model.addAttribute("teamAdd", teamService.findById(id));
		model.addAttribute("list", teamService.findAll());
		return "admin/ADM001-TEM";
	}

	@GetMapping("/team/delete")
	public String teamDelete(@RequestParam("id") int id) {
		Team tem = teamService.findById(id);
		teamService.delete(tem);
		return "redirect:/team";
	}
}
