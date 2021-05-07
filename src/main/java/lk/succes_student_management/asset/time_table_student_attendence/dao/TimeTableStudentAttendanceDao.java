package lk.succes_student_management.asset.time_table_student_attendence.dao;


import lk.succes_student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TimeTableStudentAttendanceDao extends JpaRepository< TimeTableStudentAttendance, Integer > {

  TimeTableStudentAttendance findFirstByOrderByIdDesc();

  List< TimeTableStudentAttendance> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);
}
