package lk.succes_student_management.asset.report.controller;

import com.twilio.rest.api.v2010.account.call.FeedbackSummary;
import lk.succes_student_management.asset.batch.entity.Batch;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.entity.BatchExam;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.batch_student_exam_result.entity.BatchStudentExamResult;
import lk.succes_student_management.asset.batch_student_exam_result.service.BatchStudentExamResultService;
import lk.succes_student_management.asset.common_asset.model.TwoDate;
import lk.succes_student_management.asset.common_asset.model.enums.AttendanceStatus;
import lk.succes_student_management.asset.common_asset.model.enums.ResultGrade;
import lk.succes_student_management.asset.employee.service.EmployeeFilesService;
import lk.succes_student_management.asset.employee.service.EmployeeService;
import lk.succes_student_management.asset.hall.service.HallService;
import lk.succes_student_management.asset.payment.entity.Payment;
import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import lk.succes_student_management.asset.payment.service.PaymentService;
import lk.succes_student_management.asset.report.model.BatchAmount;
import lk.succes_student_management.asset.report.model.BatchExamResultStudent;
import lk.succes_student_management.asset.report.model.PaymentStatusAmount;
import lk.succes_student_management.asset.report.model.StudentAmount;
import lk.succes_student_management.asset.school.service.SchoolService;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.student.service.StudentService;
import lk.succes_student_management.asset.subject.service.SubjectService;
import lk.succes_student_management.asset.teacher.service.TeacherService;
import lk.succes_student_management.asset.time_table.service.TimeTableService;
import lk.succes_student_management.asset.time_table_student_attendence.service.TimeTableStudentAttendanceService;
import lk.succes_student_management.asset.user_management.service.UserService;
import lk.succes_student_management.util.service.DateTimeAgeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping( "/report" )
public class ReportController {

  private final BatchService batchService;
  private final BatchExamService batchExamService;
  private final BatchStudentService batchStudentService;
  private final BatchStudentExamResultService batchStudentExamResultService;
  private final EmployeeService employeeService;
  private final EmployeeFilesService employeeFilesService;
  private final HallService hallService;
  private final PaymentService paymentService;
  private final SchoolService schoolService;
  private final StudentService studentService;
  private final SubjectService subjectService;
  private final TeacherService teacherService;
  private final TimeTableService timeTableService;
  private final TimeTableStudentAttendanceService timeTableStudentAttendanceService;
  private final UserService userService;
  private final DateTimeAgeService dateTimeAgeService;
  private PaymentStatusAmount paymentStatusAmount;
  private boolean paymentStatus;

  ;

  public ReportController(BatchService batchService, BatchExamService batchExamService,
                          BatchStudentService batchStudentService,
                          BatchStudentExamResultService batchStudentExamResultServic,
                          EmployeeService employeeService, EmployeeFilesService employeeFilesService,
                          HallService hallService, PaymentService paymentService,
                          SchoolService schoolService, StudentService studentService, SubjectService subjectService,
                          TeacherService teacherService,
                          TimeTableService timeTableService,
                          TimeTableStudentAttendanceService timeTableStudentAttendanceService,
                          UserService userService, DateTimeAgeService dateTimeAgeService) {
    this.batchService = batchService;
    this.batchExamService = batchExamService;
    this.batchStudentService = batchStudentService;
    this.batchStudentExamResultService = batchStudentExamResultServic;
    this.employeeService = employeeService;
    this.employeeFilesService = employeeFilesService;
    this.hallService = hallService;
    this.paymentService = paymentService;
    this.schoolService = schoolService;
    this.studentService = studentService;
    this.subjectService = subjectService;
    this.teacherService = teacherService;
    this.timeTableService = timeTableService;
    this.timeTableStudentAttendanceService = timeTableStudentAttendanceService;
    this.userService = userService;
    this.dateTimeAgeService = dateTimeAgeService;
  }

  private String commonIncomeReport(Model model, LocalDate startDate, LocalDate endDate) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);
    System.out.println(" astar " + startDateTime + "  end " + endDateTime);
    List< Payment > payments = paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime);

    List< BigDecimal > totalPaymentAmount = new ArrayList<>();
    List< Batch > batches = new ArrayList<>();
    List< Student > students = new ArrayList<>();
    payments.forEach(x -> {
      totalPaymentAmount.add(x.getAmount());
      batches.add(x.getBatchStudent().getBatch());
      students.add(x.getBatchStudent().getStudent());
    });
    List< BatchAmount > batchAmounts = new ArrayList<>();
    batches.stream().distinct().collect(Collectors.toList()).forEach(x -> {
      List< Payment > batchPayments =
          payments.stream().filter(y -> y.getBatchStudent().getBatch().equals(x)).collect(Collectors.toList());
      List< BigDecimal > batchPaymentAmounts = new ArrayList<>();
      batchPayments.forEach(y -> batchPaymentAmounts.add(y.getAmount()));
      BatchAmount batchAmount = new BatchAmount();
      batchAmount.setAmount(batchPaymentAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
      batchAmount.setCount(batchPayments.size());
      batchAmount.setBatch(batchService.findById(x.getId()));
      batchAmounts.add(batchAmount);
    });
    List< StudentAmount > studentAmounts = new ArrayList<>();
    students.stream().distinct().collect(Collectors.toList()).forEach(x -> {
      List< Payment > studentPayments =
          payments.stream().filter(y -> y.getBatchStudent().getStudent().equals(x)).collect(Collectors.toList());
      List< BigDecimal > studentPaymentAmounts = new ArrayList<>();
      studentPayments.forEach(y -> studentPaymentAmounts.add(y.getAmount()));
      StudentAmount studentAmount = new StudentAmount();
      studentAmount.setAmount(studentPaymentAmounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
      studentAmount.setCount(studentPaymentAmounts.size());
      studentAmount.setStudent(studentService.findById(x.getId()));
      studentAmounts.add(studentAmount);
    });


    model.addAttribute("batchAmounts", batchAmounts);
    model.addAttribute("studentAmounts", studentAmounts);
    model.addAttribute("payments", payments);
    model.addAttribute("paymentCount", payments.size());
    model.addAttribute("paymentAmount", totalPaymentAmount.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
    String message = "This report is belongs from " + startDate + " to " + endDate;
    model.addAttribute("message", message);
    return "report/incomeReport";
  }

  @GetMapping( "/income" )
  public String todayReportIncome(Model model) {
    return commonIncomeReport(model, LocalDate.now(), LocalDate.now());
  }

  @PostMapping( "/income" )
  public String todayReportIncome(@ModelAttribute TwoDate twoDate, Model model) {
    return commonIncomeReport(model, twoDate.getStartDate(), twoDate.getEndDate());
  }

  @GetMapping( "/batchExam" )
  public String batchExam(Model model) {
    LocalDate today = LocalDate.now();

    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDayWithOutNano(today);
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDayWithOutNano(today.minusDays(7));
    List< BatchExam > batchExams = batchExamService.findByStartAtBetween(startDateTime, endDateTime);
    String message = "This report is belongs from " + today.minusDays(7) + " to " + today;
    return commonExam(model, batchExams, message);
  }

  @PostMapping( "/batchExam" )
  public String batchExam(@ModelAttribute TwoDate twoDate, Model model) {
    LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDayWithOutNano(twoDate.getStartDate());
    LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDayWithOutNano(twoDate.getEndDate());
    List< BatchExam > batchExams;
    if ( twoDate.getId() == null ) {
      batchExams = batchExamService.findByStartAtBetween(startDateTime,
                                                         endDateTime);
    } else {
      Batch batch = batchService.findById(twoDate.getId());
      batchExams = batchExamService.findByStartAtBetweenAndBatch(startDateTime,
                                                                 endDateTime, batch);
      model.addAttribute("batchDetail", batch);
    }

    String message = "This report is belongs from " + twoDate.getStartDate() + " to " + twoDate.getEndDate();
    return commonExam(model, batchExams, message);
  }

  private String commonExam(Model model, List< BatchExam > batchExams, String message) {
    List< BatchExamResultStudent > batchExamResultStudents = new ArrayList<>();
    for ( BatchExam batchExam : batchExams ) {
      BatchExamResultStudent batchExamResultStudent = new BatchExamResultStudent();
      batchExamResultStudent.setBatchExam(batchExam);
      batchExamResultStudent.setBatch(batchService.findById(batchExam.getBatch().getId()));
      List< BatchStudentExamResult > batchStudentExamResults =
          batchExamService.findById(batchExam.getId()).getBatchStudentExamResults();

      List< BatchStudentExamResult > presentBatchStudentExamResults =
          batchStudentExamResults.stream().filter(x -> x.getAttendanceStatus().equals(AttendanceStatus.PRE)).collect(Collectors.toList());
      batchExamResultStudent.setAttendCount(presentBatchStudentExamResults.size());
      List< Student > presentStudents = new ArrayList<>();
      presentBatchStudentExamResults.forEach(x -> presentStudents.add(studentService.findById(x.getBatchStudent().getStudent().getId())));
      batchExamResultStudent.setAttendStudents(presentStudents);
      List< Student > absentStudents = new ArrayList<>();
      List< BatchStudentExamResult > absentBatchStudentExamResults =
          batchStudentExamResults.stream().filter(x -> x.getAttendanceStatus().equals(AttendanceStatus.AB)).collect(Collectors.toList());
      absentBatchStudentExamResults.forEach(x -> absentStudents.add(studentService.findById(x.getBatchStudent().getStudent().getId())));
      batchExamResultStudent.setAbsentCount(absentBatchStudentExamResults.size());
      batchExamResultStudent.setAbsentStudents(absentStudents);


      List< Student > aPlusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.AP);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> aPlusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("aPlusStudents", aPlusStudents);
      batchExamResultStudent.setAPlusStudents(aPlusStudents);
      List< Student > aStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.A);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> aStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("aStudents", aStudents);
      batchExamResultStudent.setAStudents(aStudents);
      List< Student > aMinusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.AM);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> aMinusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("aMinusStudents", aMinusStudents);
      batchExamResultStudent.setAMinusStudents(aMinusStudents);
      List< Student > bPlusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.BP);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> bPlusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("bPlusStudents", bPlusStudents);
      batchExamResultStudent.setBPlusStudents(bPlusStudents);
      List< Student > bStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.B);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> bStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("bStudents", bStudents);
      batchExamResultStudent.setBStudents(bStudents);
      List< Student > bMinusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.BM);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> bMinusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));
      model.addAttribute("bMinusStudents", bMinusStudents);
      batchExamResultStudent.setBMinusStudents(bMinusStudents);
      List< Student > cPlusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.CP);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> cPlusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setCPlusStudents(cPlusStudents);

      List< Student > cStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.C);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> cStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setCStudents(cStudents);

      List< Student > cMinusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.CM);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> cMinusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setCMinusStudents(cMinusStudents);

      List< Student > dPlusStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.DP);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> dPlusStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setDPlusStudents(dPlusStudents);
      List< Student > dStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.D);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> dStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setDStudents(dStudents);

      List< Student > eStudents = new ArrayList<>();
      batchStudentExamResults.stream().filter(x -> {
        if ( x.getResultGrade() != null ) {
          return x.getResultGrade().equals(ResultGrade.E);
        } else {
          return false;
        }
      }).collect(Collectors.toList()).forEach(batchStudentExamResult -> eStudents.add(studentService.findById(batchStudentExamResult.getBatchStudent().getStudent().getId())));

      batchExamResultStudent.setEStudents(eStudents);
      batchExamResultStudents.add(batchExamResultStudent);
    }
    model.addAttribute("batchExamResultStudents", batchExamResultStudents);
    model.addAttribute("message", message);
    model.addAttribute("batchExams", batchExamService.findAll());
    return "report/batchExamReport";
  }

/*    //1.total students up to now
    @GetMapping("/student")
    public String allStudentReport( Model model) {
        model.addAttribute("allStudents", studentService.findAll());
        return "report/allStudent";
    }


    //2.total payments received up to now
    @GetMapping("/payment")

    public String paymentsForPeriod( Model model) {
        //this line for test purpose
//        List<Payment> paymentStatus = paymentService.findByPaymentStatus(PaymentStatus.NO_PAID);
        LocalDate localDate = LocalDate.now();
        String message = "This report for" + localDate.toString();
        model.addAttribute("Mess",message);
        return commonPaymentStatus(localDate, localDate);

    }


    private String commonPaymentStatus(LocalDate startDate, LocalDate endDate , Model model) {
        LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(startDate);
        LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(endDate);

        List<Payment> payment = paymentService.findByCreatedAtIsBetween(startDate, endDate);
        List<BigDecimal> amounts = new ArrayList<>();

        List<PaymentStatusAmount> paymentStatusAmounts = new ArrayList<>();
        for (PaymentStatus paymentStatus : PaymentStatus.values()) {
            List<Payment> paymentsStatusNotPaid = new ArrayList<>();
            paymentsStatusNotPaid.addAll(payment
                    .stream()
                    .filter(x -> x.getPaymentStatus().equals(paymentStatus))
                    .collect(Collectors.toList()));

            paymentStatusAmount.setPaymentStatus(paymentStatus);
            paymentStatusAmount.setRecordCount(paymentsStatusNotPaid.size());
            paymentsStatusNotPaid.forEach(x -> amounts.add(x.getAmount()));
            paymentStatusAmount.setAmount(amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));

            paymentStatusAmounts.add(paymentStatusAmount);
        }
        model.addAttribute("paymentStatusAmounts",paymentStatusAmounts);

        PaymentStatusAmount paymentStatusAmount = new PaymentStatusAmount();


        paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime)
                .stream()
                .filter(x -> x.getPaymentStatus().equals(PaymentStatus.NO_PAID))
                .collect(Collectors.toList());
        List<BigDecimal> amounts = new ArrayList<>();

        paymentStatus.forEach(x -> {
            amounts.add(x.getAmount());
        });

        PaymentStatusAmount paymentStatusAmount = new PaymentStatusAmount();
        paymentStatusAmount.setPaymentStatus(PaymentStatus.NO_PAID);
        paymentStatusAmount.setAmount(amounts.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        paymentStatusAmount.setRecordCount(List < Payment > paymentStatus = paymentService.findAll()
                .stream()
                .filter(x -> x.getPaymentStatus().equals(PaymentStatus.NO_PAID))
                .count());

        return "report/paymentsForPeriod";
    }


    private String commonPaymentStatus(LocalDate localDate, LocalDate date) {
        LocalDateTime startDateTime = dateTimeAgeService.dateTimeToLocalDateStartInDay(twoDate.getStartDate());
        LocalDateTime endDateTime = dateTimeAgeService.dateTimeToLocalDateEndInDay(twoDate.getEndDate());

        List<BigDecimal> amounts = new ArrayList<>();
        paymentService.findByCreatedAtIsBetween(startDateTime, endDateTime)
                .stream()
                .filter(x -> x.getPaymentStatus().equals(PaymentStatus.NO_PAID))
                .collect(Collectors.toList()).forEach(x -> amounts.add(x.getAmount()));
        return "report/paymentStatus";
    }

    @PostMapping("/payment")

    public String paymentsForPeriodSearch(@ModelAttribute TwoDate twoDate, Model model) {
        String message = "This report for" + twoDate.getStartDate()+"00"+twoDate.getEndDate();
        model.addAttribute("Mess",message);
        return commonPaymentStatus(twoDate.getStartDate(), twoDate.getEndDate(),model);
    }
    */


}
