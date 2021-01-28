package lk.succes.student_management.asset.time_table.controller;


import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.asset.hall.service.HallService;
import lk.succes.student_management.asset.subject.service.SubjectService;
import lk.succes.student_management.asset.teacher.service.TeacherService;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.asset.time_table.service.TimeTableService;
import lk.succes.student_management.util.interfaces.AbstractController;
import lk.succes.student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/timeTable" )
public class TimeTableController implements AbstractController< TimeTable, Integer > {
  private final TimeTableService timeTableService;
  private final HallService hallService;
  private final SubjectService subjectService;
  private final TeacherService teacherService;
  private final BatchService batchService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public TimeTableController(TimeTableService timeTableService, HallService hallService,
                             SubjectService subjectService, TeacherService teacherService, BatchService batchService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.timeTableService = timeTableService;
    this.hallService = hallService;
    this.subjectService = subjectService;
    this.teacherService = teacherService;
    this.batchService = batchService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("timeTables",
                       timeTableService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "timeTable/timeTable";
  }

  @GetMapping( "/new" )
  public String form(Model model) {
    model.addAttribute("timeTable", new TimeTable());
      model.addAttribute("addStatus", true);
      return getString(model);
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("timeTableDetail", timeTableService.findById(id));
    return "timeTable/timeTable-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("timeTable", timeTableService.findById(id));
      model.addAttribute("addStatus", false);
      return getString(model);
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute TimeTable timeTable, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("timeTable", timeTable);
        return getString(model);
    }
    if ( timeTable.getId() == null ) {
      TimeTable lastTimeTable = timeTableService.lastTimeTable();
      if ( lastTimeTable == null ) {
        timeTable.setCode("SSTM" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        timeTable.setCode("SSTM" + makeAutoGenerateNumberService.numberAutoGen(lastTimeTable.getCode().substring(4)).toString());
      }
    }
    timeTableService.persist(timeTable);
    return "redirect:/timeTable";

  }

    private String getString(Model model) {
        model.addAttribute("halls",
                           hallService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
        model.addAttribute("teachers",
                           teacherService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
        model.addAttribute("subjects",
                           subjectService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
      model.addAttribute("batches",
                         batchService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
      model.addAttribute("addStatus", true);
        return "timeTable/addTimeTable";
    }

    @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    timeTableService.delete(id);
    return "redirect:/timeTable";
  }
}
