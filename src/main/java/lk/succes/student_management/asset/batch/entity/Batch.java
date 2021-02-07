package lk.succes.student_management.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.enums.ClassDay;
import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.teacher.entity.Teacher;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

  @ManyToOne(fetch = FetchType.EAGER)
  private Teacher teacher;

  @OneToMany( mappedBy = "batch" )
  private List< BatchStudent > batchStudents;

  @OneToMany( mappedBy = "batch" )
  private List< TimeTable > timeTables;

  @Transient
  private int count;
}
