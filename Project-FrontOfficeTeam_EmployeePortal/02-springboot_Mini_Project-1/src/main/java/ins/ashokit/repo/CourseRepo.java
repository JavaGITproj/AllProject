package ins.ashokit.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import ins.ashokit.entity.CourseEntity;

public interface CourseRepo  extends JpaRepository<CourseEntity, Integer>{

}
