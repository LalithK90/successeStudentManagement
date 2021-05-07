package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.teacher.entity.Teacher;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherBatch {
    private Teacher teacher;
    private Batch batch;
    long Count;
}
