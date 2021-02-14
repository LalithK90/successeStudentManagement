package lk.succes.student_management.asset.time_table_student_attendence.dao;


import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeTableStudentAttendanceDao extends JpaRepository< TimeTableStudentAttendance, Integer > {

  TimeTableStudentAttendance findFirstByOrderByIdDesc();

}
