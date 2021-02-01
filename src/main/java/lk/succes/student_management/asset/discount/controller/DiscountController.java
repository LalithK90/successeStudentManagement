package lk.succes.student_management.asset.discount.controller;



import lk.succes.student_management.asset.discount.entity.Discount;
import lk.succes.student_management.asset.discount.service.DiscountService;
import lk.succes.student_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/discount")
public class DiscountController implements AbstractController<Discount, Integer> {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("discounts", discountService.findAll());
        return "discount/discount";
    }

    @GetMapping("/add")
    public String form(Model model) {
        model.addAttribute("discount", new Discount());

        model.addAttribute("addStatus",true);
        return "discount/addDiscount";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("discountDetail", discountService.findById(id));
        return "discount/discount-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("discount", discountService.findById(id));

        model.addAttribute("addStatus",false);
        return "discount/addDiscount";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Discount discount, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("discount", discount);

            model.addAttribute("addStatus",true);
            return "discount/addDiscount";
        }

        discountService.persist(discount);
        return "redirect:/discount";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        discountService.delete(id);
        return "redirect:/discount";
    }
}
