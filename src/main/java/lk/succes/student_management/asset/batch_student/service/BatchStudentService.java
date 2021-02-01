package lk.succes.student_management.asset.batch_student.service;
import lk.succes.student_management.asset.batch_student.dao.BatchStudentDao;
import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchStudentService implements AbstractService<BatchStudent, Integer> {
  private final BatchStudentDao batchStudentDao;

  public BatchStudentService(BatchStudentDao batchStudentDao) {
    this.batchStudentDao = batchStudentDao;
  }

  public List< BatchStudent > findAll() {
    return batchStudentDao.findAll();
  }

  public BatchStudent findById(Integer id) {
    return batchStudentDao.getOne(id);
  }

  public BatchStudent persist(BatchStudent batch) {
    if(batch.getId() ==null){
      batch.setLiveDead(LiveDead.ACTIVE);
    }
    return batchStudentDao.save(batch);
  }

  public boolean delete(Integer id) {
    BatchStudent batch = batchStudentDao.getOne(id);
    batch.setLiveDead(LiveDead.STOP);
    batchStudentDao.save(batch);
    return false;
  }

  public List<BatchStudent> search(BatchStudent batch) {
    return null;
  }



}
