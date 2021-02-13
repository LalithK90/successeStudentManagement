package lk.succes.student_management.asset.payment.controller;


import lk.succes.student_management.asset.batch_student.entity.BatchStudent;
import lk.succes.student_management.asset.batch_student.service.BatchStudentService;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.payment.entity.Payment;
import lk.succes.student_management.asset.payment.entity.enums.PaymentStatus;
import lk.succes.student_management.asset.payment.service.PaymentService;
import lk.succes.student_management.asset.student.entity.Student;
import lk.succes.student_management.asset.student.service.StudentService;
import lk.succes.student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDate;
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

  public PaymentController(PaymentService paymentService, MakeAutoGenerateNumberService makeAutoGenerateNumberService
      , StudentService studentService, BatchStudentService batchStudentService) {
    this.paymentService = paymentService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
    this.studentService = studentService;
    this.batchStudentService = batchStudentService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("payments",
                       paymentService.findAll()
                           .stream()
                           .filter(x -> x.getCreatedAt().toLocalDate().equals(LocalDate.now()))
                           .collect(Collectors.toList()));
    return "payment/payment";
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
    model.addAttribute("month", months);
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
  public String persist(@Valid @ModelAttribute BatchStudent batchStudent, BindingResult bindingResult, Model model) {
    if ( bindingResult.hasErrors() ) {
      return "redirect:/payment/add/" + batchStudent.getStudent().getId();
    }
    batchStudent.getPayments().forEach(x -> {
      if ( !x.getAmount().equals(BigDecimal.ZERO) ) {
        commonSave(x);
        paymentService.persist(x);
      }
    });
    return "redirect:/payment";
  }

  private void commonSave(Payment payment) {
    if ( payment.getId() == null ) {
      // need to create auto generated registration number
      Payment lastPayment = paymentService.lastStudentOnDB();
      if ( lastPayment == null ) {
        payment.setCode("SSP" + makeAutoGenerateNumberService.numberAutoGen(null));
      } else {
        String lastNumber = lastPayment.getCode().substring(3);
        payment.setCode("SSP" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      }
    }
  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    paymentService.delete(id);
    return "redirect:/payment";
  }
}
