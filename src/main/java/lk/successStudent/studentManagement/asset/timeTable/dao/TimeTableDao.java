package lk.successStudent.studentManagement.asset.timeTable.dao;


import lk.successStudent.studentManagement.asset.timeTable.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeTableDao extends JpaRepository<TimeTable, Integer> {

}
