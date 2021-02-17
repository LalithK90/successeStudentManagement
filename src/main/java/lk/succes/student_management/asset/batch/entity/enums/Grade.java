package lk.succes.student_management.asset.batch.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Grade {
  GRADE_2("Grade-2"),
  GRADE_3("Grade-3"),
  GRADE_4("Grade-4"),
  GRADE_5("Grade-5"),
  GRADE_6("Grade-6"),
  GRADE_7("Grade-7"),
  GRADE_8("Grade-8"),
  GRADE_9("Grade-9"),
  GRADE_10("Grade-10"),
  OL("Grade-OL"),
  AL("Grade-AL");

  private final String grade;

}
