package lk.succes.student_management.asset.payment.dao;


import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Month;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

  Payment findFirstByOrderByIdDesc();

  Payment findByBatchStudentAndMonth(BatchStudent batchStudent, Month month);
}
