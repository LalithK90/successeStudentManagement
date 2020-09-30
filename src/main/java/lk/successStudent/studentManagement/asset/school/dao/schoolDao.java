package lk.successStudent.studentManagement.asset.school.dao;


import lk.successStudent.studentManagement.asset.school.entity.school;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface schoolDao extends JpaRepository<school, Integer> {

}
