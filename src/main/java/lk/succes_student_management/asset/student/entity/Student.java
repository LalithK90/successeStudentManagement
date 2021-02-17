package lk.succes_student_management.asset.student.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes_student_management.asset.batch.entity.enums.Grade;
import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.common_asset.model.enums.Gender;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.school.entity.School;
import lk.succes_student_management.util.audit.AuditEntity;
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

  @Enumerated( EnumType.STRING )
  private Grade grade;

  @ManyToOne
  private School school;

  @OneToMany(mappedBy = "student",cascade ={ CascadeType.MERGE, CascadeType.PERSIST})
  private List< BatchStudent > batchStudents;


}
