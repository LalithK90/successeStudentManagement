package lk.succes_student_management.asset.user_management.controller;


import lk.succes_student_management.asset.employee.entity.Employee;
import lk.succes_student_management.asset.employee.entity.enums.Designation;
import lk.succes_student_management.asset.employee.entity.enums.EmployeeStatus;
import lk.succes_student_management.asset.employee.service.EmployeeService;
import lk.succes_student_management.asset.student.entity.Student;
import lk.succes_student_management.asset.student.service.StudentService;
import lk.succes_student_management.asset.teacher.entity.Teacher;
import lk.succes_student_management.asset.teacher.service.TeacherService;
import lk.succes_student_management.asset.user_management.entity.User;
import lk.succes_student_management.asset.user_management.service.RoleService;
import lk.succes_student_management.asset.user_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.Collator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/user" )
public class UserController {
  private final UserService userService;
  private final RoleService roleService;
  private final EmployeeService employeeService;
  private final TeacherService teacherService;
  private final StudentService studentService;


  @Autowired
  public UserController(UserService userService, EmployeeService employeeService, RoleService roleService,
                        TeacherService teacherService, StudentService studentService) {
    this.userService = userService;
    this.employeeService = employeeService;
    this.roleService = roleService;
    this.teacherService = teacherService;
    this.studentService = studentService;
  }

  @GetMapping
  public String userPage(Model model) {
    model.addAttribute("users", userService.findAll());
    return "user/user";
  }

  @GetMapping( value = "/{id}" )
  public String userView(@PathVariable( "id" ) Integer id, Model model) {
    model.addAttribute("userDetail", userService.findById(id));
    return "user/user-detail";
  }

  private String commonCode(Model model) {
    model.addAttribute("employeeDetailShow", true);
    model.addAttribute("employeeNotFoundShow", false);
    model.addAttribute("roleList", roleService.findAll());
    return "user/addUser";
  }

  @GetMapping( value = "/edit/{id}" )
  public String editUserFrom(@PathVariable( "id" ) Integer id, Model model) {
    User user = userService.findById(id);
    //employee
    if ( user.getEmployee() != null ) {
      model.addAttribute("employee", user.getEmployee());
    }
    //teacher
    if ( user.getTeacher() != null ) {
      model.addAttribute("teacher", user.getTeacher());
    }
    //student
    if ( user.getStudent() != null ) {
      model.addAttribute("student", user.getStudent());
    }
    model.addAttribute("user", userService.findById(id));
    model.addAttribute("addStatus", false);
    return commonCode(model);
  }

  @GetMapping( value = "/add" )
  public String userAddFrom(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("employee", new Employee());
    return "user/addUser";
  }

  @PostMapping( value = "/workingPlace" )
  public String addUserEmployeeDetails(@ModelAttribute( "employee" ) Employee employee, Model model) {

    List< Employee > employees = employeeService.search(employee)
        .stream()
        .filter(userService::findByEmployee)
        .collect(Collectors.toList());

    if ( employees.size() == 1 ) {
      User user = new User();
      user.setEmployee(employees.get(0));
      model.addAttribute("user", user);
      model.addAttribute("employee", employees.get(0));
      model.addAttribute("addStatus", true);
      return commonCode(model);
    }
    model.addAttribute("addStatus", true);
    model.addAttribute("employee", new Employee());
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("employeeNotFoundShow", true);
    model.addAttribute("employeeNotFound", "There is not employee in the system according to the provided details" +
        " or that employee already be a user in the system" +
        " \n Could you please search again !!");

    return "user/addUser";
  }

  // Above method support to send data to front end - All List, update, edit
  //Bellow method support to do back end function save, delete, update, search

  @PostMapping( value = {"/save", "/update"} )
  public String addUser(@Valid @ModelAttribute User user, BindingResult result, Model model) {

    if ( user.getId() ==null ) {
      if ( user.getEmployee() != null && userService.findUserByEmployee(user.getEmployee()) != null ) {
        ObjectError error = new ObjectError("employee", "This employee already defined as a user");
        result.addError(error);
      }
      if ( user.getTeacher() != null && userService.findUserByTeacher(user.getTeacher()) != null ) {
        ObjectError error = new ObjectError("teacher", "This teacher already defined as a user");
        result.addError(error);
      }
      if ( user.getStudent() != null && userService.findUserByStudent(user.getStudent()) != null ) {
        ObjectError error = new ObjectError("student", "This teacher student defined as a user");
        result.addError(error);
      }
    }
    if ( result.hasErrors() ) {
      model.addAttribute("addStatus", true);
      model.addAttribute("user", user);
      return commonCode(model);
    }
    if ( user.getId() != null ) {
      User dbUser = userService.findById(user.getId());
      dbUser.setEnabled(true);
      dbUser.setPassword(user.getPassword());
      dbUser.setEmployee(user.getEmployee());
      dbUser.setRoles(user.getRoles());
      userService.persist(dbUser);
      return "redirect:/user";
    }


    if ( user.getEmployee() != null ) {
      Employee employee = employeeService.findById(user.getEmployee().getId());
      user.setEnabled(employee.getEmployeeStatus().equals(EmployeeStatus.WORKING));
    }
    user.setRoles(user.getRoles());
    user.setEnabled(true);
    userService.persist(user);
    return "redirect:/user";
  }


  @GetMapping( value = "/remove/{id}" )
  public String removeUser(@PathVariable Integer id) {
    userService.delete(id);
    return "redirect:/user";
  }

  @GetMapping( value = "/search" )
  public String search(Model model, User user) {
    model.addAttribute("userDetail", userService.search(user));
    return "user/user-detail";
  }

  @GetMapping( value = "/teacherAdd" )
  public String userTeacherAddFrom(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("teacher", new Teacher());
    return "user/addUserTeacher";
  }

  @PostMapping( value = "/teacher" )
  public String addUserTeacherDetails(@ModelAttribute( "teacher" ) Teacher teacher, Model model) {

    List< Teacher > teachers = teacherService.search(teacher)
        .stream()
        .filter(userService::findByTeacher)
        .collect(Collectors.toList());

    if ( teachers.size() == 1 ) {
      User user = new User();
      user.setTeacher(teachers.get(0));
      model.addAttribute("user", user);
      model.addAttribute("teacher", teachers.get(0));
      model.addAttribute("addStatus", true);
      return commonCode(model);
    }
    model.addAttribute("addStatus", true);
    model.addAttribute("teacher", new Teacher());
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("employeeNotFoundShow", true);
    model.addAttribute("employeeNotFound", "There is not teacher in the system according to the provided details" +
        " or that teacher already be a user in the system" +
        " \n Could you please search again !!");

    return "user/addUserTeacher";
  }

  @GetMapping( value = "/studentAdd" )
  public String userStudentAddFrom(Model model) {
    model.addAttribute("addStatus", true);
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("student", new Student());
    return "user/addUserStudent";
  }

  @PostMapping( value = "/student" )
  public String addUserStudentDetails(@ModelAttribute( "student" ) Student student, Model model) {

    List< Student > students = studentService.search(student)
        .stream()
        .filter(userService::findByStudent)
        .collect(Collectors.toList());

    if ( students.size() == 1 ) {
      User user = new User();
      user.setStudent(students.get(0));
      model.addAttribute("user", user);
      model.addAttribute("student", students.get(0));
      model.addAttribute("addStatus", true);
      return commonCode(model);
    }
    model.addAttribute("addStatus", true);
    model.addAttribute("student", new Student());
    model.addAttribute("employeeDetailShow", false);
    model.addAttribute("employeeNotFoundShow", true);
    model.addAttribute("employeeNotFound", "There is not student in the system according to the provided details" +
        " or that student already be a user in the system" +
        " \n Could you please search again !!");

    return "user/addUserStudent";
  }

}
