package lk.successStudent.studentManagement.asset.timeTable.entity;


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
@JsonFilter( "TimeTable" )
public class TimeTable extends AuditEntity {

    private String date;
    private String time;
    private String slotId;
    private String teacher;
    private String hallNumber;


}