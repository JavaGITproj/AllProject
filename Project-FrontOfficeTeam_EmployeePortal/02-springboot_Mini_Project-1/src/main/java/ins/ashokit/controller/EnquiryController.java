package ins.ashokit.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;
import ins.ashokit.entity.StudentEnqEntity;
import ins.ashokit.repo.StudentEnqRepo;
import ins.ashokit.service.EnquiryService;
  
@Controller
public class EnquiryController {

	@Autowired
	private EnquiryService enquiryService;
	@Autowired
	private StudentEnqRepo studenEnqRepo;

	@Autowired
	private HttpSession session;

	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}

	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		// todo : to get fetch the dashboard data
		Integer userid = (Integer) session.getAttribute("userId");

		DashboardResponse dashboardData = enquiryService.getDashboardData(userid);

		model.addAttribute("dashboardData", dashboardData);

		return "dashboard";
	}

	@PostMapping("/addEnq")
	public String Savequirypage(@ModelAttribute("enqformObj") EnquiryForm enqformObj, Model model) {
		System.out.println(enqformObj);
		// todo:save data logic
		boolean status = enquiryService.upsertEnquiry(enqformObj);

		if (status) {
			model.addAttribute("succMsg", "Data Saved..");
		} else {
			model.addAttribute("errMsg", "some problem occured");
		}
		List<String> courseName = enquiryService.getCourseName();
		List<String> enqStatus = enquiryService.getEnqStatus();
		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		return "add-enquiry";
	}

	@GetMapping("/enquiry")
	public String enquiryPage(Model model) {
		// todo:get courses for dropdown mode

		initModelForm(model);
		return "add-enquiry";
	}

	private void initModelForm(Model model) {
		EnquiryForm enqformObj = new EnquiryForm();
		
		List<String> courseName = enquiryService.getCourseName();
		// todo:get equiry satus for dropdown mode
		List<String> enqStatus = enquiryService.getEnqStatus();
		// crate binding model obj
		model.addAttribute("courseName", courseName);
		model.addAttribute("enqStatus", enqStatus);
		model.addAttribute("enqformObj", enqformObj);
	}

	@GetMapping("/enquires")
	public String viewEnquiryPage(Model model) {
		  initModelForm(model);
		 model.addAttribute("searchForm",new EnquirySearchCriteria());
		 List<StudentEnqEntity> enquiries = enquiryService.getEnquiries();
		 model.addAttribute("enquiries",enquiries);
		return "view-enquiries";
	}

	@GetMapping("/filter-enquiries")
	public String getFiltersEnq(@RequestParam String cname,@RequestParam String status,@RequestParam String mode ,Model model) {
	
		EnquirySearchCriteria criteria=new EnquirySearchCriteria();
		criteria.setCourse(cname);
		criteria.setClassMode(mode);
		criteria.setEnquiryStatus(status);
//		System.out.println(criteria);
		Integer userid = (Integer) session.getAttribute("userId");
		
		List<StudentEnqEntity> filterEnqs = enquiryService.getFilterEnqs(criteria, userid);
		
		model.addAttribute("enquiries",filterEnqs);
		
		return "view-filter-enquiries";
				
	}
	
	
	@GetMapping("/edit")
	public String edit(@RequestParam("enqid")Integer enqid, Model model) {
		
		//System.out.println(enqid);
		Optional<StudentEnqEntity> findById = studenEnqRepo.findById(enqid);
		if(findById.isPresent()) {
			StudentEnqEntity studentEnqEntity = findById.get();
			model.addAttribute("enqformObj",studentEnqEntity);

			List<String> courseName = enquiryService.getCourseName();
			// todo:get equiry satus for dropdown mode
			List<String> enqStatus = enquiryService.getEnqStatus();
			// crate binding model obj
			model.addAttribute("courseName", courseName);
			model.addAttribute("enqStatus", enqStatus);
		}
		
		return "add-enquiry";
	}
//	@PostMapping("/edit")
//	public String edit(@ModelAttribute("enqformObj") EnquiryForm enqformObj,Model model) {
//		
//		enquiryService.upsertEnquiry(enqformObj);
//		
//		return "add-enquiry";
//		 
//	}
}















