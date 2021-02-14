package lk.succes_student_management.asset.payment.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DiscountStatus {
  NOD("No Discount"),
  HCR("Half Card"),
  FCR("Free Card");

  private final String discountStatus;
}
