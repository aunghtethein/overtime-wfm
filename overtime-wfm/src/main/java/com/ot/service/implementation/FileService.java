package com.ot.service.implementation;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import com.ot.model.Position;
import com.ot.model.Project;
import com.ot.model.Staff;
import com.ot.model.Team;
import com.ot.model.TeamStructure;
import com.ot.repository.PositionRepo;
import com.ot.repository.ProjectRepo;
import com.ot.repository.StaffRepo;
import com.ot.repository.TeamRepo;
import com.ot.util.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FileService {

	@Autowired
	private PositionRepo positionRepo;
	@Autowired
	private ProjectRepo projectRepo;
	@Autowired
	private StaffRepo staffRepo;
	@Autowired
	private TeamRepo teamRepo;

	// Store File Data to database
	public void store(MultipartFile file) throws IOException {
		try {
			List<TeamStructure> list = ExcelUtil.parseExcelFile(file.getInputStream());

			for (TeamStructure ts : list) {
				Staff st = staffRepo.findByStaffId(ts.getStaffId());
				 Position pos = positionRepo.findByName(ts.getPosition());
				 Project proj = projectRepo.findByProjectId(ts.getProject());
				Team t = teamRepo.findByName(ts.getTeam());

				if (st == null) {
					Staff staff = new Staff();
					Team team = new Team();
					Project project =new Project();
					Position position=new Position();
					if (t == null) {
						team.setName(ts.getTeam());
						teamRepo.save(team);
						staff.setTeams(Arrays.asList(team));
					} else {
						staff.setTeams(Arrays.asList(t));
					}
					if (proj == null) {

						
						project.setName(ts.getProjectName());
						project.setProjectId(ts.getProject());
						projectRepo.save(project);
						staff.setProjects(Arrays.asList(project));
					}else {
						staff.setProjects(Arrays.asList(proj));
					}
					if(pos==null) {
						
						position.setName(ts.getPosition());
						positionRepo.save(position);
						staff.setPositions(position);
					}else {
						staff.setPositions(pos);
					}
					
					staff.setName(ts.getName());
					staff.setPassword("$2a$12$jCqvdUtagrxYUB.2ibEiMOYozMxJIgY8XupmqgL/0Ub6O3X86zKYe");
					staff.setStaffId(ts.getStaffId());
					staffRepo.save(staff);
					
				} else {//staff Not Null
					
					if (t == null) {

						Team team = new Team();
						team.setName(ts.getTeam());
						team.setStaffs(Arrays.asList(st));
						teamRepo.save(team);

					}
					if(proj==null) {
						Project project=new Project();
						
						project.setName(ts.getProjectName());
						project.setProjectId(ts.getProject());
						project.setStaffs(Arrays.asList(st));
						projectRepo.save(project);
					}
				
				}

			}
		}

		catch (IOException e) {
			throw new RuntimeException("Fail! -> message = " + e.getMessage());
		}
	}

}
