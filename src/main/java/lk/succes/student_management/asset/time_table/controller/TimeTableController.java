package lk.succes.student_management.asset.time_table.controller;


import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch.entity.enums.ClassDay;
import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.asset.batch_student.service.BatchStudentService;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.hall.service.HallService;
import lk.succes.student_management.asset.subject.service.SubjectService;
import lk.succes.student_management.asset.teacher.service.TeacherService;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.asset.time_table.service.TimeTableService;
import lk.succes.student_management.util.interfaces.AbstractController;
import lk.succes.student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/timeTable" )
public class TimeTableController implements AbstractController< TimeTable, Integer > {
  private final TimeTableService timeTableService;
  private final HallService hallService;
  private final SubjectService subjectService;
  private final TeacherService teacherService;
  private final BatchService batchService;
  private final BatchStudentService batchStudentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public TimeTableController(TimeTableService timeTableService, HallService hallService,
                             SubjectService subjectService, TeacherService teacherService, BatchService batchService,
                             BatchStudentService batchStudentService,
                             MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.timeTableService = timeTableService;
    this.hallService = hallService;
    this.subjectService = subjectService;
    this.teacherService = teacherService;
    this.batchService = batchService;
    this.batchStudentService = batchStudentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("timeTables",
                       timeTableService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "timeTable/timeTable";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    return "timeTable/dateChooser";
  }


  @PostMapping( "/add" )
  public String form(@RequestParam( "date" ) @DateTimeFormat( pattern = "yyyy-MM-dd" ) LocalDate date, Model model) {
    LocalDate today = LocalDate.now();
    //month
    Month month = today.getMonth();
//Day of week
    String dayOfWeek = date.getDayOfWeek().toString();

    List< Batch > batches = new ArrayList<>();
    for ( Batch batch : batchService.findByClassDay(ClassDay.valueOf(dayOfWeek))
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList()) ) {
      batch.setCount(batchStudentService.countByBatch(batch));
      batches.add(batch);
    }

    model.addAttribute("timeTable", new TimeTable());
    model.addAttribute("batches", batchService.findAll());
//    model.addAttribute("batches", batches);
    model.addAttribute("addStatus", true);
    model.addAttribute("date", date);
    return commonThing(model);
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
    return commonThing(model);
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute TimeTable timeTable, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("timeTable", timeTable);
      model.addAttribute("addStatus", true);
      return commonThing(model);
    }
    if ( timeTable.getId() == null ) {
      TimeTable lastTimeTable = timeTableService.lastTimeTable();
      if ( lastTimeTable == null ) {
        timeTable.setCode("SSTM" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        timeTable.setCode("SSTM" + makeAutoGenerateNumberService.numberAutoGen(lastTimeTable.getCode().substring(4)).toString());
      }
    }
    //todo before save need to validate
    timeTableService.persist(timeTable);
    return "redirect:/timeTable";

  }

  private String commonThing(Model model) {
    model.addAttribute("halls",
                       hallService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    model.addAttribute("teachers",
                       teacherService.findAll()
                           .stream()
                           .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
                           .collect(Collectors.toList()));
    model.addAttribute("subjects",
                       subjectService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "timeTable/addTimeTable";
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    timeTableService.delete(id);
    return "redirect:/timeTable";
  }
}
