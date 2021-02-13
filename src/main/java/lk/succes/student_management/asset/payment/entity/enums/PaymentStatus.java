package lk.succes.student_management.asset.payment.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@AllArgsConstructor
public enum PaymentStatus {
  PAID("Paid"),
  NO_PAID("Not Paid");

  private final String paymentStatus;
}
