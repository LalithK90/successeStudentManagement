package lk.succes.student_management.asset.payment.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes.student_management.asset.discount.entity.Discount;
import lk.succes.student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter( "Payment" )
public class Payment extends AuditEntity {

    private String tranId;
    private String discountStatus;
    private String amount;
    private String createdTime;
    private String createdDate;
    private String createdBy;




    @ManyToOne
    private Discount discount;

}