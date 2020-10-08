package lk.successStudent.studentManagement.asset.school.controller;


import lk.successStudent.studentManagement.asset.school.entity.School;
import lk.successStudent.studentManagement.asset.school.service.SchoolService;
<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/controller/SchoolController.java
import lk.successStudent.studentManagement.util.interfaces.AbstractController;
=======
import org.springframework.beans.factory.annotation.Autowired;
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/controller/schoolController.java
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/controller/SchoolController.java
@RequestMapping("/school")
public class SchoolController implements AbstractController<School, Integer> {
    private SchoolService schoolService;

    public void Controller(SchoolService schoolService) {this.schoolService = schoolService;
=======
@RequestMapping( "/school" )
public class SchoolController {
    private final SchoolService schoolService;

    @Autowired
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/controller/schoolController.java
    }

    private String commonMethod(Model model, boolean addStatus, School school) {
        model.addAttribute("school", school);
        model.addAttribute("addStatus", addStatus);
        return "school/addSchool";
    }

    @GetMapping
    public String schoolPage(Model model) {
        model.addAttribute("schools", schoolService.findAll());
        return "school/school";
    }

<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/controller/SchoolController.java
    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("school", new School());

        model.addAttribute("addStatus",true);
        return "school/addSchool";
    }
=======
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/controller/schoolController.java

    @GetMapping( "/edit/{id}" )
    public String edit(@PathVariable( "id" ) Integer id, Model model) {
        return commonMethod(model, false, schoolService.findById(id));
    }

    @GetMapping( "/add" )
    public String form(Model model) {
        return commonMethod(model, true, new School());
    }

<<<<<<< HEAD:src/main/java/lk/successStudent/studentManagement/asset/school/controller/SchoolController.java
    @PostMapping("/save")
    public String persist(@Valid @ModelAttribute School school, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("school", school);
=======
    // Above method support to send data to front end - All List, update, edit
    //Bellow method support to do back end function save, delete, update, search
>>>>>>> 6ef0968a98609e6d872fa41c862bb74404f03f56:src/main/java/lk/successStudent/studentManagement/asset/school/controller/schoolController.java

    @PostMapping( value = {"/save", "/update"} )
    public String addSchool(@Valid @ModelAttribute School school,
                            BindingResult result, Model model) {
        if ( result.hasErrors() ) {
            return commonMethod(model, true, school);
        }
        schoolService.persist(school);
        return "redirect:/school";
    }


    @RequestMapping( value = "/delete/{id}", method = RequestMethod.GET )
    public String delete(@PathVariable Integer id) {
        schoolService.delete(id);
        return "redirect:/school";
    }


}
