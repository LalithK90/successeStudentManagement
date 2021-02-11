package lk.succes.student_management.asset.time_table.dao;


import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeTableDao extends JpaRepository< TimeTable, Integer > {

  TimeTable findFirstByOrderByIdDesc();

  List<TimeTable> findByBatchAndStartAtIsBetween(Batch batch, LocalDateTime form, LocalDateTime to);

  List< TimeTable> findByStartAtIsBetween(LocalDateTime from, LocalDateTime to);
}
