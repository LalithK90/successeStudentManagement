package lk.succes_student_management.asset.batch_student_exam_result.controller;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import lk.succes_student_management.asset.batch_student_exam_result.service.BatchStudentExamResultService;
import lk.succes_student_management.asset.common_asset.model.enums.AttendanceStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping( "/batchStudentExamResult" )
public class BatchStudentExamResultController {
  private final BatchStudentExamResultService batchStudentExamResultService;
  private final BatchExamService batchExamService;
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;

  public BatchStudentExamResultController(BatchStudentExamResultService batchStudentExamResultService,
                                          BatchExamService batchExamService, BatchService batchService,
                                          BatchStudentService batchStudentService) {
    this.batchStudentExamResultService = batchStudentExamResultService;
    this.batchExamService = batchExamService;
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
  }

  @GetMapping( "/addAttendance/{id}" )
  public String addAttendance(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);

    List< BatchStudentExamResult > batchStudentExamResults = new ArrayList<>();

    batchStudentService.findByBatch(batchExam.getBatch()).forEach(x -> {
      BatchStudentExamResult batchStudentExamResult = new BatchStudentExamResult();
      batchStudentExamResult.setBatchStudent(x);
      batchStudentExamResult.setAttendanceStatus(AttendanceStatus.AB);
      batchStudentExamResult.setBatchExam(batchExam);
      batchStudentExamResults.add(batchStudentExamResult);
    });
    batchExam.setBatchStudentExamResults(batchStudentExamResults);
model.addAttribute("batchDetail", batchExam.getBatch());
    model.addAttribute("batchExam", batchExam);
    return "batchExamResult/addBatchExamResult";
  }

  @GetMapping( "/addAttendance/{id}" )
  public String addResult(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);
    model.addAttribute("batchExam", batchExam);
    model.addAttribute("batchDetail", batchExam.getBatch());
    model.addAttribute("addStatus", true);
    return "batchExamResult/addBatchExamResult";
  }

  @PostMapping
  public String save(@ModelAttribute BatchExam batchExam, BindingResult bindingResult) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/batchStudentExamResult/addAttendance/" + batchExam.getId();
    }

    return "redirect:/batchExam/teacher";
  }
}
