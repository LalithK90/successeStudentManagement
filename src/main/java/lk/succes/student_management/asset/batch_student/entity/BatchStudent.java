package lk.succes.student_management.asset.batch_student.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.payment.entity.Payment;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "BatchStudent" )
public class BatchStudent extends AuditEntity {

  @Enumerated( EnumType.STRING)
  private LiveDead liveDead;

  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Student student;

  @OneToMany(mappedBy = "batchStudent")
  private List< Payment > payments;
}
