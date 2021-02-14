package lk.succes.student_management.asset.batch_student_exam_result.dao;


import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BatchStudentExamResultDao extends JpaRepository< BatchStudentExamResult, Integer > {

  BatchStudentExamResult findFirstByOrderByIdDesc();

  List< BatchStudentExamResult > findByBatchStudentAndStartAtIsBetween(BatchStudent batch, LocalDateTime form, LocalDateTime to);

  List< BatchStudentExamResult > findByStartAtIsBetween(LocalDateTime from, LocalDateTime to);
}
