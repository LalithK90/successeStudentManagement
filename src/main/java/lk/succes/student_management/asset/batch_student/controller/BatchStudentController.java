package lk.succes.student_management.asset.batch_student.controller;

import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.asset.batch_student.service.BatchStudentService;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/batchStudent" )
public class BatchStudentController {
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;


  public BatchStudentController(BatchService batchService, BatchStudentService batchStudentService) {
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
  }

  @GetMapping
  public String allActiveBatch(Model model) {
    List< Batch > batchList = batchService.findAll()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList());
    for ( Batch batch : batchList ) {
      batch.setCount(batchStudentService.countByBatch(batch));
      batchList.add(batch);
    }
    model.addAttribute("batches",batchList);
    return "batchStudent/batchStudent";
  }


}
