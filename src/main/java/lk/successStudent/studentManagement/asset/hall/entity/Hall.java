package lk.successStudent.studentManagement.asset.hall.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Hall")
public class Hall extends AuditEntity {
    private String hallName;
    private String number;
    private String hallCondition;
    private String seatCount;
    private String HallLocation;




}