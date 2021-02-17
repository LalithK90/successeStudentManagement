package lk.succes_student_management.asset.hall.dao;


import lk.succes_student_management.asset.hall.entity.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface HallDao extends JpaRepository<Hall, Integer> {

}
