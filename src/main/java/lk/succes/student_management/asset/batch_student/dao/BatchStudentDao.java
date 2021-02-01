package lk.succes.student_management.asset.batch_student.dao;

import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchStudentDao extends JpaRepository< BatchStudent, Integer> {
}
