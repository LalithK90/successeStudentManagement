package lk.succes_student_management.asset.batch_exam.controller;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.util.service.MakeAutoGenerateNumberService;
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
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public BatchExamController(BatchService batchService, BatchExamService batchExamService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.batchService = batchService;
    this.batchExamService = batchExamService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
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
    model.addAttribute("batchExams", batch.getBatchExams());
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
    model.addAttribute("batchExams", batchService.findById(batchExam.getBatch().getId()).getBatchExams());
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
    if ( batchExam.getId() ==null ) {
      BatchExam lastBatchExam = batchExamService.lastBatchExamDB();
      if ( lastBatchExam != null ) {
        String lastNumber = lastBatchExam.getCode().substring(3);
        batchExam.setCode("SSB" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        batchExam.setCode("SSB" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
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
