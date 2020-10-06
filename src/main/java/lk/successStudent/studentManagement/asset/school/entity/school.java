package lk.successStudent.studentManagement.asset.school.entity;


import lk.sonali.bookshop.asset.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 45, unique = true)
    private String name;


    @ManyToMany(mappedBy = "authors")
    private List< Item > items;


}