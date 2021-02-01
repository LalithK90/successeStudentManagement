package lk.succes.student_management.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.enums.ClassDay;
import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.asset.hall.entity.Hall;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.asset.subject.entity.Subject;
import lk.succes.student_management.asset.teacher.entity.Teacher;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.swing.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Batch" )
public class Batch extends AuditEntity {

  @Column( unique = true )
  private String code;

  @Column( unique = true )
  private String name;

  private String year;

  @Enumerated( EnumType.STRING )
  private Grade grade;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;


  @Enumerated( EnumType.STRING )
  private ClassDay classDay;

  private String startAt;

  private String endAt;

  @ManyToOne
  private Teacher teacher;

  @OneToMany( mappedBy = "batch" )
  private List< BatchStudent > batchStudents;

  @OneToMany( mappedBy = "batch" )
  private List< TimeTable > timeTables;
}
