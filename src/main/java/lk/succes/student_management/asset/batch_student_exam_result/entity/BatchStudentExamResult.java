package lk.succes.student_management.asset.batch_student_exam_result.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.common_asset.model.enums.ResultGrade;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "BatchStudentExamResult" )
public class BatchStudentExamResult extends AuditEntity {

  @Column( unique = true )
  private String code;

  private String remark;

  private Integer mark;

  @Enumerated( EnumType.STRING )
  private ResultGrade resultGrade;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @Enumerated( EnumType.STRING )
  private AttendanceStatus attendanceStatus;

  @ManyToOne
  private BatchStudent batchStudent;

}
