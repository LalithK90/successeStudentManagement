package lk.succes_student_management.asset.student.service;


import lk.succes_student_management.asset.batch.entity.enums.Grade;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.student.dao.StudentDao;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.util.interfaces.AbstractService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Service
public class StudentService implements AbstractService< Student, Integer > {
    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public List<Student> findAll() {
        return studentDao.findAll();
    }

    public Student findById(Integer id) {
        return studentDao.getOne(id);
    }

    public Student persist(Student student) {
        if (student.getId() == null) {
            student.setLiveDead(LiveDead.ACTIVE);
        }
        return studentDao.save(student);
    }

    public boolean delete(Integer id) {
        Student student = studentDao.getOne(id);
        student.setLiveDead(LiveDead.STOP);
        studentDao.save(student);
        return false;
    }

    public List<Student> search(Student student) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Student> studentExample = Example.of(student, matcher);
        return studentDao.findAll(studentExample);
    }

    public Collection<Object> findByGrade(Grade batchGrade) {
        return null;
    }

    public Student lastStudentOnDB() {
        return null;
    }

  public List< Student> findByCreatedAtIsBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return studentDao.findByCreatedAtIsBetween(startDateTime, endDateTime);
  }
}
