package lk.succes.student_management.asset.time_table_student_attendence.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AttendanceStatus {
  PRE("Present"),
  AB("Absent");

  private final String attendanceStatus;
}
