package lk.succes.student_management.asset.time_table_student_attendence.service;



import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.time_table_student_attendence.dao.TimeTableStudentAttendanceDao;
import lk.succes.student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import lk.succes.student_management.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableStudentAttendanceService implements AbstractService< TimeTableStudentAttendance, Integer > {
  private final TimeTableStudentAttendanceDao timeTableStudentAttendanceDao;

  public TimeTableStudentAttendanceService(TimeTableStudentAttendanceDao timeTableStudentAttendanceDao) {
    this.timeTableStudentAttendanceDao = timeTableStudentAttendanceDao;
  }

  public List< TimeTableStudentAttendance > findAll() {
    return timeTableStudentAttendanceDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public TimeTableStudentAttendance findById(Integer id) {
    return timeTableStudentAttendanceDao.getOne(id);
  }

  public TimeTableStudentAttendance persist(TimeTableStudentAttendance timeTableStudentAttendance) {
    if ( timeTableStudentAttendance.getId() == null ) {
      timeTableStudentAttendance.setLiveDead(LiveDead.ACTIVE);
    }
    return timeTableStudentAttendanceDao.save(timeTableStudentAttendance);
  }

  public boolean delete(Integer id) {
    TimeTableStudentAttendance timeTableStudentAttendance = timeTableStudentAttendanceDao.getOne(id);
    timeTableStudentAttendance.setLiveDead(LiveDead.STOP);
    timeTableStudentAttendanceDao.save(timeTableStudentAttendance);
    return false;
  }

  public List< TimeTableStudentAttendance > search(TimeTableStudentAttendance timeTableStudentAttendance) {
    return null;
  }

  public TimeTableStudentAttendance lastTimeTable() {
    return timeTableStudentAttendanceDao.findFirstByOrderByIdDesc();
  }

}
