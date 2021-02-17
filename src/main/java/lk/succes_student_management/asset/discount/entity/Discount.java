package lk.succes_student_management.asset.discount.entity;


import com.fasterxml.jackson.annotation.JsonFilter;
import lk.succes_student_management.util.audit.AuditEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

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
}
