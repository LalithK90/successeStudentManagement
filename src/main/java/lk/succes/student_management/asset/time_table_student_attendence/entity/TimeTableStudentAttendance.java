package lk.succes.student_management.asset.time_table_student_attendence.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "TimeTableStudentAttendance" )
public class TimeTableStudentAttendance extends AuditEntity {

  @Column( unique = true )
  private String code;

  @Enumerated( EnumType.STRING )
  private AttendanceStatus attendanceStatus;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private BatchStudent batchStudent;

  @ManyToOne
  private TimeTable timeTable;
}
