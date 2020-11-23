package lk.succes.student_management.asset.subject.dao;


import lk.succes.student_management.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Integer> {

}
