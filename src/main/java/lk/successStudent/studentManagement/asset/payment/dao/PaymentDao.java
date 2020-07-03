package lk.successStudent.studentManagement.asset.payment.dao;


import lk.successStudent.studentManagement.asset.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentDao extends JpaRepository<Payment, Integer> {

}
