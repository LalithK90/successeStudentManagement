package lk.succes.student_management.asset.time_table.dao;


import lk.succes.student_management.asset.time_table.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableDao extends JpaRepository<TimeTable, Integer> {

}
