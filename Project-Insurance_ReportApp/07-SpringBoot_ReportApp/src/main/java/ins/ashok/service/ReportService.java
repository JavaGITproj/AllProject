package ins.ashok.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import ins.ashok.Entity.CitizenPlans;
import ins.ashok.request.SearchRequest;

public interface ReportService {
	
	public List<String>getPlanName();
	public List<String>getPlanStatus();
	public List<CitizenPlans>search(SearchRequest request);
	public boolean exportExcel(HttpServletResponse response)throws Exception;
	public boolean exportPdf(HttpServletResponse response)throws Exception;

}
