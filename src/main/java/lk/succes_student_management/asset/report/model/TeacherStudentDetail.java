package lk.succes_student_management.asset.report.model;

import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.teacher.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherStudentDetail {
private Teacher teacher;
private List< BatchStudent > batchStudents;

}
