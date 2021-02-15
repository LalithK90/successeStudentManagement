package lk.succes_student_management.asset.batch_exam.dao;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BatchExamDao extends JpaRepository< BatchExam, Integer> {
  int countByBatch(Batch batch);

  BatchExam findFirstByOrderByIdDesc();

  List< BatchExam> findByBatch(Batch batch);
}
