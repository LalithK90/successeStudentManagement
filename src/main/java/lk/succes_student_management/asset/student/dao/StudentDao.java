package lk.succes_student_management.asset.student.dao;


import lk.succes_student_management.asset.batch.entity.enums.Grade;
import lk.succes_student_management.asset.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StudentDao extends JpaRepository< Student, Integer > {

    Student findFirstByOrderByIdDesc();

  List< Student> findByGrade(Grade grade);

  List< Student> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
