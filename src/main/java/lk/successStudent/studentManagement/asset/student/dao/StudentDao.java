package lk.successStudent.studentManagement.asset.student.dao;


import lk.successStudent.studentManagement.asset.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository< Student, Integer > {
    Student findFirstByOrderByIdDesc();
}
