package lk.succes.student_management.asset.payment.service;


import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.util.interfaces.AbstractService;
import lk.succes.student_management.asset.payment.dao.PaymentDao;
import lk.succes.student_management.asset.payment.entity.Payment;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.util.List;

@Service
public class PaymentService implements AbstractService< Payment, Integer > {
  private final PaymentDao paymentDao;

  public PaymentService(PaymentDao paymentDao) {
    this.paymentDao = paymentDao;
  }

  public List< Payment > findAll() {
    return paymentDao.findAll();
  }

  public Payment findById(Integer id) {
    return paymentDao.getOne(id);
  }

  public Payment persist(Payment payment) {
    if ( payment.getId() == null )
      payment.setLiveDead(LiveDead.ACTIVE);
    return paymentDao.save(payment);
  }

  public boolean delete(Integer id) {
    Payment payment = paymentDao.getOne(id);
    payment.setLiveDead(LiveDead.STOP);
    paymentDao.save(payment);
    return false;
  }

  public List< Payment > search(Payment payment) {
    return null;
  }


  public Payment lastStudentOnDB() {
    return paymentDao.findFirstByOrderByIdDesc();
  }

  public Payment findByMonthAndBatchStudent(Month month, BatchStudent batchStudent) {
    return paymentDao.findByBatchStudentAndMonth(batchStudent, month);
  }
}
