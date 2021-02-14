package lk.succes_student_management.asset.student.controller;


import lk.succes_student_management.asset.batch.controller.BatchController;
import lk.succes_student_management.asset.batch.entity.enums.Grade;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.common_asset.model.enums.Gender;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.school.service.SchoolService;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.student.service.StudentService;
import lk.succes_student_management.util.interfaces.AbstractController;
import lk.succes_student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/student" )
public class StudentController implements AbstractController< Student, Integer > {
  private final StudentService studentService;
  private final BatchStudentService batchStudentService;
  private final SchoolService schoolService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public StudentController(StudentService studentService,
                           BatchStudentService batchStudentService, SchoolService schoolService,
                           MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.studentService = studentService;
    this.batchStudentService = batchStudentService;
    this.schoolService = schoolService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("students", studentService.findAll()
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList()));
    model.addAttribute("studentRemoveBatch", false);
    return "student/student";
  }

  private String commonThing(Model model, Student student, boolean addStatus) {
    model.addAttribute("student", student);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("grades", Grade.values());
    model.addAttribute("liveDeads", LiveDead.values());
    model.addAttribute("schools", schoolService.findAll().stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList()));
    model.addAttribute("gender", Gender.values());
    model.addAttribute("batchUrl", MvcUriComponentsBuilder
        .fromMethodName(BatchController.class, "findByGrade", "")
        .build()
        .toString());
    return "student/addStudent";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    return commonThing(model, new Student(), true);
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("studentDetail", studentService.findById(id));
    return "student/student-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonThing(model, studentService.findById(id), false);
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Student student, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonThing(model, student, true);
    }

    student.getBatchStudents().forEach(batchStudentService::persist);
//there are two different situation
    //1. new Student -> need to generate new number
    //2. update student -> no required to generate number
    if ( student.getId() == null ) {
      // need to create auto generated registration number
      Student lastStudent = studentService.lastStudentOnDB();
      //registration number format => SSS200001
      if ( lastStudent != null ) {
        String lastNumber = lastStudent.getRegNo().substring(3);
        student.setRegNo("SSS" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        student.setRegNo("SSS" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
    }
    studentService.persist(student);
    return "redirect:/student";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    studentService.delete(id);
    return "redirect:/student";
  }

  @PostMapping("/search")
  public String search(@ModelAttribute Student student, Model model) {
    List< Student > students = studentService.search(student);

    if ( students.isEmpty() ) {
      model.addAttribute("student", true);
      return "student/studentChooser";
    } else if ( students.size() == 1 ) {
      return "redirect:/payment/add/" + students.get(0).getId();
    } else {
      model.addAttribute("students", students);
      return "student/studentChooser";
    }
  }
}
