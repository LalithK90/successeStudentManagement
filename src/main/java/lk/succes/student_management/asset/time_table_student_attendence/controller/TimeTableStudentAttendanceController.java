package lk.succes.student_management.asset.time_table_student_attendence.controller;

import lk.succes.student_management.asset.batch_student.service.BatchStudentService;
import lk.succes.student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.time_table.entity.TimeTable;
import lk.succes.student_management.asset.time_table.service.TimeTableService;
import lk.succes.student_management.asset.time_table_student_attendence.entity.TimeTableStudentAttendance;
import lk.succes.student_management.asset.time_table_student_attendence.service.TimeTableStudentAttendanceService;
import lk.succes.student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;


@Controller
@RequestMapping( "/timeTableStudentAttendance" )
public class TimeTableStudentAttendanceController {
  private final TimeTableService timeTableService;
  private final BatchStudentService batchStudentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final TimeTableStudentAttendanceService timeTableStudentAttendanceService;


  public TimeTableStudentAttendanceController(TimeTableService timeTableService,
                                              BatchStudentService batchStudentService,
                                              MakeAutoGenerateNumberService makeAutoGenerateNumberService, TimeTableStudentAttendanceService timeTableStudentAttendanceService) {
    this.timeTableService = timeTableService;
    this.batchStudentService = batchStudentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.timeTableStudentAttendanceService = timeTableStudentAttendanceService;
  }

  @GetMapping( "/{id}" )
  public String attendanceForm(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("timeTable", new TimeTable( ));
    model.addAttribute("batchStudents",
                       batchStudentService.findByBatch(timeTableService.findById(id).getBatch())
                           .stream()
                           .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
                           .collect(Collectors.toList()));
    model.addAttribute("attendanceStatuses", AttendanceStatus.values());
    return "timeTableStudentAttendance/addTimeTableStudentAttendance";
  }

  @PostMapping
  public String saveAttendance(@ModelAttribute TimeTable timeTable, BindingResult bindingResult) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/timeTableStudentAttendance/" + timeTable.getId();
    }

    timeTable.getTimeTableStudentAttendances().forEach(x -> {
      if ( x.getAttendanceStatus().equals(AttendanceStatus.PRE) ) {
        commonSave(x);
        x.setTimeTable(timeTable);
        timeTableStudentAttendanceService.persist(x);
      }
    });
    return "redirect:/timeTable/teacher";
  }

  private void commonSave(TimeTableStudentAttendance timeTableStudentAttendance){
    if ( timeTableStudentAttendance.getId() == null ) {
      // need to create auto generated registration number
      TimeTableStudentAttendance lastTimeTable = timeTableStudentAttendanceService.lastTimeTableStudentAttendance();
      //registration number format => SSS200001
      if ( lastTimeTable != null ) {
        String lastNumber = lastTimeTable.getCode().substring(3);
        timeTableStudentAttendance.setCode("SSA" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        timeTableStudentAttendance.setCode("SSA" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
    }
  }
}
