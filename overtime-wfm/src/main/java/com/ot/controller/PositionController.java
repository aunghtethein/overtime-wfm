package com.ot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ot.dto.PositionBean;
import com.ot.model.Position;
import com.ot.service.PositionService;

@Controller
public class PositionController {

	@Autowired
	PositionService posService;

	@GetMapping("/position")
	public String position(Model model) {
		model.addAttribute("button", 0);
		model.addAttribute("list", posService.findAll());
		model.addAttribute("posAdd", new PositionBean());
		return "admin/ADM001-POS";
	}

	@PostMapping("/position/add")
	public String positionAdd(@RequestParam("id") int id,@Validated @ModelAttribute("posAdd") PositionBean posBean,BindingResult bs, 
			Model model) {
		model.addAttribute("button", id);
		posBean.setId(id);
		if (bs.hasErrors()) {
			
			model.addAttribute("list", posService.findAll());
			model.addAttribute("posAdd", posBean);
			return "admin/ADM001-POS";
		}
		Position pos=new Position();
		pos.setId(id);
		pos.setName(posBean.getName());
		List<Position> pL = posService.findAll();
		if (id == 0) {
			for (Position p : pL) {
				if (pos.getName().equals(p.getName())) {
					model.addAttribute("error", "* Position Name Already Exist!!");
					model.addAttribute("list", pL);
					model.addAttribute("posAdd", posBean);
					return "admin/ADM001-POS";
				}
			}
		} else {
			Position pId = posService.findById(id);
			for (Position p : pL) {
				if (pos.getName().equals(pId.getName())) {
					posService.save(pos);
					return "redirect:/position";
				} else if (pos.getName().equals(p.getName())) {
					model.addAttribute("error", "* Position Name Already Exist!!");
					model.addAttribute("list", pL);
					model.addAttribute("posAdd", posBean);
					return "admin/ADM001-POS";
				}
			}
		}
		posService.save(pos);
		return "redirect:/position";
	}

	@GetMapping("/position/update")
	public String positionUpdate(@RequestParam("id") int id, Model model) {
		model.addAttribute("button", id);
		Position pos=posService.findById(id);
		PositionBean posBean =new PositionBean();
		posBean.setId(id);
		posBean.setName(pos.getName());
		model.addAttribute("posAdd", posBean);
		model.addAttribute("list", posService.findAll());
		return "admin/ADM001-POS";
	}

	@GetMapping("/position/delete")
	public String positionDelete(@RequestParam("id") int id, Model model) {
		Position pos = posService.findById(id);
		posService.delete(pos);
		return "redirect:/position";
	}
}
