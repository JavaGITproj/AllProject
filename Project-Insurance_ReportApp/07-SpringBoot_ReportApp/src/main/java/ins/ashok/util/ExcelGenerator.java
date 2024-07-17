package ins.ashok.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;

import ins.ashok.Entity.CitizenPlans;

@Component
public class ExcelGenerator {
	

	public void generator(ServletResponse response,List<CitizenPlans> records,File f )throws Exception {
		
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet();
		Row headerRow = sheet.createRow(0);

		headerRow.createCell(0).setCellValue("ID");
		headerRow.createCell(1).setCellValue("Citizen Name");
		headerRow.createCell(2).setCellValue("Gender");
		headerRow.createCell(3).setCellValue("Citizen Plan");
		headerRow.createCell(4).setCellValue("Plan Status");
		headerRow.createCell(5).setCellValue("plan Start Date");
		headerRow.createCell(6).setCellValue("plan end Date");
		headerRow.createCell(7).setCellValue("Benifit Amount");

//		List<CitizenPlans> records = planRepo.findAll();
		int datarowindex = 1;
		for (CitizenPlans plans : records) {
			Row datarow = sheet.createRow(datarowindex);

			datarow.createCell(0).setCellValue(plans.getCitizenId());
			datarow.createCell(1).setCellValue(plans.getCitizenName());
			datarow.createCell(2).setCellValue(plans.getGender());
			datarow.createCell(3).setCellValue(plans.getPlanName());
			datarow.createCell(4).setCellValue(plans.getPlanStatus());
			if (null != plans.getPlanStartDate()) {
				datarow.createCell(5).setCellValue(plans.getPlanStartDate() + "");
			} else {
				datarow.createCell(5).setCellValue("N/A");
			}
			if (null != plans.getPlanEndDate()) {

				datarow.createCell(6).setCellValue(plans.getPlanEndDate() + "");
			} else {
				datarow.createCell(6).setCellValue("N/A");
			}
			if (null != plans.getBenefitAmt()) {
				datarow.createCell(7).setCellValue(plans.getBenefitAmt());
			} else {
				datarow.createCell(7).setCellValue("N/A");
			}
			datarowindex++;
		}
		FileOutputStream fos=new FileOutputStream(f);
        workbook.write(fos);
        fos.close();
        
		ServletOutputStream outputstream = response.getOutputStream();
		workbook.write(outputstream);
		workbook.close();
		
	}

}
