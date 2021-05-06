package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.teacher.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchAmount {
private Batch batch;
public Teacher teacher;
public Student student;
private BigDecimal amount;
private long count;
private BigDecimal fee;



}
