package lk.succes.student_management.asset.payment.service;


import lk.succes.student_management.util.interfaces.AbstractService;
import lk.succes.student_management.asset.payment.dao.PaymentDao;
import lk.succes.student_management.asset.payment.entity.Payment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService implements AbstractService<Payment, Integer> {
    private final PaymentDao paymentDao;

    public PaymentService(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;
    }

    public List<Payment> findAll() {
        return paymentDao.findAll();
    }

    public Payment findById(Integer id) {
        return paymentDao.getOne(id);
    }

    public Payment persist(Payment payment) {
        return paymentDao.save(payment);
    }

    public boolean delete(Integer id) {
        paymentDao.deleteById(id);
        return false;
    }

    public List<Payment> search(Payment payment) {
        return null;
    }



}
