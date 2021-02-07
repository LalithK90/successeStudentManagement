package lk.succes.student_management.asset.student.service;


import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.student.dao.StudentDao;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService implements AbstractService< Student, Integer > {
    private final StudentDao studentDao;

    public StudentService(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public List< Student > findAll() {
        return studentDao.findAll();
    }

    public Student findById(Integer id) {
        return studentDao.getOne(id);
    }

    public Student persist(Student student) {
        if(student.getId()==null){
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

    public List< Student > search(Student student) {
        return null;
    }

    public Student lastStudentOnDB() {
        return studentDao.findFirstByOrderByIdDesc();
    }

  public List< Student > findByGrade(Grade grade) {
      return studentDao.findByGrade(grade);
  }
}
