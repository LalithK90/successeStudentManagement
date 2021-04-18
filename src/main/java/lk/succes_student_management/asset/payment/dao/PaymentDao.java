package lk.succes_student_management.asset.payment.dao;


import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.payment.entity.Payment;
import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.time.YearMonth;
import java.util.List;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

  Payment findFirstByOrderByIdDesc();

  Payment findByBatchStudentAndMonth(BatchStudent batchStudent, Month month);

  Payment findByMonthAndBatchStudentAndPaymentStatus(BatchStudent batchStudent, Month month, PaymentStatus noPaid);

  List< Payment> findByCreatedAtIsBetween(LocalDateTime startAt, LocalDateTime endAt);

  Payment findByBatchStudentAndMonthAndYear(BatchStudent batchStudent, Month month, Year year);
}
