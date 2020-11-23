package lk.succes.student_management.asset.batch.dao;


import lk.succes.student_management.asset.batch.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchDao extends JpaRepository<Batch, Integer> {

}
