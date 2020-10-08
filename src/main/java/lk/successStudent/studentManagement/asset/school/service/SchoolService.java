package lk.successStudent.studentManagement.asset.school.service;


import lk.successStudent.studentManagement.asset.school.dao.SchoolDao;
import lk.successStudent.studentManagement.asset.school.entity.School;
import lk.successStudent.studentManagement.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/service/SchoolService.java
public class SchoolService implements AbstractService<School, Integer> {
    private final SchoolDao schoolDao;

    public SchoolService(SchoolDao schoolDao) {
        this.schoolDao = schoolDao;
    }

    public List<School> findAll() {
        return schoolDao.findAll();
    }

=======
public class SchoolService implements AbstractService< School, Integer> {

    private final SchoolDao schoolDao;
    @Autowired
    public SchoolService(SchoolDao schoolDao){
        this.schoolDao = schoolDao;
    }



    public List< School > findAll() {
        return schoolDao.findAll();
    }


>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/service/schoolService.java
    public School findById(Integer id) {
        return schoolDao.getOne(id);
    }

<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/service/SchoolService.java
=======

>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/service/schoolService.java
    public School persist(School school) {
        return schoolDao.save(school);
    }


    public boolean delete(Integer id) {
        schoolDao.deleteById(id);
        return false;
    }

<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/service/SchoolService.java
    public List<School> search(School school) {
        return null;
    }


=======
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/service/schoolService.java

    public List< School > search(School school) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example< School > sampleCollectingTubeExample = Example.of(school, matcher);
        return schoolDao.findAll(sampleCollectingTubeExample);
    }
}
