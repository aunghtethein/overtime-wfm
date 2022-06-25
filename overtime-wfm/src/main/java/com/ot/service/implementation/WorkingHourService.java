package com.ot.service.implementation;

import java.io.IOException;
import java.util.List;

import com.ot.model.WH;
import com.ot.repository.WkhRepo;
import com.ot.util.WorkingHourUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class WorkingHourService {

	@Autowired
	private WkhRepo repo;

	public void store(MultipartFile file) {
		try {
			List<WH> whList = WorkingHourUtil.parseExcelFile(file.getInputStream());
			System.out.println(whList);
			for (WH h : whList) {
				System.out.println("h:::::"+h);
				
				WH wh = repo.findByStaffIdAndDate(h.getStaffId(), h.getDate());
				
				if(wh != null) {
					double old_hour =wh.getActualHour();
					double new_hour =h.getActualHour();
					double rs = old_hour + new_hour;
					wh.setActualHour(rs);
					
					repo.save(wh);
				}else {
					WH w = new WH();
					w.setStaffId(h.getStaffId());
					w.setProjectId(h.getProjectId());
					w.setDate(h.getDate());
					w.setActualHour(h.getActualHour());
					repo.save(w);
				}
		
			}

		} catch (IOException e) {
			throw new RuntimeException("Fail -> " + e.getMessage());
		}

	}

}
