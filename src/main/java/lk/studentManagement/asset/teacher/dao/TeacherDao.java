package lk.studentManagement.asset.teacher.dao;

import lk.studentManagement.asset.teacher.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherDao extends JpaRepository<Teacher, Integer> {
    Teacher findFirstByOrderByIdDesc();
}
