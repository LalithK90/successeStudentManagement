package lk.successStudent.studentManagement.asset.school.entity;


<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/entity/School.java
import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
=======
import lombok.*;

import javax.persistence.*;
import java.util.List;
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/entity/school.java

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/entity/School.java
@JsonFilter( "School" )
public class School extends AuditEntity {

    private String schoolId;
    private String schoolName;
    private String district;

}
=======
@EqualsAndHashCode
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;


}
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/entity/school.java
