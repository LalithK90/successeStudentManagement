package lk.succes_student_management.asset.hall.service;


import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.interfaces.AbstractService;
import lk.succes_student_management.asset.hall.dao.HallDao;
import lk.succes_student_management.asset.hall.entity.Hall;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HallService implements AbstractService<Hall, Integer> {
    private final HallDao hallDao;

    public HallService(HallDao hallDao) {
        this.hallDao = hallDao;
    }

    public List<Hall> findAll() {
        return hallDao.findAll();
    }

    public Hall findById(Integer id) {
        return hallDao.getOne(id);
    }

    public Hall persist(Hall hall) {
        if (hall.getId() == null){
            hall.setLiveDead(LiveDead.ACTIVE);
        }
        return hallDao.save(hall);
    }

    public boolean delete(Integer id) {
     Hall hall = hallDao.getOne(id);
     hall.setLiveDead(LiveDead.STOP);
     hallDao.save(hall);
        return false;
    }

    public List<Hall> search(Hall hall) {
        return null;
    }



}
