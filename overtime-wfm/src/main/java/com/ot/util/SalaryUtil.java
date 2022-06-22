package com.ot.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ot.model.Salary;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SalaryUtil {
	public static List<Salary> parseExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet("Sheet1");

			Iterator<Row> row = sheet.iterator();
			List<Salary> list = new ArrayList<>();

			int rowNo = 0;
			while (row.hasNext()) {
				Row currentRow = row.next();

				if (rowNo == 0) {
					rowNo++;
					continue;
				}

				Iterator<Cell> cellinRow = currentRow.iterator();
				Salary s = new Salary();
				int cellIndex = 0;
				while (cellinRow.hasNext()) {
					Cell currentCell = cellinRow.next();

					if (cellIndex == 0) {
						s.setNo((int) currentCell.getNumericCellValue());
					} else if (cellIndex == 1) {
						s.setName(currentCell.getStringCellValue());
					} else if (cellIndex == 2) {
						s.setStaffId(currentCell.getStringCellValue());
					} else if (cellIndex == 3) {
						s.setPosition(currentCell.getStringCellValue());
					} else if (cellIndex == 4) {
						s.setBasicPay((double) currentCell.getNumericCellValue());
					}
					cellIndex++;
				}
				list.add(s);

			}
			workbook.close();
			return list;

		} catch (IOException e) {
			// e.printStackTrace();
			throw new RuntimeException("Fail: -> " + e.getMessage());
		}

	}
}
