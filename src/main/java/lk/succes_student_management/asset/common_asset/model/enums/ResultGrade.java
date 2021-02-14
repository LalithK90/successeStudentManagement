package lk.succes_student_management.asset.common_asset.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultGrade {
    AP("A+"),
    A("A"),
    AM("A-"),
    BP("B+"),
    B("B"),
    BM("B-"),
    CP("C+"),
    C("C"),
    CM("C-"),
    DP("D+"),
    D("D"),
    E("E");

    private final String resultGrade;
}
/*
85-100  A+   4.00
70-84   A    4.00
65-69   A-   3.70
60-64   B+   3.30
55-59   B    3.00
50-54   B-   2.70
45-49   C+   2.30
40-44   C    2.00
35-39   C-   1.70
30-34   D+   1.30
25-29   D    1.00
00-24   E    0.00
 */
