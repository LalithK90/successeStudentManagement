package lk.successStudent.studentManagement.asset.school.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "School" )
public class School extends AuditEntity {

    private String schoolId;

    private String name;}