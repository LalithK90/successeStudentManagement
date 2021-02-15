package lk.succes_student_management.asset.batch_exam.controller;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/batchExam" )
public class BatchExamController {

  private final BatchService batchService;

  private final BatchExamService batchExamService;

  public BatchExamController(BatchService batchService, BatchExamService batchExamService) {
    this.batchService = batchService;
    this.batchExamService = batchExamService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("batchExams",
                       batchExamService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "batchExam/batchExam";
  }

  @GetMapping( "/teacher" )
  public String findByTeacher(Model model) {
    //todo current logging
    model.addAttribute("batchExams",
                       batchExamService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "batchExam/batchExam";
  }

  @GetMapping( "/add/{id}" )
  public String addForm(@PathVariable Integer id, Model model) {
    Batch batch = batchService.findById(id);
    model.addAttribute("batchDetail", batch);
    BatchExam batchExam = new BatchExam();
    batchExam.setBatch(batch);
    batchExam.setStartAt(LocalDateTime.now());

    model.addAttribute("batchExam", batchExam);
    return "batchExam/addBatchExam";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("batchExamDetail", batchService.findById(id));
    return "batchExam/batchExam-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);
    model.addAttribute("batchDetail", batchExam.getBatch());
    model.addAttribute("batchExam", batchExam);
    model.addAttribute("addStatus", true);
    return "batchExam/addBatchExam";
  }


  @PostMapping
  public String save(@ModelAttribute BatchExam batchExam, BindingResult bindingResult) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/batchExam/add" + batchExam.getBatch().getId();
    }
    batchExamService.persist(batchExam);
    return "redirect:/batchExam/teacher";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id) {
    batchExamService.delete(id);
    return "redirect:/batchExam/teacher";
  }
}
