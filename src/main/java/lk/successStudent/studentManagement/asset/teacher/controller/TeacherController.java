package lk.successStudent.studentManagement.asset.teacher.controller;

import lk.successStudent.studentManagement.asset.batch.service.BatchService;
import lk.successStudent.studentManagement.asset.commonAsset.model.Enum.Gender;
import lk.successStudent.studentManagement.asset.subject.service.SubjectService;
import lk.successStudent.studentManagement.asset.teacher.entity.Teacher;
import lk.successStudent.studentManagement.asset.teacher.service.TeacherService;
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/teacher")
public class TeacherController implements AbstractController<Teacher, Integer> {
    private final TeacherService teacherService;
    private final SubjectService subjectService;
    private final BatchService batchService;

    public TeacherController(TeacherService teacherService, SubjectService subjectService, BatchService batchService) {
        this.teacherService = teacherService;
        this.subjectService = subjectService;
        this.batchService = batchService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("teachers", teacherService.findAll());
        return "teacher/teacher";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("teacher", new Teacher());
        model.addAttribute("gender", Gender.values());
        model.addAttribute("addStatus",true);
        model.addAttribute("batches",batchService.findAll());
        model.addAttribute("subjects",subjectService.findAll());
        return "teacher/addTeacher";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("teacherDetail", teacherService.findById(id));
        return "teacher/teacher-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("teacher", teacherService.findById(id));
        model.addAttribute("gender", Gender.values());
        model.addAttribute("addStatus",false);
        model.addAttribute("batches",batchService.findAll());
        model.addAttribute("subjects",subjectService.findAll());
        return "teacher/addTeacher";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Teacher teacher, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("teacher", teacher);
            model.addAttribute("gender", Gender.values());
            model.addAttribute("addStatus",true);
            model.addAttribute("batches",batchService.findAll());
            model.addAttribute("subjects",subjectService.findAll());
            return "teacher/addTeacher";
        }
//todo-> teacher registration number need make
        teacherService.persist(teacher);
        return "redirect:/teacher";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        teacherService.delete(id);
        return "redirect:/teacher";
    }
}
