package lk.successStudent.studentManagement.asset.batch.entity;


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
@JsonFilter( "Group" )
public class Batch extends AuditEntity {

    private String classReferance;
    private String grade;
    private String year;
    private String subjectName;
    private String teacherName;

}