package lk.succes.student_management.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.hall.entity.Hall;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.asset.subject.entity.Subject;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Batch" )
public class Batch extends AuditEntity {

    private String classReferance;
    @Enumerated(EnumType.STRING)
    private Grade grade;
    private String year;
    private String subjectName;
    private String teacherName;

    @ManyToOne
    private Subject subject;

//so many student on one batch
    @OneToMany(mappedBy = "batch")
    private List<Student> students;

    @ManyToMany
    @JoinTable( name = "batch_hall",
            joinColumns = @JoinColumn( name = "batch_id" ),
            inverseJoinColumns = @JoinColumn( name = "hall_id" ) )
    private List<Hall> halls;

}