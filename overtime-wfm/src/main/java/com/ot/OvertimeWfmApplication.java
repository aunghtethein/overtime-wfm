package com.ot;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ot.model.Position;
import com.ot.model.Privilege;
import com.ot.model.Project;
import com.ot.model.Role;
import com.ot.model.Staff;
import com.ot.model.Team;
import com.ot.service.PositionService;
import com.ot.service.PrivilegeService;
import com.ot.service.ProjectService;
import com.ot.service.RoleService;
import com.ot.service.StaffService;
import com.ot.service.TeamService;

@SpringBootApplication
public class OvertimeWfmApplication implements CommandLineRunner {
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private StaffService staffService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private TeamService teamService;

	public static void main(String[] args) {
		SpringApplication.run(OvertimeWfmApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Privilege setup = new Privilege("setup");
		Privilege manageOt = new Privilege("manageOt");
		Privilege overtime = new Privilege("overtime");
		Privilege wh = new Privilege("wh");
		Privilege masterData = new Privilege("masterData");
		Privilege report = new Privilege("report");
		Privilege hrreport = new Privilege("hrReport");
		Privilege approverreport = new Privilege("approverReport");
		Privilege dsh001 = new Privilege("dsh001");
		Privilege dsh002 = new Privilege("dsh002");
		Privilege dsh003 = new Privilege("dsh003");

		privilegeService.save(setup);
		privilegeService.save(manageOt);
		privilegeService.save(overtime);
		privilegeService.save(wh);
		privilegeService.save(masterData);
		privilegeService.save(report);
		privilegeService.save(dsh001);
		privilegeService.save(dsh002);
		privilegeService.save(dsh003);
		privilegeService.save(hrreport);
		privilegeService.save(approverreport);

		Position position1 = new Position(1, "Programmer");
		Position position2 = new Position(2, "SEL");
		Position position3 = new Position(3, "Project Lead");
		Position position4 = new Position(4, "Project Manager");
		Position position5 = new Position(5, "Hr Team Member");
		Position position6 = new Position(6, "Dept Head");
		Position position7 = new Position(7, "Division Head");

		positionService.save(position1);
		positionService.save(position2);
		positionService.save(position3);
		positionService.save(position4);
		positionService.save(position5);
		positionService.save(position6);
		positionService.save(position7);

		Role staffRole = new Role();
		staffRole.setName("StaffRole");
		staffRole.setPrivileges(new HashSet<>(Arrays.asList(overtime,report, dsh003)));
		roleService.save(staffRole);

		Role pmRole = new Role();
		pmRole.setName("PMRole");
		pmRole.setPrivileges(new HashSet<>(Arrays.asList(overtime, manageOt, approverreport, dsh002)));
		roleService.save(pmRole);

		Role deptRole = new Role();
		deptRole.setName("DeptRole");
		deptRole.setPrivileges(new HashSet<>(Arrays.asList(overtime, manageOt, approverreport, dsh002)));
		roleService.save(deptRole);

		Role divRole = new Role();
		divRole.setName("DivRole");
		divRole.setPrivileges(new HashSet<>(Arrays.asList(overtime, manageOt, approverreport, dsh002)));
		roleService.save(divRole);

		Role hrRole = new Role();
		hrRole.setName("HRRole");
		hrRole.setPrivileges(new HashSet<>(Arrays.asList(overtime, manageOt, hrreport, wh,dsh003)));
		roleService.save(hrRole);

		Role adminRole = new Role();
		adminRole.setName("AdminRole");
		adminRole.setPrivileges(new HashSet<>(Arrays.asList(setup, masterData, dsh001)));
		roleService.save(adminRole);

		Project pj1 = new Project(1, "AEON", "pj-123456");
		Project pj2 = new Project(2, "Phoenix", "pj-234567");
		Project pj3 = new Project(3, "System", "pj-223344");
		Project pj4 = new Project(4, "Sun", "pj-445566");

		projectService.save(pj1);
		projectService.save(pj2);
		projectService.save(pj3);
		projectService.save(pj4);
		
		Team team1 = new Team(1, "Aeon");
		Team team2 = new Team(2, "B1");
		teamService.save(team1);
		teamService.save(team2);

		
		Staff staff1 = new Staff(1, "26-11111", "Cherry", 200000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff1.setRoles(Set.of(staffRole));
		staff1.setProjects(Arrays.asList(pj1, pj2));
		staff1.setPositions(position1);
		staff1.setTeams(Arrays.asList(team1,team2));
		staffService.save(staff1);
		
		
		
		Staff staff2 = new Staff(2, "25-22222", "Su Su", 300000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff2.setRoles(Set.of(pmRole));
		staff2.setProjects(Arrays.asList(pj1));
		staff2.setPositions(position4);
		staff2.setTeams(Arrays.asList(team1));
		staffService.save(staff2);

		Staff staff3 = new Staff(3, "25-33333", "Kyaw Kyaw", 300000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff3.setRoles(Set.of(deptRole));
		staff3.setProjects(Arrays.asList(pj1));
		staff3.setPositions(position6);
		staff3.setTeams(Arrays.asList(team1));
		staffService.save(staff3);

		Staff staff4 = new Staff(4, "25-44444", "Hla Hla", 200000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff4.setRoles(Set.of(divRole));
		staff4.setProjects(Arrays.asList(pj1));
		staff4.setPositions(position7);
		staffService.save(staff4);

		Staff staff5 = new Staff(5, "25-55555", "Gi Gi", 200000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff5.setRoles(Set.of(pmRole));
		staff5.setProjects(Arrays.asList(pj2));
		staff5.setPositions(position4);
		staff5.setTeams(Arrays.asList(team2));
		staffService.save(staff5);

		Staff staff6 = new Staff(6, "25-66666", "Kyi Kyi", 300000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff6.setRoles(Set.of(deptRole));
		staff6.setProjects(Arrays.asList(pj2));
		staff6.setPositions(position6);
		staff6.setTeams(Arrays.asList(team2));
		staffService.save(staff6);

		Staff staff7 = new Staff(7, "25-77777", "Wana", 300000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff7.setRoles(Set.of(divRole));
		staff7.setProjects(Arrays.asList(pj2));
		staff7.setPositions(position7);
		staff7.setTeams(Arrays.asList(team2));
		staffService.save(staff7);

		Staff staff8 = new Staff(8, "25-88888", "Ku Gi", 200000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff8.setRoles(Set.of(hrRole));
		staff8.setPositions(position5);
		staffService.save(staff8);
		

		Staff staff10 = new Staff(10, "25-00000", "admin", 200000,
				"$2a$12$159jVZ.3Yg7FVHArY2gBG.KXa.AmYH1YJ1KWicwcJvuWd4.Y7L1di");
		staff10.setRoles(Set.of(adminRole));
		staff10.setProjects(Arrays.asList(pj2));
		staff10.setPositions(position1);
		staff10.setTeams(Arrays.asList(team2));
		staffService.save(staff10);
		
		

	}

}