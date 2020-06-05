package lk.successStudent.studentManagement.asset.student.controller;


import lk.successStudent.studentManagement.asset.commonAsset.model.Enum.Gender;
import lk.successStudent.studentManagement.asset.student.entity.Student;
import lk.successStudent.studentManagement.asset.student.service.StudentService;
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/student")
public class StudentController implements AbstractController<Student, Integer> {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("students", studentService.findAll());
        return "student/student";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("student", new Student());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("addStatus",true);
        return "student/addStudent";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("studentDetail", studentService.findById(id));
        return "student/student-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("student", studentService.findById(id));
        model.addAttribute("gender", Gender.values());
        model.addAttribute("addStatus",false);
        return "student/addStudent";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Student student, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("student", student);
            model.addAttribute("gender", Gender.values());
            model.addAttribute("addStatus",true);
            return "student/addStudent";
        }
//todo-> student registration number need make
        studentService.persist(student);
        return "redirect:/student";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        studentService.delete(id);
        return "redirect:/student";
    }
}
