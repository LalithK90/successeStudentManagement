package lk.successStudent.studentManagement.asset.discount.service;


import lk.successStudent.studentManagement.util.interfaces.AbstractService;
import lk.successStudent.studentManagement.asset.discount.dao.DiscountDao;
import lk.successStudent.studentManagement.asset.discount.entity.Discount;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService implements AbstractService<Discount,Integer> {
    private final DiscountDao discountDao;

    public DiscountService(DiscountDao discountDao) {
        this.discountDao = discountDao;
    }

    public List<Discount> findAll() {
        return discountDao.findAll();
    }

    public Discount findById(Integer id) {
        return discountDao.getOne(id);
    }

    public Discount persist(Discount discount) {
        return discountDao.save(discount);
    }

    public boolean delete(Integer id) {
        discountDao.deleteById(id);
        return false;
    }

    public List<Discount> search(Discount discount) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Discount> discountExample = Example.of(discount, matcher);
        return discountDao.findAll(discountExample);
    }
}
