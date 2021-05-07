package lk.succes_student_management.asset.report.model;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BatchStudentAttendance {
  private Batch batch;
  private List< TimeTableStudentAttendance > timeTableStudentAttendances;
}
