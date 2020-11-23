package lk.succes.student_management.asset.batch.controller;



import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/batch")
public class BatchController implements AbstractController<Batch, Integer> {
    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("batchs", batchService.findAll());
        return "batch/batch";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("batch", new Batch());

        model.addAttribute("addStatus",true);
        return "batch/addBatch";
    }

    @GetMapping("/view/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("batchDetail", batchService.findById(id));
        return "batch/batch-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("batch", batchService.findById(id));

        model.addAttribute("addStatus",false);
        return "batch/addBatch";
    }

    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute Batch batch, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("batch", batch);

            model.addAttribute("addStatus",true);
            return "batch/addBatch";
        }

        batchService.persist(batch);
        return "redirect:/batch";

    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        batchService.delete(id);
        return "redirect:/batch";
    }
}
