package lk.succes_student_management.asset.batch_exam.controller;

import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/batchExam")
public class BatchExamController {

  private final BatchService batchService;

  private final BatchExamService batchExamService;

  public BatchExamController(BatchService batchService, BatchExamService batchExamService) {
    this.batchService = batchService;
    this.batchExamService = batchExamService;
  }


}
