package lk.successStudent.studentManagement.asset.subject.entity;


import com.fasterxml.jackson.annotation.JsonFilter;

import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Subject" )
public class Subject extends AuditEntity {

    private String code;

    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Batch> batches;

    @ManyToMany(mappedBy = "subjects")
    private List<Student> students;

    @OneToMany(mappedBy = "subject")
    private List<Teacher> teachers;


}