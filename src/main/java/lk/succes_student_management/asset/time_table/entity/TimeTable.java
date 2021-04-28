package lk.succes_student_management.asset.time_table.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.hall.entity.Hall;
import lk.succes_student_management.asset.time_table.entity.enums.TimeTableStatus;
import lk.succes_student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import lk.succes_student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "TimeTable" )
public class TimeTable extends AuditEntity {

  @Column( unique = true )
  private String code;

  private String lesson;

  private String remark;

  @Enumerated(EnumType.STRING)
  private TimeTableStatus timeTableStatus;

  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime startAt;

  @DateTimeFormat( pattern = "yyyy-MM-dd'T'HH:mm" )
  private LocalDateTime endAt;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Hall hall;

  @OneToMany( mappedBy = "timeTable" )
  private List< TimeTableStudentAttendance > timeTableStudentAttendances;
}
