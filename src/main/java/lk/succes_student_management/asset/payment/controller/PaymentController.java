package lk.succes_student_management.asset.payment.controller;


import lk.succes_student_management.asset.batch_student.entity.BatchStudent;
import lk.succes_student_management.asset.batch_student.service.BatchStudentService;
import lk.succes_student_management.asset.common_asset.model.TwoDate;
import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.payment.entity.Payment;
import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import lk.succes_student_management.asset.payment.service.PaymentService;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.student.service.StudentService;
import lk.succes_student_management.util.service.DateTimeAgeService;
import lk.succes_student_management.util.service.EmailService;
import lk.succes_student_management.util.service.MakeAutoGenerateNumberService;
import org.aspectj.apache.bcel.classfile.Code;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/payment" )
public class PaymentController {
  private final PaymentService paymentService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;
  private final StudentService studentService;
  private final BatchStudentService batchStudentService;
  private final DateTimeAgeService dateTimeAgeService;
  private final EmailService emailService;

  public PaymentController(PaymentService paymentService, MakeAutoGenerateNumberService makeAutoGenerateNumberService
      , StudentService studentService, BatchStudentService batchStudentService, DateTimeAgeService dateTimeAgeService
      , EmailService emailService) {
    this.paymentService = paymentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.studentService = studentService;
    this.batchStudentService = batchStudentService;
    this.dateTimeAgeService = dateTimeAgeService;
    this.emailService = emailService;
  }

  private String commonFindAll(LocalDate from, LocalDate to, Model model) {

    LocalDateTime startAt = dateTimeAgeService.dateTimeToLocalDateStartInDay(from);
    LocalDateTime endAt = dateTimeAgeService.dateTimeToLocalDateEndInDay(to);


    model.addAttribute("payments",
                       paymentService.findByCreatedAtIsBetween(startAt, endAt));

    model.addAttribute("message",
                       "Following table show details belongs from " + from + " to " + to +
                           "there month. if you need to more please search using above method");
    return "payment/payment";
  }

  @GetMapping
  public String findAll(Model model) {
    return commonFindAll(LocalDate.now(), LocalDate.now(), model);
  }

  @PostMapping
  public String findAllSearch(@ModelAttribute TwoDate twoDate, Model model) {
    return commonFindAll(twoDate.getStartDate(), twoDate.getEndDate(), model);
  }

  @GetMapping( "/add" )
  public String chooseForm(Model model) {
    model.addAttribute("student", false);
    return "student/studentChooser";
  }

  @GetMapping( "/add/{id}" )
  public String form(@PathVariable( "id" ) Integer id, Model model) {
    Student student = studentService.findById(id);
    List< BatchStudent > batchStudents = batchStudentService.findByStudent(student)
        .stream()
        .filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE))
        .collect(Collectors.toList());
    List< Month > months = Arrays.asList(Month.values());

    List< BatchStudent > batchStudentPayment = new ArrayList<>();


    batchStudents.forEach(y -> {
      List< Payment > payments = new ArrayList<>();
      months.forEach(x -> {
        Payment payment = paymentService.findByMonthAndBatchStudent(x, y);
        if ( payment == null ) {
          Payment newPayment = new Payment();
          newPayment.setPaymentStatus(PaymentStatus.NO_PAID);
          newPayment.setLiveDead(LiveDead.STOP);
          newPayment.setBatchStudent(y);
          newPayment.setAmount(y.getBatch().getTeacher().getFee());
          newPayment.setMonth(x);
          payments.add(newPayment);
        } else {
          payments.add(payment);
        }
      });
      y.setPayments(payments.stream().distinct().collect(Collectors.toList()));
      batchStudentPayment.add(y);
    });

    student.setBatchStudents(batchStudentPayment);
    model.addAttribute("paymentStatuses", PaymentStatus.values());
    model.addAttribute("student", student);
    model.addAttribute("addStatus", true);
    model.addAttribute("studentDetail", student);
    return "payment/addAllBatchPayment";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("paymentDetail", paymentService.findById(id));
    return "payment/payment-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("payment", paymentService.findById(id));
    model.addAttribute("addStatus", false);
    return "payment/addPayment";
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Payment payment, Model model) {
    commonSave(payment);
    paymentService.persist(payment);
    return "redirect:/payment";

  }

  @PostMapping( "/batchStudent/save" )
  public String persist(@Valid @ModelAttribute Student student, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/payment/add/" + student.getId();
    }
    List< Payment > payments = new ArrayList<>();


    for ( int i = 0; i < student.getBatchStudents().size(); i++ ) {
      if ( student.getBatchStudents().get(i).getPayments() != null ) {
        for ( int k = 0; k < student.getBatchStudents().get(i).getPayments().size(); k++ ) {
          if ( student.getBatchStudents().get(i).getPayments().get(k).getAmount() != null ) {
            if ( student.getBatchStudents().get(i).getPayments().get(k).getPaymentStatus() != null && !student.getBatchStudents().get(i).getPayments().get(k).getPaymentStatus().equals(PaymentStatus.NO_PAID) ) {
              student.getBatchStudents().get(i).getPayments().get(k).setBatchStudent(student.getBatchStudents().get(i));
              payments.add(paymentService.persist(commonSave(student.getBatchStudents().get(i).getPayments().get(k))));
            }
          }
        }
      }
    }


    List< Payment > withBatchStudent = new ArrayList<>();
    payments.forEach(x -> {
      x.setBatchStudent(batchStudentService.findById(x.getBatchStudent().getId()));
      withBatchStudent.add(x);

    });

    StringBuilder paymentInfo = new StringBuilder();
    for ( Payment payment : withBatchStudent ) {
      paymentInfo.append("\n\t\t\t\t Payment Code \t\t").append(payment.getCode()).append("\t\t\t\t Month \t\t").append(payment.getMonth()).append(
          " \t\t\t\t Amount \t\t ").append(payment.getAmount()).append("\t\t\t\t Paid At \t\t").append(payment.getCreatedAt().toLocalDate()).append(" \t\t\t\tCreated By\t\t").append(payment.getCreatedBy());
    }


    Student studentDb = studentService.findById(student.getId());
    if ( studentDb.getEmail() != null ) {
      String message =
          "Dear " + studentDb.getFirstName() + "\n Your following payment was accepted\n" + paymentInfo + "\n Thanks " +
              "\n\n Success Student";
      emailService.sendEmail(studentDb.getEmail(), "Payment - Notification", message);
    }

    model.addAttribute("payments", withBatchStudent);
    return "payment/paymentPrint";
  }

  private Payment commonSave(Payment payment) {
    if ( payment.getId() == null ) {
      Payment lastPayment = paymentService.lastStudentOnDB();
      if ( lastPayment == null ) {
        payment.setCode("SSP" + makeAutoGenerateNumberService.numberAutoGen(null));
      } else {
        String lastNumber = lastPayment.getCode().substring(3);
        payment.setCode("SSP" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      }
    }
    return payment;
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    paymentService.delete(id);
    return "redirect:/payment";
  }
}
