package ins.ashok.service;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import ins.ashok.Entity.CitizenPlans;
import ins.ashok.repo.CitizenPlanRepository;
import ins.ashok.request.SearchRequest;
import ins.ashok.util.EmailGenerator;
import ins.ashok.util.ExcelGenerator;
import ins.ashok.util.PdfGenerator;

@Service
public class RportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;
	@Autowired
	private ExcelGenerator excelgenerator;
	@Autowired
	private PdfGenerator pdfgenerator;
	@Autowired
	private EmailGenerator emailutils;

	@Override
	public List<String> getPlanName() {
		return planRepo.getplanName();
	}

	@Override
	public List<String> getPlanStatus() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlans> search(SearchRequest request) {
		CitizenPlans entity = new CitizenPlans();
		if (request.getPlanName() != null && request.getPlanName() != "") {
			entity.setPlanName(request.getPlanName());
		}
		if (request.getPlanStatus() != null && request.getPlanStatus() != "") {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (request.getGender() != null && request.getGender() != "") {
			entity.setGender(request.getGender());
		}
		if (request.getStartDate() != null && request.getStartDate() != "") {
			String planstratdate = request.getStartDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localdate = LocalDate.parse(planstratdate, formatter);
			entity.setPlanStartDate(localdate);
		}
		if (request.getEndDate() != null && request.getEndDate() != "") {
			String planenddate = request.getEndDate();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localdate = LocalDate.parse(planenddate, formatter);
			entity.setPlanEndDate(localdate);
		}
		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {
		File f=new File("plans.xls");
		List<CitizenPlans> plans = planRepo.findAll();
		excelgenerator.generator(response, plans,f);
		String subject="Test email subject";
		String  body="<h1>Test email body</h1>";
		String to="anilbaral061@gmail.com";
		emailutils.sendemail(subject, body, to,f);
		f.delete();

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response) throws Exception {
		File f=new File("plans.pdf");
		List<CitizenPlans> plans = planRepo.findAll();
		pdfgenerator.generator(response, plans,f);
		
		String subject="Test email subject";
		String  body="<h1>Test email body</h1>";
		String to="anilbaral061@gmail.com";
		emailutils.sendemail(subject, body, to,f);
		f.delete();
		return true;
	}

}
