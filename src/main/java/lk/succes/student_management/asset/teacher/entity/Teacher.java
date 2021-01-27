package lk.succes.student_management.asset.teacher.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.common_asset.model.Enum.Gender;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.asset.subject.entity.Subject;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Teacher" )
public class Teacher extends AuditEntity {
  private String RegistrationId;

  @Size( max = 12, min = 10, message = "NIC number is contained numbers between 9 and X/V or 12 " )
  @Column( unique = true )
  private String nic;

  @Enumerated( EnumType.STRING )
  private Gender gender;

  @DateTimeFormat( pattern = "yyyy-MM-dd" )
  private LocalDate dateOfBirth;

  private String firstName;

  private String lastName;

  private String address;

  @Column( unique = true )
  private String email;

  @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
  private String mobile;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  private BigDecimal fee;

  /*One particular batch*/
  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Subject subject;

  @OneToMany( mappedBy = "teacher" )
  private List< TimeTable > timeTables;

}
