package lk.successStudent.studentManagement.asset.student.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.asset.commonAsset.model.Enum.Gender;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Student" )
public class Student extends AuditEntity {
    private String registrationId;



    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @DateTimeFormat( pattern = "yyyy-MM-dd" )
    private LocalDate dateOfBirth;

    private String address;

    private String city;

    private String school;

    private String guardian;

    @Size( max = 10, message = "Mobile number length should be contained 10 and 9" )
    private String mobile;

// TODO: 6/5/2020  add the picture for profile picture here 

}
