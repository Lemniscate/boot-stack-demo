package com.github.lemniscate.stack.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lemniscate.lib.sra.annotation.ApiNestedResource;
import com.github.lemniscate.lib.sra.repo.Model;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="settings")
@ApiNestedResource(path = "settings", parentProperty = "owner")
@Getter @Setter
public class UserSettings implements Model<Long> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    private boolean showDashboard, sortAscending;

    @JsonIgnore
    @OneToOne
    @PrimaryKeyJoinColumn
    private UserAccount owner;


}
