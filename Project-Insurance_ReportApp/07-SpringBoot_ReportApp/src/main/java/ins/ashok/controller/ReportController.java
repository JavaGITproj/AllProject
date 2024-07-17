package ins.ashok.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import ins.ashok.Entity.CitizenPlans;
import ins.ashok.request.SearchRequest;
import ins.ashok.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;

	@GetMapping("/pdf")
	public void pdfExport(HttpServletResponse response) throws Exception {
		response.setContentType("application/pdf");
		response.addHeader("content-Disposition", "attachment;filename=plans.pdf");
		service.exportPdf(response);
	}
	@GetMapping("/excel")
	public void excelExport(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		response.addHeader("content-Disposition", "attachment;filename=plans.xls");
		service.exportExcel(response);
	}

	@PostMapping("/search")
	public String searchHandler(@ModelAttribute("search") SearchRequest request, Model model) {
		System.out.println(request);
		List<CitizenPlans> list = service.search(request);
		model.addAttribute("plans", list);
		initMethod(model);
		return "index";
	}

	@GetMapping("/")
	public String homePage(Model model) {
		// model.addAttribute("search",new SearchRequest());
		this.initMethod(model);
		return "index";
	}

	private void initMethod(Model model) {
		model.addAttribute("search", new SearchRequest());
		model.addAttribute("names", service.getPlanName());
		model.addAttribute("status", service.getPlanStatus());
	}

}
