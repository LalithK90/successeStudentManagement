package lk.succes.student_management.asset.hall.controller;


import lk.succes.student_management.asset.common_asset.model.Enum.LiveDead;
import lk.succes.student_management.asset.hall.entity.Hall;
import lk.succes.student_management.asset.hall.entity.enums.HallCondition;
import lk.succes.student_management.asset.hall.service.HallService;
import lk.succes.student_management.util.interfaces.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/hall" )
public class HallController implements AbstractController< Hall, Integer > {
  private final HallService hallService;

  public HallController(HallService hallService) {
    this.hallService = hallService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("halls",
                       hallService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "hall/hall";
  }

  @GetMapping( "/new" )
  public String form(Model model) {
    model.addAttribute("hall", new Hall());
    model.addAttribute("hallConditions", HallCondition.values());
    model.addAttribute("addStatus", true);
    return "hall/addHall";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("hallDetail", hallService.findById(id));
    return "hall/hall-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("hall", hallService.findById(id));
    model.addAttribute("hallConditions", HallCondition.values());
    model.addAttribute("addStatus", false);
    return "hall/addHall";
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Hall hall, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("hall", hall);
      model.addAttribute("hallConditions", HallCondition.values());
      model.addAttribute("addStatus", false);
      return "hall/addHall";
    }

    hallService.persist(hall);
    return "redirect:/hall";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    hallService.delete(id);
    return "redirect:/hall";
  }
}
