package ins.ashok.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import ins.ashok.Entity.CitizenPlans;
@Component
public class PdfGenerator {

	public void generator(ServletResponse response,List<CitizenPlans>plans,File f)throws Exception {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		PdfWriter.getInstance(document, new FileOutputStream(f));
		document.open();
		Paragraph p = new Paragraph("Citizen Plans Info");
		p.setAlignment(Paragraph.ALIGN_CENTER);
		document.add(p);
		// Creating font
		// Setting font style and size
		Font fontTiltle = FontFactory.getFont(FontFactory.TIMES_ROMAN);
		fontTiltle.setSize(20);
		// Aligning the paragraph in document
		// Setting width of table, its columns and spacing
		PdfPTable table = new PdfPTable(8);
				table.setWidthPercentage(100f);
			//	table.setWidths(new int[] { 3, 3, 3 });
				table.setSpacingBefore(8);
				
				// Setting the background color and padding
//				table.setBackgroundColor(CMYKColor.MAGENTA);
//				table.setPadding();
				
		table.addCell("ID");
		table.addCell("Citizen Name");
		table.addCell("Gender");
		table.addCell("Plan Status");
		table.addCell("Plan Name");
		table.addCell("PlanStart Date");
		table.addCell("plan End Date");
		table.addCell("Benefit Amt");

//		List<CitizenPlans> plans = planRepo.findAll();
		for (CitizenPlans plan : plans) {
			table.addCell(String.valueOf(plan.getCitizenId()));
			table.addCell(plan.getCitizenName());
			table.addCell(plan.getGender());
			if(null !=plan.getPlanStartDate()) {
				table.addCell(plan.getPlanStartDate() + "");				
			}else {
				table.addCell("N/A");
			}
			table.addCell(plan.getPlanStatus());
			table.addCell(plan.getPlanName());
			table.addCell(plan.getPlanEndDate() + "");
			if(null !=plan.getBenefitAmt()) {
				table.addCell(String.valueOf(plan.getBenefitAmt()));			
			}
			else {
				   table.addCell("N/A");
			}

		}

		document.add(table);
		document.close();
	}
}
