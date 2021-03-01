package lk.succes_student_management.asset.batch_student.controller;

import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.student.service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    List< Batch > batches = new ArrayList<>();
    for ( Batch batch : batchList ) {
      batch.setCount(batchStudentService.countByBatch(batch));
      batches.add(batch);
    }
    model.addAttribute("batches", batches);
    return "batchStudent/batchStudent";
  }

  @GetMapping( "/batch/{id}" )
  public String studentAddBatch(@PathVariable( "id" ) Integer id, Model model) {
    common(id, model, true);
    return "batchStudent/addBatchStudent";
  }

  @PostMapping( "/removeBatch" )
  public String removeStudentFromBatch(@ModelAttribute BatchStudent batchStudent) {
    BatchStudent batchStudentDB = batchStudentService.findByStudentAndBatch(batchStudent.getStudent(),
                                                                            batchStudent.getBatch());
    batchStudentDB.setLiveDead(LiveDead.STOP);
    batchStudentService.persist(batchStudentDB);
    return "redirect:/batchStudent/batch/" + batchStudentDB.getBatch().getId();
  }

  @GetMapping( "/batch/student/{id}" )
  public String batchStudent(@PathVariable( "id" ) Integer id, Model model) {
    common(id, model, false);
    return "batchStudent/showStudent";
  }

  private void common(Integer id, Model model, boolean addStatus) {
    Batch batch = batchService.findById(id);
    model.addAttribute("batchDetail", batch);
    model.addAttribute("teacherDetail", batch.getTeacher());
    //already registered student on this batch
    List< Student > registeredStudent = new ArrayList<>();
    batch.getBatchStudents()
            .stream()
            .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
            .collect(Collectors.toList())
            .forEach(x -> registeredStudent.add(x.getStudent()));
    model.addAttribute("students", registeredStudent);
    model.addAttribute("studentRemoveBatch", true);


    if ( addStatus ) {
      model.addAttribute("student", new BatchStudent());
      //not registered student on this batch
      List< Student > notRegisteredStudent = studentService.findByGrade(batch.getGrade())
          .stream()
          .filter(x -> !registeredStudent.contains(x))
          .collect(Collectors.toList());
      model.addAttribute("notRegisteredStudent", notRegisteredStudent);
    }

  }

}
