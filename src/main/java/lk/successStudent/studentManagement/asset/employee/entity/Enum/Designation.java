package lk.successStudent.studentManagement.asset.employee.entity.Enum;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Designation {
    //All station can check
    MGR("Manager"),
    TEST("Test User"),
    TEMPORY("Tempory Staff"),
    ADMIN("Administrative Officer )"),
    CLRK("Office clerk)"),
//    //Normal every thing belong to his - able to check more than one work station
//    ACE("Assistant Commissioner Of Excise"),
//    //Below guy has check station belong to him
//    SE("Superintendent Of Excise"),
//    //Station staff all below this comment
//    OIC("Chief Inspector Of Excise"),
//    IE("Inspector Of Excise"),
//    ESM("Excise Sergeant Major"),
//    ES("Excise Sergeant"),
//    //There is no authority to logo in to the system
//    EC("Excise Corporal"),
//    EG("Excise Guard"),
//    ED("Excise Driver"), Owner("");

    private final String designation;
}

