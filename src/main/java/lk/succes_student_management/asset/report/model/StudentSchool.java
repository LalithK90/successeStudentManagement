package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.school.entity.School;
import lk.succes_student_management.asset.student.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSchool {
    private Student student;
    private long count;
    private School school;
}
