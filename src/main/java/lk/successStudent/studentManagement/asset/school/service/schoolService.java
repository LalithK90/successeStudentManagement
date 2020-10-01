package lk.successStudent.studentManagement.asset.school.service;


import lk.successStudent.studentManagement.asset.school.entity.school;
import lk.successStudent.studentManagement.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class schoolService implements AbstractService<school, Integer> {
    private final lk.successStudent.studentManagement.asset.school.dao.schoolDao schoolDao;

    public schoolService(lk.successStudent.studentManagement.asset.school.dao.schoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public List<school> findAll() {
        return schoolDao.findAll();
    }

    public school findById(Integer id) {
        return schoolDao.getOne(id);
    }

    public school persist(school school) {
        return schoolDao.save(school);
    }

    public boolean delete(Integer id) {
        schoolDao.deleteById(id);
        return false;
    }

    public List<school> search(school school) {
        return null;
    }



}
