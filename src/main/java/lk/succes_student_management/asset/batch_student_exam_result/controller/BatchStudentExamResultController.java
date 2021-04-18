package lk.succes_student_management.asset.batch_student_exam_result.controller;

import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.batch_exam.entity.enums.ExamStatus;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import lk.succes_student_management.asset.batch_student_exam_result.service.BatchStudentExamResultService;
import lk.succes_student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes_student_management.asset.common_asset.model.enums.ResultGrade;
import lk.succes_student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/batchStudentExamResult" )
public class BatchStudentExamResultController {
  private final BatchStudentExamResultService batchStudentExamResultService;
  private final BatchExamService batchExamService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;

  public BatchStudentExamResultController(BatchStudentExamResultService batchStudentExamResultService,
                                          BatchExamService batchExamService,
                                          MakeAutoGenerateNumberService makeAutoGenerateNumberService,
                                          BatchService batchService,
                                          BatchStudentService batchStudentService) {
    this.batchStudentExamResultService = batchStudentExamResultService;
    this.batchExamService = batchExamService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
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
    model.addAttribute("attendanceStatuses", AttendanceStatus.values());
    model.addAttribute("addStatus", false);
    return "batchExamResult/addBatchExamResult";
  }

  @GetMapping( "/addResult/{id}" )
  public String addResult(@PathVariable Integer id, Model model) {
    BatchExam batchExam = batchExamService.findById(id);
    List< BatchStudentExamResult > batchStudentExamResults = batchExam.getBatchStudentExamResults()
        .stream()
        .filter(x -> x.getAttendanceStatus().equals(AttendanceStatus.PRE))
        .collect(Collectors.toList());
    batchExam.setBatchStudentExamResults(batchStudentExamResults);
    model.addAttribute("batchExam", batchExam);
    model.addAttribute("batchDetail", batchExam.getBatch());
    model.addAttribute("resultGrades", ResultGrade.values());
    model.addAttribute("attendanceStatuses", AttendanceStatus.values());
    model.addAttribute("addStatus", true);
    return "batchExamResult/addBatchExamResult";
  }

  @PostMapping
  public String save(@ModelAttribute BatchExam batchExam, BindingResult bindingResult) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/batchStudentExamResult/addAttendance/" + batchExam.getId();
    }
    batchExam.getBatchStudentExamResults().forEach(x -> {
      if ( x.getId() == null ) {
        BatchStudentExamResult lastBatchStudentExamResult =
            batchStudentExamResultService.lastBatchStudentExamResultDB();
        if ( lastBatchStudentExamResult != null ) {
          String lastNumber = lastBatchStudentExamResult.getCode().substring(4);
          x.setCode("SSER" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
        } else {
          x.setCode("SSER" + makeAutoGenerateNumberService.numberAutoGen(null));
        }
      }
      batchStudentExamResultService.persist(x);
    });
  BatchExam batchExamDb =   batchExamService.findById(batchExam.getId());
  batchExamDb.setExamStatus(batchExam.getExamStatus());
  batchExamService.persist(batchExamDb);
    return "redirect:/batchExam/teacher";
  }
}
