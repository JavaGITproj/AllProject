package ins.ashok.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ins.ashok.Entity.CitizenPlans;

public interface CitizenPlanRepository  extends JpaRepository<CitizenPlans, Integer>{
	
	@Query("select distinct (planName) from CitizenPlans")
	public List<String>getplanName();
	
	@Query("select distinct(planStatus) from CitizenPlans")
	public List<String>getPlanStatus();

}
