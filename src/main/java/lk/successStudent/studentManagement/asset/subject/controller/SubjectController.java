package lk.successStudent.studentManagement.asset.subject.controller;


import lk.successStudent.studentManagement.asset.subject.entity.Subject;
import lk.successStudent.studentManagement.asset.subject.service.SubjectService;
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/subject")
public class SubjectController implements AbstractController<Subject, Integer> {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("subjects", subjectService.findAll());
        return "subject/subject";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("subject", new Subject());

        model.addAttribute("addStatus",true);
        return "subject/addSubject";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("subjectDetail", subjectService.findById(id));
        return "subject/subject-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("subject", subjectService.findById(id));

        model.addAttribute("addStatus",false);
        return "subject/addSubject";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Subject subject, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("subject", subject);

            model.addAttribute("addStatus",true);
            return "subject/addSubject";
        }

        subjectService.persist(subject);
        return "redirect:/subject";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        subjectService.delete(id);
        return "redirect:/subject";
    }
}
