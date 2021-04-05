package lk.succes_student_management.asset.payment.dao;


import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.payment.entity.Payment;
import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

  Payment findFirstByOrderByIdDesc();

  Payment findByBatchStudentAndMonth(BatchStudent batchStudent, Month month);

  Payment findByMonthAndBatchStudentAndPaymentStatus(BatchStudent batchStudent, Month month, PaymentStatus noPaid);
}
