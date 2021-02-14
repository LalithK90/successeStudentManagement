package lk.succes.student_management.asset.batch_exam.dao;

import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch_exam.entity.BatchExam;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchExamDao extends JpaRepository< BatchExam, Integer> {
  int countByBatch(Batch batch);

  List< BatchExam> findByBatch(Batch batch);
}
