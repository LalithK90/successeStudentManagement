package lk.succes_student_management.asset.subject.controller;


import lk.succes_student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes_student_management.asset.subject.entity.Subject;
import lk.succes_student_management.asset.subject.service.SubjectService;
import lk.succes_student_management.util.interfaces.AbstractController;
import lk.succes_student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping( "/subject" )
public class SubjectController implements AbstractController< Subject, Integer > {
  private final SubjectService subjectService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public SubjectController(SubjectService subjectService, MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.subjectService = subjectService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }

  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("addStatus", false);
    model.addAttribute("subjects",
                       subjectService.findAll());
    return "subject/subject";
  }

  @GetMapping("/delete")
  public String findAllDeleted(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("subjects",
                       subjectService.findAllDeleted());
    return "subject/subject";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    model.addAttribute("subject", new Subject());
    model.addAttribute("addStatus", true);
    return "subject/addSubject";
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("subjectDetail", subjectService.findById(id));
    return "subject/subject-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    model.addAttribute("subject", subjectService.findById(id));
    model.addAttribute("addStatus", false);
    return "subject/addSubject";
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Subject subject, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      model.addAttribute("subject", subject);
      model.addAttribute("addStatus", true);
      return "subject/addSubject";
    }
    if ( subject.getId() == null ) {
      Subject lastSubject = subjectService.lastSubject();
      if ( lastSubject == null ) {
        subject.setCode("SSSC" + makeAutoGenerateNumberService.numberAutoGen(null).toString());
      } else {
        subject.setCode("SSSC" + makeAutoGenerateNumberService.numberAutoGen(lastSubject.getCode()
                                                                                 .substring(4)).toString());
      }
    }

    try {
      subjectService.persist(subject);
    } catch ( Exception e ) {
      ObjectError error = new ObjectError("subject",
                                          "Please fix following errors which you entered .\n System message -->" + e.getCause().getCause().getMessage());
      bindingResult.addError(error);
      model.addAttribute("subject", subject);
      model.addAttribute("addStatus", true);
      return "subject/addSubject";
    }
    return "redirect:/subject";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    subjectService.delete(id);
    return "redirect:/subject";
  }

  @GetMapping( "/active/{id}" )
  public String active(@PathVariable Integer id) {
    Subject subject = subjectService.findById(id);
    subject.setLiveDead(LiveDead.ACTIVE);
    subjectService.persist(subject);
    return "redirect:/subject/delete";
  }
}
