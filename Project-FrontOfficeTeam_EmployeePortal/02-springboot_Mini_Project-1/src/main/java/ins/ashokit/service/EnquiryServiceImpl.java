package ins.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ins.ashokit.binding.DashboardResponse;
import ins.ashokit.binding.EnquiryForm;
import ins.ashokit.binding.EnquirySearchCriteria;
import ins.ashokit.constant.Appconstant;
import ins.ashokit.entity.CourseEntity;
import ins.ashokit.entity.EnqStatusEntity;
import ins.ashokit.entity.StudentEnqEntity;
import ins.ashokit.entity.UserdtlsEntity;
import ins.ashokit.repo.CourseRepo;
import ins.ashokit.repo.EnqStatusRepo;
import ins.ashokit.repo.StudentEnqRepo;
import ins.ashokit.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private HttpSession session;

	@Autowired
	private StudentEnqRepo studentRepo;

	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo enqStatusRepo;

	@Autowired
	private UserDtlsRepo userRepo;

	@Override
	public List<String> getCourseName() {

		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();

		for (CourseEntity entity : findAll) {

			names.add(entity.getCourseName());
		}

		return names;
	}

	@Override
	public List<String> getEnqStatus() {

		List<EnqStatusEntity> findAll = enqStatusRepo.findAll();

		List<String> statusList = new ArrayList<>();

		for (EnqStatusEntity entity : findAll) {

			statusList.add(entity.getStatusName());
		}

		return statusList;
	}

	@Override
	public DashboardResponse getDashboardData(Integer userid) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserdtlsEntity> findById = userRepo.findById(userid);

		if (findById.isPresent()) {
			UserdtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			Integer totalEnquiriescnt = enquiries.size();

			Integer enrolledcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals(Appconstant.STR_ENROLLED_COUNT))
					.collect(Collectors.toList()).size();

			Integer lostcnt = enquiries.stream().filter(e -> e.getEnqStatus().equals(Appconstant.STR_LOST_COUNT))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiries(totalEnquiriescnt);
			response.setEnrolledcnt(enrolledcnt);
			response.setLostcnt(lostcnt);

		}
		return response;
	}

	@Override
	public boolean upsertEnquiry(EnquiryForm form) {

		StudentEnqEntity Enqentity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, Enqentity);

		Integer userid = (Integer) session.getAttribute(Appconstant.STR_USER_ID);

		 Optional<UserdtlsEntity> findById = userRepo.findById(userid);
		 if(findById.isPresent()) {
			 UserdtlsEntity userdtlsEntity = findById.get();
			 Enqentity.setUser(userdtlsEntity);
		 }

		studentRepo.save(Enqentity);

		return true;
	}


	
	@Override
	public List<StudentEnqEntity> getEnquiry() {
		  Integer userid = (Integer)session.getAttribute(Appconstant.STR_USER_ID);		
		  Optional<UserdtlsEntity> findbyId = userRepo.findById(userid);
		  if(findbyId.isPresent()) {
			   UserdtlsEntity userdtlsEntity = findbyId.get();
			   List<StudentEnqEntity> enquiries = userdtlsEntity.getEnquiries();
			   return enquiries;
		  }
		return null;
	}
	
	
	
	

	@Override
	public List<StudentEnqEntity> getEnquiries() {
		 Integer userid = (Integer)session.getAttribute(Appconstant.STR_USER_ID);		
		  Optional<UserdtlsEntity> findbyId = userRepo.findById(userid);
		  if(findbyId.isPresent()) {
			   UserdtlsEntity userdtlsEntity = findbyId.get();
			   List<StudentEnqEntity> enquiries = userdtlsEntity.getEnquiries();
			   return enquiries;
		  }
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilterEnqs(EnquirySearchCriteria criteria,Integer userid) {
		
		Optional<UserdtlsEntity> findbyId = userRepo.findById(userid);
		  if(findbyId.isPresent()) {
			   UserdtlsEntity userdtlsEntity = findbyId.get();
			   List<StudentEnqEntity> enquiries = userdtlsEntity.getEnquiries();
			   
			   //filter logic
			   if(null !=criteria.getCourse()& !"".equals(criteria.getCourse())) {
				   enquiries=enquiries.stream()
				   .filter(e->e.getCourseName().equals(criteria.getCourse() ))
				   .collect(Collectors.toList());			   		  
			   }
			   if(null!=criteria.getEnquiryStatus()& !"".equals(criteria.getEnquiryStatus())) {
				   enquiries=enquiries.stream().filter(e->e.getEnqStatus().equals(criteria.getEnquiryStatus()))
				                     .collect(Collectors.toList());
			   }
			   if(null!=criteria.getClassMode()& !"".equals(criteria.getClassMode())) {
				   enquiries.stream().filter(e->e.getClassMode().equals(criteria.getClassMode()))
				                     .collect(Collectors.toList());
			   }
				   
			   
			   return enquiries;
	}
		  return null;
	
	

   }
}
















