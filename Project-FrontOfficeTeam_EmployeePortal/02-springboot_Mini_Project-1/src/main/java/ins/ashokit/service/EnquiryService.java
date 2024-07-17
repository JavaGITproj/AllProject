package ins.ashokit.service;

import java.util.List;

import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;
import ins.ashokit.entity.StudentEnqEntity;

public interface EnquiryService {
	
   public List<String> getCourseName();
   
   public List<String> getEnqStatus();

   public DashboardResponse getDashboardData(Integer userid);
   
   public boolean upsertEnquiry(EnquiryForm form);
   
   public List<StudentEnqEntity> getEnquiry();
   
   public List<StudentEnqEntity> getEnquiries( );
   
   public List<StudentEnqEntity>getFilterEnqs(EnquirySearchCriteria criteria,Integer userid);
}
