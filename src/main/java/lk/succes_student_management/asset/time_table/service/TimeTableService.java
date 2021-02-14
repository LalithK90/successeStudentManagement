package lk.succes_student_management.asset.time_table.service;


import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.interfaces.AbstractService;
import lk.succes_student_management.asset.time_table.dao.TimeTableDao;
import lk.succes_student_management.asset.time_table.entity.TimeTable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TimeTableService implements AbstractService< TimeTable, Integer > {
  private final TimeTableDao timeTableDao;

  public TimeTableService(TimeTableDao timeTableDao) {
    this.timeTableDao = timeTableDao;
  }

  public List< TimeTable > findAll() {
    return timeTableDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public TimeTable findById(Integer id) {
    return timeTableDao.getOne(id);
  }

  public TimeTable persist(TimeTable timeTable) {
    if ( timeTable.getId() == null ) {
      timeTable.setLiveDead(LiveDead.ACTIVE);
    }
    return timeTableDao.save(timeTable);
  }

  public boolean delete(Integer id) {
    TimeTable timeTable = timeTableDao.getOne(id);
    timeTable.setLiveDead(LiveDead.STOP);
    timeTableDao.save(timeTable);
    return false;
  }

  public List< TimeTable > search(TimeTable timeTable) {
    return null;
  }


  public TimeTable lastTimeTable() {
    return timeTableDao.findFirstByOrderByIdDesc();
  }

  public boolean availableTimeTableCheck(LocalDateTime from, LocalDateTime to, Batch batch) {
    List< TimeTable > timeTables = timeTableDao.findByBatchAndStartAtIsBetween(batch, from, to);
    return timeTables.isEmpty();
  }

  public List< TimeTable> findByCreatedAtIsBetween(LocalDateTime from, LocalDateTime to) {
  return timeTableDao.findByStartAtIsBetween(from,to);
  }
}
