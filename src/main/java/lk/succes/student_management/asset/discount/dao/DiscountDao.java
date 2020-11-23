package lk.succes.student_management.asset.discount.dao;


import lk.succes.student_management.asset.discount.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscountDao extends JpaRepository<Discount, Integer> {
}
