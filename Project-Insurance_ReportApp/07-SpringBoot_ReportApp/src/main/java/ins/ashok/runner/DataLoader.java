package ins.ashok.runner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ins.ashok.Entity.CitizenPlans;
import ins.ashok.repo.CitizenPlanRepository;

@Component
public class DataLoader  implements ApplicationRunner {
	
	@Autowired
	private CitizenPlanRepository repo;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		  repo.deleteAll();
	      
	      //cash plan approved
		  CitizenPlans c1=new CitizenPlans();
		  c1.setCitizenName("smith");
		  c1.setGender("Male");
		  c1.setPlanName("Cash Plan");
		  c1.setPlanStatus("Approved");
		  c1.setPlanStartDate(LocalDate.now());
		  c1.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c1.setBenefitAmt(5000.00);
		  
		  //cash plan denied
		  CitizenPlans c2=new CitizenPlans();
		  c2.setCitizenName("Cathy");
		  c2.setGender("Fe-Male");
		  c2.setPlanName("Cash Plan");
		  c2.setPlanStatus("Denied");
		  c2.setDenialReason("Rental Income");

		  // Cash plan Terminated
		  CitizenPlans c3=new CitizenPlans();
		  c3.setCitizenName("john");
		  c3.setGender("Male");
		  c3.setPlanName("Cash Plan");
		  c3.setPlanStatus("Terminated");
		  c3.setPlanStartDate(LocalDate.now().minusMonths(4));
		  c3.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c3.setBenefitAmt(4000.00);
		  
		  //Food plan approved
		  CitizenPlans c4=new CitizenPlans();
		  c4.setCitizenName("Borish");
		  c4.setGender("Male");
		  c4.setPlanName("Food Plan");
		  c4.setPlanStatus("Approved");
		  c4.setPlanStartDate(LocalDate.now());
		  c4.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c4.setBenefitAmt(5000.00);
		  
		  //Food plan denied
		  CitizenPlans c5=new CitizenPlans();
		  c5.setCitizenName("Sandy");
		  c5.setGender("Fe-Male");
		  c5.setPlanName("Food Plan");
		  c5.setPlanStatus("Denied");
		  c5.setDenialReason("Property Income");
		  
		  // Food Terminated
		  CitizenPlans c6=new CitizenPlans();
		  c6.setCitizenName("Mike");
		  c6.setGender("Male");
		  c6.setPlanName("Food Plan");
		  c6.setPlanStatus("Terminated");
		  c6.setPlanStartDate(LocalDate.now().minusMonths(4));
		  c6.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c6.setBenefitAmt(4000.00);
		  
		  
		  //medical plan approved
		  CitizenPlans c7=new CitizenPlans();
		  c7.setCitizenName("Galgot");
		  c7.setGender("Fe-Male");
		  c7.setPlanName("Medical Plan");
		  c7.setPlanStatus("Approved");
		  c7.setPlanStartDate(LocalDate.now());
		  c7.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c7.setBenefitAmt(5000.00);
		  
		  //medical plan denied
		  CitizenPlans c8=new CitizenPlans();
		  c8.setCitizenName("Sunny");
		  c8.setGender("Fe-Male");
		  c8.setPlanName("Medical Plan");
		  c8.setPlanStatus("Denied");
		  c8.setDenialReason("Business");
		  
		  // Medical plan Terminated
		  CitizenPlans c9=new CitizenPlans();
		  c9.setCitizenName("Tomcurise");
		  c9.setGender("Male");
		  c9.setPlanName("Medical Plan");
		  c9.setPlanStatus("Terminated");
		  c9.setPlanStartDate(LocalDate.now().minusMonths(4));
		  c9.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c9.setBenefitAmt(4000.00);
		  
		  //Employment plan approved
		  CitizenPlans c10=new CitizenPlans();
		  c10.setCitizenName("Robert");
		  c10.setGender("Male");
		  c10.setPlanName("Employment Plan");
		  c10.setPlanStatus("Approved");
		  c10.setPlanStartDate(LocalDate.now());
		  c10.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c10.setBenefitAmt(5000.00);
		  
		  //Employment plan denied
		  CitizenPlans c11=new CitizenPlans();
		  c11.setCitizenName("jackie");
		  c11.setGender("Fe-Male");
		  c11.setPlanName("Employment Plan");
		  c11.setPlanStatus("Denied");
		  c11.setDenialReason("Rental Income");
		  
		  // Employment plan Terminated
		  CitizenPlans c12=new CitizenPlans();
		  c12.setCitizenName("Amit");
		  c12.setGender("Male");
		  c12.setPlanName("Employment Plan");
		  c12.setPlanStatus("Terminated");
		  c12.setPlanStartDate(LocalDate.now().minusMonths(4));
		  c12.setPlanEndDate(LocalDate.now().plusMonths(6));
		  c12.setBenefitAmt(4000.00);
		  
		 List<CitizenPlans>list= Arrays.asList(c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12)	;
		 
		 repo.saveAll(list);
		 
		 
	}

}
