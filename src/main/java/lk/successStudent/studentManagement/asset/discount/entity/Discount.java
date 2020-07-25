package lk.successStudent.studentManagement.asset.discount.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.successStudent.studentManagement.asset.payment.entity.Payment;
import lk.successStudent.studentManagement.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("Discount")
public class Discount extends AuditEntity {
    private String discountName;
    private String discountType;
    private String discountAmount;
    private String approvedBy;
    private String discountStatus;

    @OneToMany(mappedBy = "discount")
    private List<Payment> payments;





}