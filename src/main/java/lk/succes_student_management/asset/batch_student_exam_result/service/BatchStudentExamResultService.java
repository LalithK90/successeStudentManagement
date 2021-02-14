package lk.succes_student_management.asset.batch_student_exam_result.service;



import lk.succes_student_management.asset.batch_student_exam_result.dao.BatchStudentExamResultDao;
import lk.succes_student_management.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.interfaces.AbstractService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BatchStudentExamResultService implements AbstractService< BatchStudentExamResult, Integer > {
  private final BatchStudentExamResultDao batchStudentExamResultDao;

  public BatchStudentExamResultService(BatchStudentExamResultDao batchStudentExamResultDao) {
    this.batchStudentExamResultDao = batchStudentExamResultDao;
  }

  public List< BatchStudentExamResult > findAll() {
    return batchStudentExamResultDao.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList());
  }

  public BatchStudentExamResult findById(Integer id) {
    return batchStudentExamResultDao.getOne(id);
  }

  public BatchStudentExamResult persist(BatchStudentExamResult batchStudentExamResult) {
    if ( batchStudentExamResult.getId() == null ) {
      batchStudentExamResult.setLiveDead(LiveDead.ACTIVE);
    }
    return batchStudentExamResultDao.save(batchStudentExamResult);
  }

  public boolean delete(Integer id) {
    BatchStudentExamResult batchStudentExamResult = batchStudentExamResultDao.getOne(id);
    batchStudentExamResult.setLiveDead(LiveDead.STOP);
    batchStudentExamResultDao.save(batchStudentExamResult);
    return false;
  }

  public List< BatchStudentExamResult > search(BatchStudentExamResult batchStudentExamResult) {
    return null;
  }


  public BatchStudentExamResult lastTimeTable() {
    return batchStudentExamResultDao.findFirstByOrderByIdDesc();
  }


}
