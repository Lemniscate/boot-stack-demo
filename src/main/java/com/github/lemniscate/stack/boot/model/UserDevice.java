package com.github.lemniscate.stack.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lemniscate.spring.crud.annotation.ApiResource;
import com.github.lemniscate.spring.crud.model.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="device")
@ApiResource(omitController = true)
@Getter @Setter
public class UserDevice implements Model<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    private String identifier, os;

    @JsonIgnore
    @ManyToOne
    @JoinTable(name="user_device",
            joinColumns = { @JoinColumn(name="device_id", referencedColumnName="id", nullable=false, updatable=false)},
            inverseJoinColumns = { @JoinColumn(name="account_id", referencedColumnName="id", nullable=false, updatable=false) }
        )
    private UserAccount owner;


}
