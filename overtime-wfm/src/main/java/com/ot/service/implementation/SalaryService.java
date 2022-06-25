package com.ot.service.implementation;

import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.ot.model.Position;
import com.ot.model.Salary;
import com.ot.model.Staff;
import com.ot.repository.PositionRepo;
import com.ot.repository.StaffRepo;
import com.ot.util.SalaryUtil;

@Service
public class SalaryService {

	@Autowired
	private PositionRepo positionRepo;
	@Autowired
	private StaffRepo staffRepo;

	public void store(MultipartFile file) {
		try {
			List<Salary> salary = SalaryUtil.parseExcelFile(file.getInputStream());

			for (Salary s : salary) {

				Staff st = staffRepo.findByStaffId(s.getStaffId());
				Position p = positionRepo.findByName(s.getPosition());

				if (st == null) {
					Staff staff = new Staff();

					if (p == null) {
						Position position = new Position();
						position.setName(s.getPosition());
						positionRepo.save(position);

						staff.setName(s.getName());
						staff.setStaffId(s.getStaffId());
						staff.setPositions(position);
						staff.setSalary(s.getBasicPay());
						staffRepo.save(staff);
					} else {

						staff.setName(s.getName());
						staff.setStaffId(s.getStaffId());
						staff.setPositions(p);
						staff.setSalary(s.getBasicPay());
						staffRepo.save(staff);

					}

				} else {

					st.setSalary(s.getBasicPay());
					staffRepo.save(st);
				}

			}

		} catch (IOException e) {
			throw new RuntimeException("Fail -> " + e.getMessage());
		}

	}

}
