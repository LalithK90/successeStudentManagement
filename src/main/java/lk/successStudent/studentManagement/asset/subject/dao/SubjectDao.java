package lk.successStudent.studentManagement.asset.subject.dao;


import lk.successStudent.studentManagement.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectDao extends JpaRepository<Subject, Integer> {

}
