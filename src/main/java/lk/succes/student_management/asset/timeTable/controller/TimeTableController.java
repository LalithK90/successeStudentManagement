package lk.succes.student_management.asset.timeTable.controller;


import lk.succes.student_management.asset.timeTable.entity.TimeTable;
import lk.succes.student_management.asset.timeTable.service.TimeTableService;
import lk.succes.student_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/timeTable")
public class TimeTableController implements AbstractController<TimeTable, Integer> {
    private final TimeTableService timeTableService;

    public TimeTableController(TimeTableService timeTableService) {
        this.timeTableService = timeTableService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("timeTables", timeTableService.findAll());
        return "timeTable/timeTable";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("timeTable", new TimeTable());

        model.addAttribute("addStatus",true);
        return "timeTable/addTimeTable";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("timeTableDetail", timeTableService.findById(id));
        return "timeTable/timeTable-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("timeTable", timeTableService.findById(id));

        model.addAttribute("addStatus",false);
        return "timeTable/addTimeTable";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute TimeTable timeTable, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("timeTable", timeTable);

            model.addAttribute("addStatus",true);
            return "timeTable/addTimeTable";
        }

        timeTableService.persist(timeTable);
        return "redirect:/timeTable";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        timeTableService.delete(id);
        return "redirect:/timeTable";
    }
}
