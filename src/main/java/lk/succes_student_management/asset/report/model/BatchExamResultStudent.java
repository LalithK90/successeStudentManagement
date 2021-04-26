package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchExamResultStudent {
  private BatchExam batchExam;
  private Batch batch;
  private long attendCount;
  private List<Student> attendStudents;
  private long absentCount;
  private List< Student > absentStudents;
  private List< Student > aPlusStudents;
  private List< Student > aStudents;
  private List< Student > aMinusStudents;
  private List< Student > bPlusStudents;
  private List< Student > bStudents;
  private List< Student > bMinusStudents;
  private List< Student > cPlusStudents;
  private List< Student > cStudents;
  private List< Student > cMinusStudents;
  private List< Student > dPlusStudents;
  private List< Student > dStudents;
  private List< Student > eStudents;

}
