package lk.successStudent.studentManagement.asset.timeTable.service;


import lk.successStudent.studentManagement.util.interfaces.AbstractService;
import lk.successStudent.studentManagement.asset.timeTable.dao.TimeTableDao;
import lk.successStudent.studentManagement.asset.timeTable.entity.TimeTable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeTableService implements AbstractService<TimeTable, Integer> {
    private final TimeTableDao timeTableDao;

    public TimeTableService(TimeTableDao timeTableDao) {
        this.timeTableDao = timeTableDao;
    }

    public List<TimeTable> findAll() {
        return timeTableDao.findAll();
    }

    public TimeTable findById(Integer id) {
        return timeTableDao.getOne(id);
    }

    public TimeTable persist(TimeTable timeTable) {
        return timeTableDao.save(timeTable);
    }

    public boolean delete(Integer id) {
        timeTableDao.deleteById(id);
        return false;
    }

    public List<TimeTable> search(TimeTable timeTable) {
        return null;
    }



}
