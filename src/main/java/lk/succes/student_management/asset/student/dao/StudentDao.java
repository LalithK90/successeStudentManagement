package lk.succes.student_management.asset.student.dao;


import lk.succes.student_management.asset.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentDao extends JpaRepository< Student, Integer > {

    Student findFirstByOrderByIdDesc();

}