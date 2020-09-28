package lk.successStudent.studentManagement.asset.school.service;


import lk.successStudent.studentManagement.asset.school.dao.SchoolDao;
import lk.successStudent.studentManagement.asset.school.entity.School;
import lk.successStudent.studentManagement.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchoolService implements AbstractService<School,Integer> {
    private SchoolDao schoolDao;

    public void SchoolDao(SchoolDao schoolDaoDao) {
        this.schoolDao = schoolDao;
    }

    public SchoolService(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public List<School> findAll() {
        return schoolDao.findAll();
    }

    public School findById(Integer id) {
        return schoolDao.getOne(id);
    }

    public School persist(School school) {
        return schoolDao.save(school);
    }

    public boolean delete(Integer id) {
        schoolDao.deleteById(id);
        return false;
    }

    @Override
    public List<School> search(School school) {
        return null;
    }

}
