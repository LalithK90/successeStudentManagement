package lk.succes.student_management.asset.payment.controller;


import lk.succes.student_management.asset.payment.entity.Payment;
import lk.succes.student_management.asset.payment.service.PaymentService;
import lk.succes.student_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/payment")
public class PaymentController implements AbstractController<Payment, Integer> {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("payments", paymentService.findAll());
        return "payment/payment";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("payment", new Payment());

        model.addAttribute("addStatus",true);
        return "payment/addPayment";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("paymentDetail", paymentService.findById(id));
        return "payment/payment-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("payment", paymentService.findById(id));

        model.addAttribute("addStatus",false);
        return "payment/addPayment";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Payment payment, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("payment", payment);

            model.addAttribute("addStatus",true);
            return "payment/addPayment";
        }

        paymentService.persist(payment);
        return "redirect:/payment";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        paymentService.delete(id);
        return "redirect:/payment";
    }
}
