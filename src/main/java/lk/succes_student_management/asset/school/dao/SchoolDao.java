package lk.succes_student_management.asset.school.dao;


import lk.succes_student_management.asset.school.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SchoolDao extends JpaRepository< School, Integer> {
}
