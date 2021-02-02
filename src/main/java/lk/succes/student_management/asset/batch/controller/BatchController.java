package lk.succes.student_management.asset.batch.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.succes.student_management.asset.batch.entity.Batch;
import lk.succes.student_management.asset.batch.entity.enums.ClassDay;
import lk.succes.student_management.asset.batch.entity.enums.Grade;
import lk.succes.student_management.asset.batch.service.BatchService;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.teacher.controller.TeacherController;
import lk.succes.student_management.asset.teacher.service.TeacherService;
import lk.succes.student_management.util.interfaces.AbstractController;
import lk.succes.student_management.util.service.MakeAutoGenerateNumberService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/batch" )
public class BatchController implements AbstractController< Batch, Integer > {
  private final BatchService batchService;
  private final TeacherService teacherService;
  private final MakeAutoGenerateNumberService makeAutoGenerateNumberService;

  public BatchController(BatchService batchService, TeacherService teacherService,
                         MakeAutoGenerateNumberService makeAutoGenerateNumberService) {
    this.batchService = batchService;
    this.teacherService = teacherService;
    this.makeAutoGenerateNumberService = makeAutoGenerateNumberService;
  }


  @GetMapping
  public String findAll(Model model) {
    model.addAttribute("batchs",
                       batchService.findAll().stream().filter(x -> x.getLiveDead().equals(LiveDead.ACTIVE)).collect(Collectors.toList()));
    return "batch/batch";
  }

  private String commonMethod(Model model, Batch batch, boolean addStatus) {
    model.addAttribute("grades", Grade.values());
    model.addAttribute("classDays", ClassDay.values());
    model.addAttribute("teachers", teacherService.findAll());
    model.addAttribute("batch", batch);
    model.addAttribute("addStatus", addStatus);
    model.addAttribute("subjectUrl", MvcUriComponentsBuilder
        .fromMethodName(TeacherController.class, "findId", "")
        .build()
        .toString());
    return "batch/addBatch";
  }

  @GetMapping( "/add" )
  public String form(Model model) {
    return commonMethod(model, new Batch(), true);
  }

  @GetMapping( "/view/{id}" )
  public String findById(@PathVariable Integer id, Model model) {
    model.addAttribute("batchDetail", batchService.findById(id));
    return "batch/batch-detail";
  }

  @GetMapping( "/edit/{id}" )
  public String edit(@PathVariable Integer id, Model model) {
    return commonMethod(model, batchService.findById(id), false);
  }

  @PostMapping( "/save" )
  public String persist(@Valid @ModelAttribute Batch batch, BindingResult bindingResult,
                        RedirectAttributes redirectAttributes, Model model) {
    if ( bindingResult.hasErrors() ) {
      return commonMethod(model, batch, true);
    }

    if ( batch.getId() == null ) {
      Batch batchDb = batchService.findByName(batch.getName());

      if ( batchDb != null ) {
        ObjectError error = new ObjectError("batch",
                                            "This batch is already in the system. ");
        bindingResult.addError(error);
        return commonMethod(model, batch, true);
      }
      // need to create auto generated registration number
      Batch lastBatch = batchService.lastBatchOnDB();
      if ( lastBatch != null ) {
        String lastNumber = lastBatch.getCode().substring(3);
        batch.setCode("SSB" + makeAutoGenerateNumberService.numberAutoGen(lastNumber));
      } else {
        batch.setCode("SSB" + makeAutoGenerateNumberService.numberAutoGen(null));
      }
    }

    batchService.persist(batch);
    return "redirect:/batch";

  }

  @GetMapping( "/delete/{id}" )
  public String delete(@PathVariable Integer id, Model model) {
    batchService.delete(id);
    return "redirect:/batch";
  }

  @GetMapping( "/{grade}" )
  @ResponseBody
  public MappingJacksonValue findByGrade(@PathVariable( "grade" ) Grade grade) {
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(batchService.findByGrade(grade));

    SimpleBeanPropertyFilter forBatch = SimpleBeanPropertyFilter
        .filterOutAllExcept("id", "name");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Batch", forBatch);

    mappingJacksonValue.setFilters(filters);

    return mappingJacksonValue;
  }

  @GetMapping( "/id/{id}" )
  @ResponseBody
  public MappingJacksonValue findById(@PathVariable( "id" ) Integer id) {
    MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(batchService.findById(id).getTeacher());

    SimpleBeanPropertyFilter forTeacher = SimpleBeanPropertyFilter
        .filterOutAllExcept("id","firstName", "fee");

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter("Teacher", forTeacher);

    mappingJacksonValue.setFilters(filters);

    return mappingJacksonValue;
  }
}
