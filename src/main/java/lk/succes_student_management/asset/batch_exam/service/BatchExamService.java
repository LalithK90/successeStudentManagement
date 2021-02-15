package lk.succes_student_management.asset.batch_exam.service;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch_exam.dao.BatchExamDao;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchExamService implements AbstractService< BatchExam, Integer > {
  private final BatchExamDao batchExamDao;

  public BatchExamService(BatchExamDao batchExamDao) {
    this.batchExamDao = batchExamDao;
  }

  public List< BatchExam > findAll() {
    return batchExamDao.findAll();
  }

  public BatchExam findById(Integer id) {
    return batchExamDao.getOne(id);
  }

  public BatchExam persist(BatchExam batchExam) {
    if ( batchExam.getId() == null ) {
      batchExam.setLiveDead(LiveDead.ACTIVE);
    }
    return batchExamDao.save(batchExam);
  }

  public boolean delete(Integer id) {
    BatchExam batchExam = batchExamDao.getOne(id);
    batchExam.setLiveDead(LiveDead.STOP);
    batchExamDao.save(batchExam);
    return false;
  }

  public List< BatchExam > search(BatchExam batchExam) {
    return null;
  }


  public int countByBatch(Batch batch) {
    return batchExamDao.countByBatch(batch);
  }

  public BatchExam lastBatchExamDB() {
    return batchExamDao.findFirstByOrderByIdDesc();
  }

}
