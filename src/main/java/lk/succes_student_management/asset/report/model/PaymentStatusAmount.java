package lk.succes_student_management.asset.report.model;


import lk.succes_student_management.asset.payment.entity.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class PaymentStatusAmount {
    private PaymentStatus paymentStatus;
    private BigDecimal amount;
    private long recordCount;
}
