package lk.succes_student_management.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
  ADMIN("Admin"),
    MANAGER("Manager"),
    STUDENT("Student"),
  OFFICE_ASSISTANT("Office Assistant");

    private final String designation;
}
