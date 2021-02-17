package lk.succes_student_management.asset.batch.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassDay {
  MONDAY("Monday"),
  TUESDAY("Tuesday"),
  WEDNESDAY("Wednesday"),
  THURSDAY("Thursday"),
  FRIDAY("Friday"),
  SATURDAY("Saturday"),
  SUNDAY("Sunday");
  private final String classDay;
}
