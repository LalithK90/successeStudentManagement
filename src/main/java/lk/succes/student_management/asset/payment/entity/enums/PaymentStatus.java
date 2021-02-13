package lk.succes.student_management.asset.payment.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
  PAID("Paid"),
  NO_PAID("Not");

  private final String paymentStatus;
}
