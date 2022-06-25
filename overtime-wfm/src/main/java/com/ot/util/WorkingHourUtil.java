package com.ot.util;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.ot.model.WH;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkingHourUtil {
	public static List<WH> parseExcelFile(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet("Sheet1");

			Iterator<Row> row = sheet.iterator();
			List<WH> list = new ArrayList<>();

			int rowNo = 0;
			while (row.hasNext()) {
				Row currentRow = row.next();

				if (rowNo == 0) {
					rowNo++;
					continue;
				}

				Iterator<Cell> cellinRow = currentRow.iterator();
				WH h = new WH();
				int cellIndex = 0;
				while (cellinRow.hasNext()) {
					Cell currentCell = cellinRow.next();

					if (cellIndex == 0) {
						h.setDate(currentCell.getDateCellValue());
					} else if (cellIndex == 1) {
						h.setProjectId(currentCell.getStringCellValue());
					} else if (cellIndex == 3) {
						h.setStaffId(currentCell.getStringCellValue());
					} else if (cellIndex == 14) {
						h.setActualHour((double) currentCell.getNumericCellValue());
					}
					 
					cellIndex++;
				}
				list.add(h);

			}
			workbook.close();
			return list;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			throw new RuntimeException("Fail: -> " + e.getMessage());
		}

	}
}
