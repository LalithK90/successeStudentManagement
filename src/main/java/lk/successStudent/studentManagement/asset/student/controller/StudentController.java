package lk.successStudent.studentManagement.asset.student.controller;


import lk.successStudent.studentManagement.asset.school.service.SchoolService;
import lk.successStudent.studentManagement.asset.student.entity.Student;
import lk.successStudent.studentManagement.asset.student.service.StudentService;
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
import lk.successStudent.studentManagement.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/student" )
public class StudentController implements AbstractController< Student, Integer > {
    private final StudentService studentService;

    private final SchoolService schoolService;
    private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

    public StudentController(StudentService studentService,
                             SchoolService schoolService, MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
        this.studentService = studentService;
        this.schoolService = schoolService;
        this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/student";
    }

    @GetMapping( "/new" )
    public String form(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("addStatus", true);
        return "student/addStudent";
    }

    @GetMapping( "/view/{id}" )
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("studentDetail", studentService.findById(id));
        return "student/student-detail";
    }

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("schools", schoolService.findAll());
        model.addAttribute("addStatus", false);
        return "student/addStudent";
    }

    @PostMapping( "/save" )
    public String persist(@Valid @ModelAttribute Student student, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if ( bindingResult.hasErrors() ) {
            model.addAttribute("student", student);
            model.addAttribute("schools", schoolService.findAll());
            model.addAttribute("addStatus", true);
            return "student/addStudent";
        }

//there are two different situation
        //1. new Student -> need to generate new number
        //2. update student -> no required to generate number
        if ( student.getId() == null ) {
            // need to create auto generated registration number
            Student lastStudent = studentService.lastStudentOnDB();
            //registration number format => SS200001
            if ( lastStudent != null ) {
                String lastNumber = lastStudent.getRegNo().substring(2);
                student.setRegNo("SS" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
            } else {
                student.setRegNo("SS" + makeAutoGenerateNumberService.numberAutoGen(null));
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
}
