package lk.succes.student_management.asset.payment.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.common_asset.model.enums.LiveDead;
import lk.succes.student_management.asset.discount.entity.Discount;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Payment" )
public class Payment extends AuditEntity {

    @Column(unique = true)
    private String code;

    private String discountStatus;

    private String amount;

    @Enumerated( EnumType.STRING )
    private LiveDead liveDead;

    @ManyToOne
    private Discount discount;

}
