package com.github.lemniscate.stack.boot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.lemniscate.lib.sra.annotation.ApiResource;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Identifiable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @Author dave 2/8/14 11:45 AM
 */
@Entity
@ApiResource(path = "users")
@Getter @Setter
public class UserAccount implements UserDetails, Identifiable<Long>{

    private static final long serialVersionUID = 1L;

    public static enum Roles{
        ROLE_DEFAULT
    };

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Setter(AccessLevel.NONE)
    private Long id;

    @JsonIgnore
    private String userPassword;

    private String firstName, lastName, email;
    private Boolean active;

    @ManyToOne
    @JoinTable(name="user_organization",
            inverseJoinColumns = { @JoinColumn(name="user_id", referencedColumnName="id", nullable=true, updatable=true) },
            joinColumns = { @JoinColumn(name="org_id", referencedColumnName="id", nullable=true, updatable=true)})
    private Organization organization;

    @JsonIgnore
    @OneToMany(mappedBy = "owner", cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserDevice> devices = new ArrayList<UserDevice>();

    @JsonIgnore
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private UserSettings settings;

    private transient List<Roles> roles = new ArrayList<Roles>();

    @Deprecated
    public UserAccount() {}

    public UserAccount(String firstName, String lastName, String email, String userPassword, Boolean active, List<Roles> roles) {
        Assert.hasText(firstName, "Must supply a first name");
        Assert.hasText(lastName, "Must supply a last name");
        Assert.hasText(email, "Must supply a valid email");
        Assert.hasText(userPassword, "Must supply a valid username");
        Assert.isTrue(userPassword.length() == 60, "Must supply 60 character userPassword");
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userPassword = userPassword;
        this.active = active;
        this.roles = roles;
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    // SpringSecurity methods

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        for (final Roles role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_USERS_WRITE"));
        authorities.add(new SimpleGrantedAuthority("ROLE_USERS_READ"));
        authorities.add(new SimpleGrantedAuthority("ROLE_DEFAULT"));
        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return userPassword;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

}

