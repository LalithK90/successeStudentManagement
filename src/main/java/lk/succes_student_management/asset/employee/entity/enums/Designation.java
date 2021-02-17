package lk.succes_student_management.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
  ADMIN("Admin"),
    MANAGER("Manager"),
    PROCUREMENT_MANAGER("Procurement Manager"),
    HR_MANAGER("HR Manager"),
    CASHIER("Cashier");

    private final String designation;
}
