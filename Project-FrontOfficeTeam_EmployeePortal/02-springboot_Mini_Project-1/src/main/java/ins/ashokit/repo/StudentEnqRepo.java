package ins.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ins.ashokit.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
