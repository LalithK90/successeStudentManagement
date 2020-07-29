package lk.successStudent.studentManagement.asset.student.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.asset.batch.entity.Batch;
import lk.successStudent.studentManagement.asset.hall.entity.Hall;
import lk.successStudent.studentManagement.asset.subject.entity.Subject;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Student")
public class Student extends AuditEntity {

    private String regNo;

    private String firstName;

    private String lastName;

    private String gender;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    private String address;

    private String city;

    private String school;

    private String guardian;

    private String mobile;

    private String home;

    // TODO: 6/13/2020  add the student picture
    /*One particular batch*/
    @ManyToOn
    private Batch batch;

    @ManyToMany
    @JoinTable( name = "student_subject",
            joinColumns = @JoinColumn( name = "student_id" ),
            inverseJoinColumns = @JoinColumn( name = "subject_id" ) )
    private List<Subject> subjects;






}