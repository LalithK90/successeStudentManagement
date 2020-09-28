package lk.successStudent.studentManagement.asset.school.controller;


import lk.successStudent.studentManagement.asset.school.entity.School;
import lk.successStudent.studentManagement.asset.school.service.SchoolService;
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/school")
public class SchoolController implements AbstractController<School, Integer> {
    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        return "school/school";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("school", new School());

        model.addAttribute("addStatus",true);
        return "school/addSchool";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("schoolDetail", schoolService.findById(id));
        return "school/school-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("school", schoolService.findById(id));

        model.addAttribute("addStatus",false);
        return "school/addSchool";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute School school, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("school", school);

            model.addAttribute("addStatus",true);
            return "school/addSchool";
        }

        schoolService.persist(school);
        return "redirect:/school";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        schoolService.delete(id);
        return "redirect:/school";
    }
}
