package lk.succes.student_management.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LiveDead {
    ACTIVE("Active"),
    STOP("Stop");

    private final String liveDead;
}
