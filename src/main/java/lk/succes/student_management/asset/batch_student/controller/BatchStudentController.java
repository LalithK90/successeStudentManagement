package lk.succes.student_management.asset.batch_student.controller;

import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.asset.batch_student.service.BatchStudentService;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/batchStudent" )
public class BatchStudentController {
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;
  private final StudentService studentService;


  public BatchStudentController(BatchService batchService, BatchStudentService batchStudentService,
                                StudentService studentService) {
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
    this.studentService = studentService;
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
  @GetMapping("/batch/{id}")
  public String studentAddBatch(@PathVariable("id")Integer id, Model model) {
    Batch batch = batchService.findById(id);
    //todo -> file
    model.addAttribute("students",studentService.findByGrade(batch.getGrade()));
    return "batchStudent/addBatchStudent";}


}
