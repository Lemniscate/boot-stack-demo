package com.github.lemniscate.stack.boot.model;


import com.github.lemniscate.lib.rest.annotation.ApiResource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Identifiable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@ApiResource
@Getter @Setter
public class Organization implements Identifiable<Long>{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "organization", cascade=CascadeType.ALL)
    private List<UserAccount> users = new ArrayList<UserAccount>();

}
