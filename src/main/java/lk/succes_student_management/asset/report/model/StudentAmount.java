package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentAmount {
private Student student;
private BigDecimal amount;
private long count;
}
