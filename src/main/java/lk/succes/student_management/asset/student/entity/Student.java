package lk.succes.student_management.asset.student.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.common_asset.model.Enum.Gender;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.asset.school.entity.School;
import lk.succes.student_management.asset.subject.entity.Subject;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Student" )
public class Student extends AuditEntity {

  private String regNo;

  private String firstName;

  private String lastName;

  @Enumerated( EnumType.STRING )
  private Gender gender;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dob;

  private String address;

  private String city;

  private String guardian;

  private String mobile;

  private String home;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;


  // TODO: 6/13/2020  add the student picture
  /*One particular batch*/
  @ManyToOne
  private School school;

  @OneToMany
  private List< Batch > batches;


}
