package lk.succes_student_management.asset.report.controller;

import com.twilio.rest.api.v2010.account.call.FeedbackSummary;
import lk.succes_student_management.asset.batch.service.BatchService;
import lk.succes_student_management.asset.batch_exam.service.BatchExamService;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.batch_student_exam_result.service.BatchStudentExamResultService;
import lk.succes_student_management.asset.common_asset.model.TwoDate;
import lk.succes_student_management.asset.employee.service.EmployeeFilesService;
import lk.succes_student_management.asset.employee.service.EmployeeService;
import lk.succes_student_management.asset.hall.service.HallService;
import lk.succes_student_management.asset.payment.entity.Payment;
import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import lk.succes_student_management.asset.payment.service.PaymentService;
import lk.succes_student_management.asset.report.model.PaymentStatusAmount;
import lk.succes_student_management.asset.school.service.SchoolService;
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
@RequestMapping("/report")
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

    public ReportController(BatchService batchService, BatchExamService batchExamService, BatchStudentService batchStudentService, BatchStudentExamResultService batchStudentExamResultServic,
                            EmployeeService employeeService, EmployeeFilesService employeeFilesService, HallService hallService, PaymentService paymentService,
                            SchoolService schoolService, StudentService studentService, SubjectService subjectService, TeacherService teacherService,
                            TimeTableService timeTableService, TimeTableStudentAttendanceService timeTableStudentAttendanceService,
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

    //1.total students up to now
    @GetMapping("/student")
    public String allStudentReport(@NotNull Model model) {
        model.addAttribute("allStudents", studentService.findAll());
        return "report/allStudent";
    }


    //2.total payments received up to now
    @GetMapping("/payment")

    public String paymentsForPeriod(@NotNull Model model) {
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


}
