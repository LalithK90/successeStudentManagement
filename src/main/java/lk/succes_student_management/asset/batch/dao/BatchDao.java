package lk.succes_student_management.asset.batch.dao;


import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.entity.enums.ClassDay;
import lk.succes_student_management.asset.batch.entity.enums.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;


@Repository
public interface BatchDao extends JpaRepository< Batch, Integer > {

  Batch findFirstByOrderByIdDesc();

  Batch findByName(String name);

  List< Batch > findByGrade(Grade grade);

  List< Batch > findByClassDay(ClassDay classDay);

  Batch findByYearAndClassDayAndStartAtIsBetweenAndEndAtIsBetween(String year, ClassDay classDay, LocalTime startAt,
                                                                  LocalTime endAt, LocalTime startAt1, LocalTime endAt1);
}
