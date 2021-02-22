package lk.succes_student_management.asset.subject.service;


import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.interfaces.AbstractService;
import lk.succes_student_management.asset.subject.dao.SubjectDao;
import lk.succes_student_management.asset.subject.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SubjectService implements AbstractService<Subject, Integer> {
    private final SubjectDao subjectDao;

    public SubjectService(SubjectDao subjectDao) {
        this.subjectDao = subjectDao;
    }

    public List<Subject> findAll() {
        return subjectDao.findAll() .stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
    }

    public Subject findById(Integer id) {
        return subjectDao.getOne(id);
    }

    public Subject persist(Subject subject) {
        if(subject.getId() ==null){
            subject.setLiveDead(LiveDead.ACTIVE);
        }
        return subjectDao.save(subject);
    }

    public boolean delete(Integer id) {
        Subject subject = subjectDao.getOne(id);
        subject.setLiveDead(LiveDead.STOP);
        subjectDao.save(subject);
        return false;
    }

    public List<Subject> search(Subject subject) {
        return null;
    }



}
