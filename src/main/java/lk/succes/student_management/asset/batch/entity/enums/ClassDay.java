package lk.succes.student_management.asset.batch.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClassDay {
  MON("Monday"),
  TUE("Tuesday"),
  WED("Wednesday"),
  THU("Thursday"),
  FRI("Friday"),
  SAT("Saturday"),
  SUN("Sunday");
  private final String classDay;
}
