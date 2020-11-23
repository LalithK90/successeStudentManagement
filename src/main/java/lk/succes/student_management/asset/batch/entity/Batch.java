package lk.succes.student_management.asset.batch.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.hall.entity.Hall;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.asset.subject.entity.Subject;
import lk.succes.student_management.asset.teacher.entity.Teacher;
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

    private String year;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    @ManyToOne
    private Subject subject;

    @ManyToOne
    private Teacher teacher;

//so many student on one batch
    @OneToMany(mappedBy = "batch")
    private List<Student> students;

    @ManyToMany
    @JoinTable( name = "batch_hall",
            joinColumns = @JoinColumn( name = "batch_id" ),
            inverseJoinColumns = @JoinColumn( name = "hall_id" ) )
    private List<Hall> halls;

}