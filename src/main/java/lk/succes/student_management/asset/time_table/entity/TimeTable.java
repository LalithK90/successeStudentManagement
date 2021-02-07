package lk.succes.student_management.asset.time_table.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.hall.entity.Hall;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "TimeTable" )
public class TimeTable extends AuditEntity {

  private String code;

  private String lesson;

  private String remark;

  private LocalDateTime startAt;

  private LocalDateTime endAt;

  @Enumerated( EnumType.STRING )
  private LiveDead liveDead;

  @ManyToOne
  private Batch batch;

  @ManyToOne
  private Hall hall;


}
