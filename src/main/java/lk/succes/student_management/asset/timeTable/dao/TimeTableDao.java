package lk.succes.student_management.asset.timeTable.dao;


import lk.succes.student_management.asset.timeTable.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableDao extends JpaRepository<TimeTable, Integer> {

}
