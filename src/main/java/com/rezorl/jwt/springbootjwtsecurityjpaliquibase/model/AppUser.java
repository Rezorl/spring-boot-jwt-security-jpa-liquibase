package com.rezorl.jwt.springbootjwtsecurityjpaliquibase.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "Users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 4, max = 255, message = "Minimum username length: 4 characters")
    @Column(unique = true, nullable = false)
    private String username;

    @Size(min = 8, max = 255, message = "Minimum email length: 8 characters")
    @Column(unique = true, nullable = false)
    private String email;

    @Size(min = 8, max = 255, message = "Minimum password length: 8 characters")
    @Column(nullable = false)
    private String password;

    @Column(name = "account_non_expired", nullable = false, columnDefinition = "bit default true")
    private boolean accountNonExpired = true;

    @Column(name = "account_non_locked", nullable = false, columnDefinition = "bit default true")
    private boolean accountNonLocked = true;

    @Column(name = "credentials_non_expired", nullable = false, columnDefinition = "bit default true")
    private boolean credentialsNonExpired = true;

    @Column(nullable = false, columnDefinition = "bit default true")
    private boolean enabled = true;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "Roles", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "role", nullable = false)
    private List<Role> roles;

    private AppUser() {
    }

    public AppUser(@Size(min = 4, max = 255, message = "Minimum username length: 4 characters") String username, @Size(min = 8, max = 255, message = "Minimum email length: 8 characters") String email, @Size(min = 8, max = 255, message = "Minimum password length: 8 characters") String password, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

}